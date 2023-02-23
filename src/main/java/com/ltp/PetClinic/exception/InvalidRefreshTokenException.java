package com.ltp.PetClinic.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String exceptionText) {
        super(exceptionText);
    }
}
