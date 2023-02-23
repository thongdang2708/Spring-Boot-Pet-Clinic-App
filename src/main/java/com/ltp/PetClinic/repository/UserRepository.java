package com.ltp.PetClinic.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.PetClinic.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    Optional<User> findByUsername(String username);
}
