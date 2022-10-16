package com.flexpag.paymentscheduler.Services;

import com.flexpag.paymentscheduler.Domain.DTO.PaymentDTO;
import com.flexpag.paymentscheduler.Domain.Entities.Payment;

import java.util.List;

public interface PaymentService {

    PaymentDTO findByIdPayment(Integer id);

    List<PaymentDTO> findAllPayment();

    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO UpdatePayment(Integer id, PaymentDTO obj);

    void deletePayment(Integer id);
}