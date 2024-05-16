package com.carrefour.driveanddeliver.service;

import com.carrefour.driveanddeliver.TimeSlotUnavailableException;
import com.carrefour.driveanddeliver.dto.DeliveryTimeSlotDTO;
import com.carrefour.driveanddeliver.model.DeliveryMethod;

import java.util.List;

public interface DeliveryService {

    List<DeliveryTimeSlotDTO> getAvailableTimeSlot(DeliveryMethod method);

    void book(String customerEmail, String timeSlotCode) throws TimeSlotUnavailableException;
}
