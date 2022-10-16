package com.flexpag.paymentscheduler.Services.exception;

public class EmptyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmptyException(String message){
        super(message);
    }
}
