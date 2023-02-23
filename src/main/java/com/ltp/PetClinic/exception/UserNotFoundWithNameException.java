package com.ltp.PetClinic.exception;

public class UserNotFoundWithNameException extends RuntimeException {
    public UserNotFoundWithNameException(String exceptionText) {
        super(exceptionText);
    }
}
