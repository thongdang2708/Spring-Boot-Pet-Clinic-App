package com.ltp.PetClinic.service;

import com.ltp.PetClinic.entity.Vet;
import java.util.List;

public interface VetService {
    Vet getVet(Long id);

    Vet addVet(Vet vet);

    Vet updateVet(Long id, Vet vet);

    void deleteVet(Long id);

    List<Vet> getAllVets();

    void updateChildrenTableFirst(Long id);

    List<Vet> getVetsByStatus(String status);

    List<Vet> getVetsByType(String type);

}
