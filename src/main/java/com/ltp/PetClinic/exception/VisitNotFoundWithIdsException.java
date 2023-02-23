package com.ltp.PetClinic.exception;

public class VisitNotFoundWithIdsException extends RuntimeException {
    public VisitNotFoundWithIdsException(Long ownerId, Long petId) {
        super("This owner id " + ownerId + " or petId " + petId + " does not exist!");
    }
}
