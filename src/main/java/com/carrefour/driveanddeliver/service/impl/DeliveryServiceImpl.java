package com.carrefour.driveanddeliver.service.impl;

import com.carrefour.driveanddeliver.TimeSlotUnavailableException;
import com.carrefour.driveanddeliver.dto.DeliveryTimeSlotDTO;
import com.carrefour.driveanddeliver.mapper.DeliveryMapper;
import com.carrefour.driveanddeliver.mapper.DeliveryTimeSlotMapper;
import com.carrefour.driveanddeliver.model.Delivery;
import com.carrefour.driveanddeliver.model.DeliveryMethod;
import com.carrefour.driveanddeliver.repository.CustomerRepository;
import com.carrefour.driveanddeliver.repository.DeliveryRepository;
import com.carrefour.driveanddeliver.repository.DeliveryTimeSlotRepository;
import com.carrefour.driveanddeliver.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final RabbitTemplate template;

    private final DeliveryRepository deliveryRepository;
    private final DeliveryTimeSlotRepository deliveryTimeSlotRepository;
    private final CustomerRepository customerRepository;

    private final DeliveryTimeSlotMapper deliveryTimeSlotMapper;
    private final DeliveryMapper deliveryMapper;

    @Value("${amqp.exchange.name}")
    public String exchangeName;

    @Override
    public List<DeliveryTimeSlotDTO> getAvailableTimeSlot(DeliveryMethod method) {
        var timeSlots = deliveryTimeSlotRepository.findAvailableSLots(method, LocalDate.now(), LocalTime.now());
        return deliveryTimeSlotMapper.toDTO(timeSlots);
    }

    @Override
    public void book(String customerEmail, String timeSlotCode) throws TimeSlotUnavailableException {
        var timeSlot = deliveryTimeSlotRepository.findByCode(timeSlotCode);

        if (timeSlot.getRemainingSlots() <= 0) {
            throw new TimeSlotUnavailableException("This time slot is no longer available");
        }

        timeSlot.setRemainingSlots(timeSlot.getRemainingSlots() - 1);
        deliveryTimeSlotRepository.save(timeSlot);

        var delivery = new Delivery();
        delivery.setCode(UUID.randomUUID().toString());

        var customer = customerRepository.findByEmail(customerEmail);
        delivery.setCustomer(customer);

        delivery.setDeliveryTimeSlot(timeSlot);

        var savedDelivery = deliveryRepository.save(delivery);
        var deliveryDTO = deliveryMapper.toDTO(savedDelivery);

        template.convertAndSend(exchangeName, timeSlot.getMethod().name(), deliveryDTO);
    }
}
