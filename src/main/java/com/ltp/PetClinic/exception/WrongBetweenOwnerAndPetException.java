package com.ltp.PetClinic.exception;

public class WrongBetweenOwnerAndPetException extends RuntimeException {
    public WrongBetweenOwnerAndPetException(Long ownerId, Long petId) {
        super("This pet id " + " does not belong to " + ownerId);
    }
}
