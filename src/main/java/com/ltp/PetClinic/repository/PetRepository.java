package com.ltp.PetClinic.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.PetClinic.entity.Pet;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
    Optional<Pet> findByIdAndOwnerId(Long id, Long ownerId);

    @Transactional
    void deleteByIdAndOwnerId(Long petId, Long ownerId);

    List<Pet> findByOwnerId(Long ownerId);
}
