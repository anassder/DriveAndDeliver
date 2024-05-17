package com.carrefour.driveanddeliver.service;

import com.carrefour.driveanddeliver.TimeSlotUnavailableException;
import com.carrefour.driveanddeliver.dto.DeliveryDTO;
import com.carrefour.driveanddeliver.mapper.DeliveryMapper;
import com.carrefour.driveanddeliver.mapper.DeliveryTimeSlotMapper;
import com.carrefour.driveanddeliver.model.Customer;
import com.carrefour.driveanddeliver.model.Delivery;
import com.carrefour.driveanddeliver.model.DeliveryMethod;
import com.carrefour.driveanddeliver.model.DeliveryTimeSlot;
import com.carrefour.driveanddeliver.repository.CustomerRepository;
import com.carrefour.driveanddeliver.repository.DeliveryRepository;
import com.carrefour.driveanddeliver.repository.DeliveryTimeSlotRepository;
import com.carrefour.driveanddeliver.service.impl.DeliveryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryTimeSlotMapper deliveryTimeSlotMapper;
    @Mock
    private DeliveryMapper deliveryMapper;
    @Mock
    private RabbitTemplate template;
    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private DeliveryTimeSlotRepository deliveryTimeSlotRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void getAvailableTimeSlot_GivenDeliveryMethod_ReturnSlots() {
        // GIVEN
        var deloveryMethod = DeliveryMethod.DELIVERY_ASAP;
        var timeSlotsMock = List.of(new DeliveryTimeSlot(1L,
                                                         "TIME_SLOT_1",
                                                         LocalDate.now(),
                                                         LocalTime.now(),
                                                         LocalTime.now(),
                                                         deloveryMethod,
                                                         5),
                                    new DeliveryTimeSlot(2L,
                                                         "TIME_SLOT_2",
                                                         LocalDate.now(),
                                                         LocalTime.now(),
                                                         LocalTime.now(),
                                                         deloveryMethod,
                                                         8),
                                    new DeliveryTimeSlot(3L,
                                                         "TIME_SLOT_3",
                                                         LocalDate.now(),
                                                         LocalTime.now(),
                                                         LocalTime.now(),
                                                         deloveryMethod,
                                                         3));

        when(deliveryTimeSlotRepository.findAvailableSLots(any(),
                                                           any(LocalDate.class),
                                                           any(LocalTime.class))).thenReturn(timeSlotsMock);

        // WHEN
        var timeSlots = deliveryService.getAvailableTimeSlot(deloveryMethod);

        // THEN
        assertNotNull(timeSlots);
        assertFalse(timeSlots.isEmpty());
        assertEquals(3, timeSlots.size());
    }

    @Test
    void book_givenValidCustomerAndTimeSlot_CreateDelivery() throws TimeSlotUnavailableException {
        // GIVEN
        var timeSlotCode = "SLOT_1";
        var customerEmail = "customer@test.com";

        var timeSlotMock = new DeliveryTimeSlot(3L,
                                                "TIME_SLOT_3",
                                                LocalDate.now(),
                                                LocalTime.now(),
                                                LocalTime.now(),
                                                DeliveryMethod.DELIVERY_ASAP,
                                                3);
        var customer = new Customer();

        when(deliveryTimeSlotRepository.findByCode(timeSlotCode)).thenReturn(timeSlotMock);
        when(deliveryTimeSlotRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(customerRepository.findByEmail(customerEmail)).thenReturn(customer);
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(i -> i.getArgument(0));
        doAnswer(invocationOnMock -> null).when(template).convertAndSend(anyString(), anyString(), any(DeliveryDTO.class));

        // WHEN
        deliveryService.book(customerEmail, timeSlotCode);

        // THEN
        assertTrue(true);
    }

    @Test
    void book_givenUnavailableTimeSlot_throwException() throws TimeSlotUnavailableException {
        // GIVEN
        var timeSlotCode = "SLOT_1";
        var customerEmail = "customer@test.com";

        var timeSlotMock = new DeliveryTimeSlot(3L,
                                                "TIME_SLOT_3",
                                                LocalDate.now(),
                                                LocalTime.now(),
                                                LocalTime.now(),
                                                DeliveryMethod.DELIVERY_ASAP,
                                                0);
        var customer = new Customer();

        when(deliveryTimeSlotRepository.findByCode(timeSlotCode)).thenReturn(timeSlotMock);
        when(deliveryTimeSlotRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(customerRepository.findByEmail(customerEmail)).thenReturn(customer);
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(i -> i.getArgument(0));
        doAnswer(invocationOnMock -> null).when(template).convertAndSend(anyString(), anyString(), any(DeliveryDTO.class));

        assertThrows(TimeSlotUnavailableException.class, () -> deliveryService.book(customerEmail, timeSlotCode));
    }
}