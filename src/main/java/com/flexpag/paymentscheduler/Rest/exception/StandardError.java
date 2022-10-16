package com.flexpag.paymentscheduler.Rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class StandardError {

    private LocalDateTime timeStamp;
    private Integer status;
    private String error;
    private String path;
}
