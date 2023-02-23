package com.ltp.PetClinic.exception;

public class VisitNotFoundWithIdException extends RuntimeException {
    public VisitNotFoundWithIdException(Long id) {
        super("This visit id " + id + " does not exist!");
    }
}
