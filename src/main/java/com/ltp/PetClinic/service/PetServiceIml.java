package com.ltp.PetClinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.PetClinic.entity.Owner;
import com.ltp.PetClinic.entity.Pet;
import com.ltp.PetClinic.exception.PetNotFoundWithIdsException;
import com.ltp.PetClinic.repository.PetRepository;

@Service
public class PetServiceIml implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerService ownerService;

    @Override
    public Pet getSinglePet(Long petId, Long ownerId) {
        Optional<Pet> pet = petRepository.findByIdAndOwnerId(petId, ownerId);

        if (pet.isPresent()) {
            return pet.get();
        } else {
            throw new PetNotFoundWithIdsException(petId, ownerId);
        }
    }

    @Override
    public Pet addPet(Long ownerId, Pet pet) {

        Owner owner = ownerService.getOwner(ownerId);

        pet.setOwner(owner);

        return petRepository.save(pet);

    }

    @Override
    public Pet updatePet(Long petId, Long ownerId, Pet pet) {
        Pet updatedPet = getSinglePet(petId, ownerId);

        updatedPet.setDate(pet.getDate());
        updatedPet.setPetOption(pet.getPetOption());

        return petRepository.save(updatedPet);

    }

    @Override
    public void deletePet(Long petId, Long ownerId) {
        // TODO Auto-generated method stub
        petRepository.deleteByIdAndOwnerId(petId, ownerId);
    }

    @Override
    public List<Pet> getAllPets() {
        return (List<Pet>) petRepository.findAll();
    }

    @Override
    public List<Pet> getAllPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
