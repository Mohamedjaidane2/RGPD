package com.example.rgpdplatform.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException{

    @Getter
    private final ErrorCodes errorCode;
    @Getter
    private final String message;

    public EntityNotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}

