package com.ltp.PetClinic.service;

import com.ltp.PetClinic.entity.Pet;
import com.ltp.PetClinic.entity.Vet;
import com.ltp.PetClinic.entity.Visit;
import java.util.List;

public interface VisitService {
    Visit getVisit(Long ownerId, Long petId);

    Visit addVisit(Long ownerId, Long petId, Visit visit);

    Visit updateVisit(Long ownerId, Long petId, Visit visit);

    void deleteVisit(Long ownerId, Long petId);

    List<Visit> getAllVisitsOfOwner(Long ownerId);

    List<Visit> getAllVisitsOfPet(Long petId);

    List<Visit> getAllVisits();

    Visit getVisitByVisitId(Long visitID);

    Visit assignVetToVisitByAdmin(Long id, Long vetId);

}
