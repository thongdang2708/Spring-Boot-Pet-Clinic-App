package com.ltp.PetClinic.exception;

public class InvalidInputDueToEnum extends RuntimeException {
    public InvalidInputDueToEnum(String exceptionText) {
        super(exceptionText);
    }
}
