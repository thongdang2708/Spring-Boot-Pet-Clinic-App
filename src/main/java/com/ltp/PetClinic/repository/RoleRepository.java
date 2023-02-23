package com.ltp.PetClinic.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.PetClinic.entity.Role;
import com.ltp.PetClinic.entity.RoleType;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRole(RoleType roleType);
}
