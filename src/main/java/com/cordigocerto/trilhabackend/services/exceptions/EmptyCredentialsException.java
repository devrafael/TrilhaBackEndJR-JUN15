package com.cordigocerto.trilhabackend.services.exceptions;

public class EmptyCredentialsException extends RuntimeException {

    public EmptyCredentialsException(String message) {
        super(message);
    }

}
