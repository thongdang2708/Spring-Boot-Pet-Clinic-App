package com.ltp.PetClinic.service;

import com.ltp.PetClinic.entity.Owner;
import com.ltp.PetClinic.entity.Pet;
import java.util.List;

public interface PetService {
    Pet getSinglePet(Long petId, Long ownerId);

    Pet addPet(Long ownerId, Pet pet);

    Pet updatePet(Long petId, Long ownerId, Pet pet);

    void deletePet(Long petId, Long ownerId);

    List<Pet> getAllPetsByOwner(Long ownerId);

    List<Pet> getAllPets();

}
