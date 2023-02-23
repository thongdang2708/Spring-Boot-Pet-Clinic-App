package com.ltp.PetClinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.PetClinic.entity.Owner;
import com.ltp.PetClinic.entity.Pet;
import com.ltp.PetClinic.entity.Vet;
import com.ltp.PetClinic.entity.VetStatus;
import com.ltp.PetClinic.entity.Visit;
import com.ltp.PetClinic.exception.VisitExistsWithOwnerIdAndPetIdException;
import com.ltp.PetClinic.exception.VisitNotFoundWithIdException;
import com.ltp.PetClinic.exception.VisitNotFoundWithIdsException;
import com.ltp.PetClinic.exception.WrongBetweenOwnerAndPetException;
import com.ltp.PetClinic.repository.OwnerRepository;
import com.ltp.PetClinic.repository.VetRepository;
import com.ltp.PetClinic.repository.VisitRepository;

@Service
public class VisitServiceIml implements VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private PetService petService;

    @Autowired
    private VetService vetService;

    @Autowired
    private VetRepository vetRepository;

    @Override
    public Visit getVisit(Long ownerId, Long petId) {
        Optional<Visit> visit = visitRepository.findByOwnerIdAndPetId(ownerId, petId);

        if (visit.isPresent()) {
            return visit.get();
        } else {
            throw new VisitNotFoundWithIdsException(ownerId, petId);
        }
    }

    @Override
    public Visit addVisit(Long ownerId, Long petId, Visit visit) {
        Owner owner = ownerService.getOwner(ownerId);
        Pet getPet = petService.getSinglePet(petId, ownerId);

        if (!owner.getPet().contains(getPet)) {
            throw new WrongBetweenOwnerAndPetException(ownerId, petId);
        } else if (visitRepository.existsVisitByOwnerIdAndPetId(ownerId, petId)) {
            throw new VisitExistsWithOwnerIdAndPetIdException(
                    "This owner id " + ownerId + " and pet id exist already!");
        } else {
            visit.setOwner(owner);
            visit.setPet(getPet);

            return visitRepository.save(visit);
        }

    }

    @Override
    public Visit updateVisit(Long ownerId, Long petId, Visit visit) {
        Owner owner = ownerService.getOwner(ownerId);
        Pet pet = petService.getSinglePet(petId, ownerId);
        Visit getVisit = getVisit(ownerId, petId);

        if (!owner.getPet().contains(pet)) {
            throw new WrongBetweenOwnerAndPetException(ownerId, petId);
        } else {
            getVisit.setDate(visit.getDate());

            return visitRepository.save(getVisit);
        }
    }

    @Override
    public void deleteVisit(Long ownerId, Long petId) {
        // TODO Auto-generated method stub
        Visit visit = getVisit(ownerId, petId);

        VetStatus vetStatus = new VetStatus();

        visit.getVet().setVetCondition(vetStatus.getActiveMode());

        visitRepository.deleteByOwnerIdAndPetId(ownerId, petId);

    }

    @Override
    public List<Visit> getAllVisitsOfOwner(Long ownerId) {
        // TODO Auto-generated method stub
        return visitRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Visit> getAllVisitsOfPet(Long petId) {
        // TODO Auto-generated method stub
        return visitRepository.findByPetId(petId);
    }

    @Override
    public List<Visit> getAllVisits() {
        // TODO Auto-generated method stub
        return (List<Visit>) visitRepository.findAll();
    }

    @Override
    public Visit assignVetToVisitByAdmin(Long id, Long vetId) {
        Visit visit = getVisitByVisitId(id);
        Vet vet = vetService.getVet(vetId);

        visit.setVet(vet);
        VetStatus vetStatus = new VetStatus();
        vet.setVetCondition(vetStatus.getInactiveMode());

        vetRepository.save(vet);
        return visitRepository.save(visit);

    }

    @Override
    public Visit getVisitByVisitId(Long visitID) {

        Optional<Visit> visit = visitRepository.findById(visitID);

        if (visit.isPresent()) {
            return visit.get();
        } else {
            throw new VisitNotFoundWithIdException(visitID);
        }
    }

}
