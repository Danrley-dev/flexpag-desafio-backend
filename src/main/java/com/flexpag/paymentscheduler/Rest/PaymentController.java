package com.flexpag.paymentscheduler.Rest;

import com.flexpag.paymentscheduler.Domain.DTO.PaymentDTO;
import com.flexpag.paymentscheduler.Services.Impl.PaymentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping( "/{id}")
    public ResponseEntity<PaymentDTO> findByIdPayment(@PathVariable Integer id){
        return ResponseEntity.ok().body(service.findByIdPayment(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> findAllPayment(){
        return ResponseEntity.ok().body(service.findAllPayment());
    }

    @PostMapping
    public ResponseEntity paymentRecord(@RequestBody PaymentDTO paymentDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(service.createPayment(paymentDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping( "/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Integer id, @RequestBody PaymentDTO obj){
        return ResponseEntity.ok().body(service.UpdatePayment(id, obj));
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity deletePayment(@PathVariable Integer id){
        service.deletePayment(id);
        return ResponseEntity.ok().build();
    }

}
