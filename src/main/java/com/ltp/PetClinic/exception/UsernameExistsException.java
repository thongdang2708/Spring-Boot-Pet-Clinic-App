package com.ltp.PetClinic.exception;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String exceptionText) {
        super("Fail to register " + exceptionText);
    }
}
