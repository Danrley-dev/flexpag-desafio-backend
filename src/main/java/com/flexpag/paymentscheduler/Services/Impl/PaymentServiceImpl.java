package com.flexpag.paymentscheduler.Services.Impl;

import com.flexpag.paymentscheduler.Domain.DTO.PaymentDTO;
import com.flexpag.paymentscheduler.Domain.Entities.Payment;
import com.flexpag.paymentscheduler.Domain.Enums.StatusPayment;
import com.flexpag.paymentscheduler.Repositories.PaymentRepository;
import com.flexpag.paymentscheduler.Services.PaymentService;
import com.flexpag.paymentscheduler.Services.exception.DataIntegratyViolationException;
import com.flexpag.paymentscheduler.Services.exception.EmptyException;
import com.flexpag.paymentscheduler.Services.exception.ObjectNotFoundException;
import com.flexpag.paymentscheduler.Services.exception.StatusPaymentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public PaymentDTO findByIdPayment(Integer id) {
        Optional<Payment> obj = repository.findById(id);
        if(!obj.isPresent()){
           throw new ObjectNotFoundException("object not found");
        }
        return mapper.map(obj.get(), PaymentDTO.class);
    }

    public List<PaymentDTO> findAllPayment(){
        return repository.findAll().stream()
                .map(x -> mapper.map(x, PaymentDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        if (paymentDTO.getDepositValue() == null){
            throw new EmptyException("invalid, some field is empty");
        }
        paymentDTO.setCurrentTime(LocalTime.now().toString());
        paymentDTO.setScheduledDate(convertStringToDate(paymentDTO.getScheduledDate()).toString());
        paymentDTO.setStatusPayment(String.valueOf(StatusPayment.PENDING));

        Payment payment = repository.save(mapper.map(paymentDTO, Payment.class));
        return  mapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO UpdatePayment(Integer id, PaymentDTO paymentDTO) {
        PaymentDTO obj = findByIdPayment(id);
        statusPayment(obj);
        PaymentDTO byIdPayment = null;
        try {
            if (paymentDTO.getDepositValue() == null) {
                throw new EmptyException("invalid, some field is empty");
            }
            byIdPayment = findByIdPayment(id);
            byIdPayment.setDepositValue(paymentDTO.getDepositValue());
            byIdPayment.setScheduledDate(convertStringToDate(paymentDTO.getScheduledDate()).toString());
            byIdPayment.setScheduledHour(convertStringToHour(paymentDTO.getScheduledHour()).toString());
        } catch (ParseException e) {
            e.getMessage();
        }
        Payment payment = repository.saveAndFlush(mapper.map(byIdPayment, Payment.class));
        return mapper.map(payment, PaymentDTO.class);
    }

    public void deletePayment(Integer id){
        PaymentDTO payment = findByIdPayment(id);
        statusPayment(payment);
        repository.deleteById(id);
    }

    public LocalDate convertStringToDate(String date) {
        try{
            if(date != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(date, formatter);
                return localDate;
            }
        } catch (DataIntegratyViolationException e){
            throw new DataIntegratyViolationException
                    ("invalid date, try again with the following pattern: (day/month/year)");
        }

        return null;
    }

    public LocalTime convertStringToHour(String hour) throws ParseException {
        try {
            if(hour != null){
                String[] split = hour.split(":");
                LocalTime locaTime = LocalTime.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
                return locaTime;
            }
        } catch (DataIntegratyViolationException e){
            throw new DataIntegratyViolationException
                    ("invalid hour, try again with the following pattern: (hour:minutes)");
        }
        return null;
    }

    private void statusPayment(PaymentDTO paymentDTO){
        System.out.println(paymentDTO);
        if (paymentDTO.getStatusPayment().equals(String.valueOf(StatusPayment.PAID))){
            throw new StatusPaymentException("unable to delete or edit an added payment");
        }
    }
}
