package com.ltp.PetClinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ltp.PetClinic.entity.Vet;
import java.util.List;

@Repository
public interface VetJPARepository extends JpaRepository<Vet, Long> {

    

}
