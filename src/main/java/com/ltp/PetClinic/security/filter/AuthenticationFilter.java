package com.ltp.PetClinic.security.filter;

import java.io.IOException;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ltp.PetClinic.entity.User;
import com.ltp.PetClinic.security.SecurityConstants;
import com.ltp.PetClinic.security.manager.CustomAuthenticationManager;
import com.ltp.PetClinic.storageObject.MessageExceptionObject;
import com.ltp.PetClinic.storageObject.TokenStorageObject;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {

            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword());

            return authenticationManager.authenticate(authentication);

        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // TODO Auto-generated method stub
        List<String> listOfAuthorities = new ArrayList<>();

        for (GrantedAuthority authority : authResult.getAuthorities()) {
            listOfAuthorities.add(authority.toString());
        }

        String access_token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        SecurityConstants.JWT_ACCESS_TOKEN_EXPIRATION))
                .withClaim("roles", listOfAuthorities)
                .sign(Algorithm.HMAC512(SecurityConstants.JWT_SECRET_CODE));

        String refresh_token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        SecurityConstants.JWT_REFRESH_TOKEN_EXPIRATION))
                .withClaim("roles", listOfAuthorities)
                .sign(Algorithm.HMAC512(SecurityConstants.JWT_SECRET_CODE));

        Gson gson = new Gson();
        TokenStorageObject tokenStorageObject = new TokenStorageObject(access_token,
                refresh_token);
        String body = gson.toJson(tokenStorageObject);

        response.setHeader("Authorization", "Bearer " + access_token);
        response.setHeader("refresh_token", refresh_token);

        response.setContentType("application/json");
        response.setStatus(response.SC_OK);
        response.getWriter().write(body);
        response.getWriter().flush();

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        MessageExceptionObject message = new MessageExceptionObject(failed.getMessage());
        String body = gson.toJson(message);
        response.setContentType("application/json");
        response.setStatus(response.SC_UNAUTHORIZED);
        response.getWriter().write(body);
        response.getWriter().flush();

    }
}
