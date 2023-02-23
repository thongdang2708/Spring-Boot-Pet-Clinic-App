package com.ltp.PetClinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.PetClinic.entity.Role;
import com.ltp.PetClinic.entity.RoleType;
import com.ltp.PetClinic.entity.User;
import com.ltp.PetClinic.exception.InvalidRefreshTokenException;
import com.ltp.PetClinic.exception.RoleNotFoundWithIdException;
import com.ltp.PetClinic.exception.RoleNotFoundWithNameException;
import com.ltp.PetClinic.exception.UserNotFoundWithIdException;
import com.ltp.PetClinic.exception.UserNotFoundWithNameException;
import com.ltp.PetClinic.exception.UsernameExistsException;
import com.ltp.PetClinic.repository.RoleRepository;
import com.ltp.PetClinic.repository.UserRepository;
import com.ltp.PetClinic.security.SecurityConstants;
import com.ltp.PetClinic.storageObject.TokenStorageObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthServiceIml implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Override
    public User getUser(Long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundWithIdException(id);
        }
    }

    @Override
    public User register(User user) {
        if (userRepository.existsUserByUsername(user.getUsername())
                || userRepository.existsUserByEmail(user.getEmail())) {
            throw new UsernameExistsException("this user exists already!");
        } else {

            Role role = checkRole(roleRepository.findByRole(RoleType.user));

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User createdUser = userRepository.save(user);

            addRoleToUser(createdUser.getId(), role.getId());

            return createdUser;

        }

    }

    public void addRoleToUser(Long userId, Long roleId) {

        Role role = checkRoleWithId(roleRepository.findById(roleId), roleId);

        User user = getUser(userId);

        user.addRole(role);

        userRepository.save(user);

    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundWithNameException(username);
        }
    }

    public Role checkRoleWithId(Optional<Role> entity, Long roleId) {

        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new RoleNotFoundWithIdException(roleId);
        }
    }

    public Role checkRole(Optional<Role> entity) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new RoleNotFoundWithNameException("Cannot find a role with this role name!");
        }
    }

    @Override
    public TokenStorageObject createNewTokenFromRefreshToken(String refreshToken) {

        try {

            String username = JWT.require(Algorithm.HMAC512(SecurityConstants.JWT_SECRET_CODE))
                    .build()
                    .verify(refreshToken)
                    .getSubject();

            User user = getUserByUsername(username);

            List<String> getAuthorities = new ArrayList<>();

            for (GrantedAuthority authority : user.getAuthorities()) {
                getAuthorities.add(authority.toString());
            }

            String new_access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.JWT_ACCESS_TOKEN_EXPIRATION))
                    .withClaim("roles", getAuthorities)
                    .sign(Algorithm.HMAC512(SecurityConstants.JWT_SECRET_CODE));

            String new_refresh_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(
                            new Date(System.currentTimeMillis() + SecurityConstants.JWT_REFRESH_TOKEN_EXPIRATION))
                    .withClaim("roles", getAuthorities)
                    .sign(Algorithm.HMAC512(SecurityConstants.JWT_SECRET_CODE));

            response.setHeader("Authorization", "Bearer " + new_access_token);
            response.setHeader("refresh_token", new_refresh_token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            TokenStorageObject token = new TokenStorageObject(new_access_token, new_refresh_token);

            return token;

        } catch (Exception ex) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    @Override
    public User registerAdmin(User user) {
        if (userRepository.existsUserByUsername(user.getUsername())
                || userRepository.existsUserByEmail(user.getEmail())) {
            throw new UsernameExistsException("This user exists already!");
        } else {

            Role role = checkRole(roleRepository.findByRole(RoleType.admin));

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User createdUser = userRepository.save(user);

            addAdminRoleToUser(createdUser.getId(), role.getId());

            return createdUser;
        }
    }

    public void addAdminRoleToUser(Long userId, Long roleId) {

        Role role = checkRole(roleRepository.findById(roleId));

        User user = getUser(userId);

        user.addRole(role);

        userRepository.save(user);

    }
}
