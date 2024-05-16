package com.carrefour.driveanddeliver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(catalog = "drive_and_deliver")
@EntityListeners(AuditingEntityListener.class)
public class Delivery {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = 45)
    private String code;

    @CreatedDate
    @Column(name = "create_date", nullable = false, length = 45)
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "time_slot_id", referencedColumnName = "id", nullable = false)
    private DeliveryTimeSlot deliveryTimeSlot;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
}
