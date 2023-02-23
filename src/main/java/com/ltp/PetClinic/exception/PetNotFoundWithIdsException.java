package com.ltp.PetClinic.exception;

public class PetNotFoundWithIdsException extends RuntimeException {
    public PetNotFoundWithIdsException(Long petId, Long ownerId) {
        super("This pet with id " + petId + " or owner id " + ownerId + " cannot be found!");
    }
}
