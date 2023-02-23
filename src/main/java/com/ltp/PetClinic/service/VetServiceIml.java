package com.ltp.PetClinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.PetClinic.entity.Vet;
import com.ltp.PetClinic.entity.VetStatus;
import com.ltp.PetClinic.entity.VetType;
import com.ltp.PetClinic.entity.Visit;
import com.ltp.PetClinic.exception.VetNotFoundWithIdException;
import com.ltp.PetClinic.repository.VetJPARepository;
import com.ltp.PetClinic.repository.VetRepository;
import com.ltp.PetClinic.repository.VisitRepository;

@Service
public class VetServiceIml implements VetService {

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private VetJPARepository vetJPARepository;

    @Override
    public Vet getVet(Long id) {
        Optional<Vet> vet = vetRepository.findById(id);

        if (vet.isPresent()) {
            return vet.get();
        } else {
            throw new VetNotFoundWithIdException(id);
        }
    }

    @Override
    public Vet addVet(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public Vet updateVet(Long id, Vet vet) {
        Vet updatedVet = getVet(id);

        updatedVet.setName(vet.getName());
        updatedVet.setVetCondition(vet.getVetCondition());
        updatedVet.setVetOption(vet.getVetOption());

        return vetRepository.save(updatedVet);
    }

    @Override
    public void deleteVet(Long id) {
        // TODO Auto-generated method stub

        vetRepository.deleteById(id);
    }

    @Override
    public void updateChildrenTableFirst(Long id) {
        List<Visit> visits = visitRepository.findByVetId(id);

        for (Visit visit : visits) {
            visit.setVet(null);
            visitRepository.save(visit);
        }

    }

    @Override
    public List<Vet> getAllVets() {
        // TODO Auto-generated method stub
        return (List<Vet>) vetRepository.findAll();
    }

    @Override
    public List<Vet> getVetsByStatus(String status) {
        List<Vet> vets = (List<Vet>) vetRepository.findAll();
        List<Vet> results = new ArrayList<>();
        VetStatus vetStatus = new VetStatus();
        for (Vet vet : vets) {
            if (vet.getVetCondition() == vetStatus.searchByStatus(status)) {
                results.add(vet);
            }
        }

        return results;
    }

    @Override
    public List<Vet> getVetsByType(String type) {

        List<Vet> vets = (List<Vet>) vetRepository.findAll();
        List<Vet> results = new ArrayList<>();
        VetType vetType = new VetType();
        for (Vet vet : vets) {
            if (vet.getVetOption() == vetType.searchWithOption(type)) {
                results.add(vet);
            }
        }

        return results;
    }

}
