package com.ltp.PetClinic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.ltp.PetClinic.security.exception.CustomAccessDenyHandler;
import com.ltp.PetClinic.security.exception.CustomAuthenticationEntryPoint;
import com.ltp.PetClinic.security.filter.AuthenticationFilter;
import com.ltp.PetClinic.security.filter.ExceptionHandlerFilter;
import com.ltp.PetClinic.security.filter.JWTAuthorizationFilter;
import com.ltp.PetClinic.security.manager.CustomAuthenticationManager;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Autowired
    private CustomAccessDenyHandler accessDenyHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/login");

        http.exceptionHandling().accessDeniedHandler(accessDenyHandler);

        http.cors()
                .and()
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeHttpRequests((requests) -> requests
                        // Register
                        .requestMatchers(HttpMethod.POST, SecurityConstants.registerPath).permitAll()
                        // Path get for user
                        .requestMatchers(HttpMethod.GET, "/auth/user/*").hasAnyAuthority("user", "admin")
                        // Get refresh token
                        .requestMatchers(HttpMethod.GET, SecurityConstants.getRefreshTokenPath).permitAll()
                        // Log in by user
                        .requestMatchers(HttpMethod.POST, SecurityConstants.logInPath).permitAll()
                        // Register as admin
                        .requestMatchers(HttpMethod.POST, SecurityConstants.registerAdminPath).permitAll()
                        // Security configs for owners
                        .requestMatchers(SecurityConstants.fullOwnerPath).permitAll()
                        // Security configs for pet
                        .requestMatchers(SecurityConstants.petPath).permitAll()
                        // Security configs for vet
                        .requestMatchers(SecurityConstants.vetPath).permitAll()
                        // Security configs for visit
                        .requestMatchers(SecurityConstants.visitPath).permitAll()
                        // Any request to authenticate
                        .anyRequest().authenticated())
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.logout((logout) -> logout
                .logoutUrl("/logout")
                .addLogoutHandler((request, response, auth) -> {
                    SecurityContextHolder.clearContext();
                })
                .permitAll());

        return http.build();
    }

}
