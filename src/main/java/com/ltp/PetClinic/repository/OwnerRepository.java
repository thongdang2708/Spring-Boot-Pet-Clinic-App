package com.ltp.PetClinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.PetClinic.entity.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
    boolean existsOwnerByName(String name);
}
