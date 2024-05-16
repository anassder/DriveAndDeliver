package com.carrefour.driveanddeliver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryTimeSlotDTO {

    private String code;
    private LocalDate day;
    private LocalTime start;
    private LocalTime end;
}
