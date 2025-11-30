package org.example.excptions;

public class PhoneNumberNotValidException extends RuntimeException{

    public PhoneNumberNotValidException(String message) {
        super(message);
    }

}
