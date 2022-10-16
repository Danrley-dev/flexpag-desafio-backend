package com.flexpag.paymentscheduler.Repositories;

import com.flexpag.paymentscheduler.Domain.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
