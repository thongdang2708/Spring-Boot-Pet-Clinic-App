package com.ltp.PetClinic.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.PetClinic.entity.Visit;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {
    Optional<Visit> findByOwnerIdAndPetId(Long ownerId, Long petId);

    boolean existsVisitByOwnerIdAndPetId(Long ownerId, Long petId);

    @Transactional
    void deleteByOwnerIdAndPetId(Long ownerId, Long petId);

    List<Visit> findByOwnerId(Long ownerId);

    List<Visit> findByPetId(Long petId);

    List<Visit> findByVetId(Long vetId);
}
