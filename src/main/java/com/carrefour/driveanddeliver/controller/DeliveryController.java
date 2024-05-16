package com.carrefour.driveanddeliver.controller;

import com.carrefour.driveanddeliver.TimeSlotUnavailableException;
import com.carrefour.driveanddeliver.dto.DeliveryTimeSlotDTO;
import com.carrefour.driveanddeliver.model.DeliveryMethod;
import com.carrefour.driveanddeliver.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/delivery/{methodCode}/slots")
    public List<DeliveryTimeSlotDTO> getDeliveryAvailableTimeSlots(@PathVariable("methodCode") DeliveryMethod method) {
        return deliveryService.getAvailableTimeSlot(method);
    }

    @GetMapping("/delivery/{customer}/book/{timeSlotCode}")
    public void getDeliveryAvailableTimeSlots(@PathVariable("customer") String customerEmail, @PathVariable("timeSlotCode") String timeSlotCode) throws TimeSlotUnavailableException {
        deliveryService.book(customerEmail, timeSlotCode);
    }
}
