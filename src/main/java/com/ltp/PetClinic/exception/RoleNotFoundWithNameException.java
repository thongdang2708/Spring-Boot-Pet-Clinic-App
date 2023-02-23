package com.ltp.PetClinic.exception;

public class RoleNotFoundWithNameException extends RuntimeException {
    public RoleNotFoundWithNameException(String exceptionText) {
        super(exceptionText);
    }
}
