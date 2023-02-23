package com.ltp.PetClinic.exception;

public class VetNotFoundWithIdException extends RuntimeException {
    public VetNotFoundWithIdException(Long id) {
        super("This vet id " + id + " does not exist!");
    }
}
