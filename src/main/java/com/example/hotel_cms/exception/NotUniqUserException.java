package com.example.hotel_cms.exception;

public class NotUniqUserException extends RuntimeException {
    public NotUniqUserException(String message) {
        super(message);
    }
}
