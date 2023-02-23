package com.ltp.PetClinic.storageObject;

public class MessageExceptionObject {
    private String message;

    public MessageExceptionObject(String message) {
        this.message = message;
    }

    public MessageExceptionObject() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
