package com.ltp.PetClinic.exception;

public class RoleNotFoundWithIdException extends RuntimeException {
    public RoleNotFoundWithIdException(Long id) {
        super("This role id " + id + " does not exist!");
    }
}
