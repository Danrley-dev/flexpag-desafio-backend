package com.flexpag.paymentscheduler.Domain.Entities;

import com.flexpag.paymentscheduler.Domain.Enums.StatusPayment;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_currentTime")
    private String currentTime;

    private String scheduledDate;

    private String scheduledHour;

    private Double depositValue;

    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;
}
