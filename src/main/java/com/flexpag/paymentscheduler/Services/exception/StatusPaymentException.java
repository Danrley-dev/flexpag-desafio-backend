package com.flexpag.paymentscheduler.Services.exception;

public class StatusPaymentException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public StatusPaymentException(String message){
        super(message);
    }
}
