package com.ltp.PetClinic.exception;

public class VisitExistsWithOwnerIdAndPetIdException extends RuntimeException {
    public VisitExistsWithOwnerIdAndPetIdException(String exceptionText) {
        super(exceptionText);
    }
}
