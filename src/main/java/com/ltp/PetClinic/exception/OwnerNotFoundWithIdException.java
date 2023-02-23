package com.ltp.PetClinic.exception;

public class OwnerNotFoundWithIdException extends RuntimeException {
    public OwnerNotFoundWithIdException(Long id) {
        super("This owner id " + id + " does not exist!");
    }
}
