package com.ltp.PetClinic.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.PetClinic.security.SecurityConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        String username = JWT.require(Algorithm.HMAC512(SecurityConstants.JWT_SECRET_CODE))
                .build()
                .verify(token)
                .getSubject();

        List<String> getAuthorities = JWT.require(Algorithm.HMAC512(SecurityConstants.JWT_SECRET_CODE))
                .build()
                .verify(token)
                .getClaim("roles")
                .asList(String.class);

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (String authority : getAuthorities) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
}
