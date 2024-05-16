package com.carrefour.driveanddeliver.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryDTO {

    private String code;
    private LocalDateTime createDate;
    private DeliveryTimeSlotDTO deliveryTimeSlot;
    private CustomerDTO customer;
}
