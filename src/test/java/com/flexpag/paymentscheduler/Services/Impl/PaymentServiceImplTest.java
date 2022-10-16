package com.flexpag.paymentscheduler.Services.Impl;

import com.flexpag.paymentscheduler.Domain.DTO.PaymentDTO;
import com.flexpag.paymentscheduler.Domain.Entities.Payment;
import com.flexpag.paymentscheduler.Domain.Enums.StatusPayment;
import com.flexpag.paymentscheduler.Repositories.PaymentRepository;
import com.flexpag.paymentscheduler.Services.exception.DataIntegratyViolationException;
import com.flexpag.paymentscheduler.Services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceImplTest {

    private Payment payment;
    private PaymentDTO paymentDTO;

    @Mock
    private PaymentRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PaymentServiceImpl service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startPayment();
    }


    @Test
    void whenFindByIdPaymentThenReturnUserInstace() {
        Payment payment1 = getPaymentId();
        when(repository.findById(anyInt())).thenReturn(Optional.ofNullable(payment1));
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Object not Found"));

        try{
            service.findByIdPayment(1);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Object not Found", ex.getMessage());
        }
    }

    @Test
    void whenFindAllPaymentThenReturnAnListOfPayment() {
        List<Payment> paymentList = getListPayment();
        when(repository.findAll()).thenReturn(paymentList);

        List<PaymentDTO> response = service.findAllPayment();

        for (int i = 0; i < getListPayment().size(); i++){
            assertEquals(paymentList.get(i).getId(), i + 1);
            assertEquals(paymentList.get(i).getScheduledDate(), "10/10/2022");
            assertEquals(paymentList.get(i).getScheduledHour(), "10:40");
            assertEquals(paymentList.get(i).getDepositValue(), 1200.0);
            assertEquals(paymentList.get(i).getStatusPayment(), StatusPayment.PENDING);
        }

    }

    @Test
    void whenCreatePaymentThenReturnSucess() {
        when(repository.save(any())).thenReturn(payment);
    }

    @Test
    void updatePayment() {
        when(repository.findById(anyInt())).thenReturn(Optional.ofNullable(payment));

    }

    @Test
    void deletePayment() {
        when(repository.findById(anyInt())).thenReturn(Optional.ofNullable(payment));
        doNothing().when(repository).deleteById(anyInt());
    }

    public List<Payment> getListPayment(){
        List<Payment> paymentList = new LinkedList<>();
        for (int i = 1; i < 5; i++){
            Payment payment1 = new Payment();

            payment1.setId(i);
            payment1.setScheduledDate("10/10/2022");
            payment1.setScheduledHour("10:40");
            payment1.setDepositValue(1200.0);
            payment1.setStatusPayment(StatusPayment.PENDING);

            paymentList.add(payment1);
        }
        return paymentList;
    }

    public Payment getPaymentId(){
        Payment payment1 = new Payment();
        payment1.setId(1);
        payment1.setCurrentTime(LocalDate.now().toString());
        payment1.setScheduledDate("10/10/2022");
        payment1.setScheduledHour("10:40");
        payment1.setDepositValue(1200.0);
        payment1.setStatusPayment(StatusPayment.PENDING);

        return payment1;
    }

    private void startPayment(){
        payment = new Payment(1, LocalTime.now().toString(), "20/07/2022", "10:45",
                1200.0, StatusPayment.PENDING);

        paymentDTO = new PaymentDTO(1, LocalTime.now().toString(), "20/07/2022",
                "10:45", 1200.0, "PENDING");
    }
}