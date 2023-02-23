package com.ltp.PetClinic.service;

import com.ltp.PetClinic.entity.User;
import com.ltp.PetClinic.storageObject.TokenStorageObject;

public interface AuthService {
    User getUser(Long id);

    User register(User user);

    User getUserByUsername(String username);

    User registerAdmin(User user);

    TokenStorageObject createNewTokenFromRefreshToken(String refreshToken);
}
