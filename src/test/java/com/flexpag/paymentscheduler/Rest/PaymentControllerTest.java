package com.flexpag.paymentscheduler.Rest;

import com.flexpag.paymentscheduler.Domain.DTO.PaymentDTO;
import com.flexpag.paymentscheduler.Domain.Entities.Payment;
import com.flexpag.paymentscheduler.Domain.Enums.StatusPayment;
import com.flexpag.paymentscheduler.Services.Impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentControllerTest {

    private Payment payment;
    private PaymentDTO paymentDTO;

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PaymentServiceImpl service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startPayment();
    }

    @Test
    void whenFindByIdPaymentThenReturnSucess() {
        when(service.findByIdPayment(anyInt())).thenReturn(paymentDTO);
        when(mapper.map(any(), any())).thenReturn(payment);

        ResponseEntity<PaymentDTO> response = paymentController.findByIdPayment(1);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PaymentDTO.class, response.getBody().getClass());

        assertEquals(1, response.getBody().getId());
        assertEquals("20/07/2022", response.getBody().getScheduledDate());
        assertEquals("10:45", response.getBody().getScheduledHour());
        assertEquals(1200.0, response.getBody().getDepositValue());
        assertEquals(payment.getStatusPayment().toString(), response.getBody().getStatusPayment());
    }

    @Test
    void whenFindAllPaymentThenRetunListOfPaymentDTO() {
        when(service.findAllPayment()).thenReturn(List.of(paymentDTO));
        when(mapper.map(any(), any())).thenReturn(payment);

        ResponseEntity<List<PaymentDTO>> response = paymentController.findAllPayment();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PaymentDTO.class, response.getBody().get(0).getClass());

        assertEquals(1, response.getBody().get(0).getId());
        assertEquals("20/07/2022", response.getBody().get(0).getScheduledDate());
        assertEquals("10:45", response.getBody().get(0).getScheduledHour());
        assertEquals(1200.0, response.getBody().get(0).getDepositValue());
        assertEquals(payment.getStatusPayment().toString(), response.getBody().get(0).getStatusPayment());
    }

    @Test
    void whenPaymentRecordThenReturnCreated() {
        when(service.createPayment(any())).thenReturn(paymentDTO);

        ResponseEntity<PaymentDTO> response = paymentController.paymentRecord(paymentDTO);

        assertNotNull(response.getHeaders().get("location"));
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void whenUpdatePaymentThenReturnSucess() {
        when(service.UpdatePayment(payment.getId(), paymentDTO)).thenReturn(paymentDTO);
        when(mapper.map(any(), any())).thenReturn(payment);

        ResponseEntity<PaymentDTO> response = paymentController.updatePayment(1, paymentDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PaymentDTO.class, response.getBody().getClass());

        assertEquals(1, response.getBody().getId());
        assertEquals("20/07/2022", response.getBody().getScheduledDate());
        assertEquals("10:45", response.getBody().getScheduledHour());
        assertEquals(1200.0, response.getBody().getDepositValue());
        assertEquals(payment.getStatusPayment().toString(), response.getBody().getStatusPayment());
    }

    @Test
    void deletePayment() {
        doNothing().when(service).deletePayment(anyInt());

        ResponseEntity<PaymentDTO> response = paymentController.deletePayment(1);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).deletePayment(anyInt());
    }

    private void startPayment(){
        payment = new Payment(1, LocalTime.now().toString(), "20/07/2022", "10:45",
                1200.0, StatusPayment.PENDING);

        paymentDTO = new PaymentDTO(1, LocalTime.now().toString(), "20/07/2022",
                "10:45", 1200.0, "PENDING");
    }
}