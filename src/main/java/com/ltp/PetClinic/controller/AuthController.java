package com.ltp.PetClinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.PetClinic.entity.RefreshToken;
import com.ltp.PetClinic.entity.User;
import com.ltp.PetClinic.service.AuthService;
import com.ltp.PetClinic.service.OwnerService;
import com.ltp.PetClinic.storageObject.TokenStorageObject;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/registerAdmin")
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> registerUserAdmin(@Valid @RequestBody User user) {

        return new ResponseEntity<>(authService.registerAdmin(user), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {

        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenStorageObject> getRefreshToken(@RequestBody RefreshToken refreshToken) {

        return new ResponseEntity<>(authService.createNewTokenFromRefreshToken(refreshToken.getRefreshToken()),
                HttpStatus.CREATED);
    }

}
