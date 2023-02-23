package com.ltp.PetClinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.PetClinic.entity.Vet;

@Repository
public interface VetRepository extends CrudRepository<Vet, Long> {

}
