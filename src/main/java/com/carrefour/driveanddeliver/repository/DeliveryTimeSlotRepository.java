package com.carrefour.driveanddeliver.repository;

import com.carrefour.driveanddeliver.model.DeliveryMethod;
import com.carrefour.driveanddeliver.model.DeliveryTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DeliveryTimeSlotRepository extends JpaRepository<DeliveryTimeSlot, Long> {

    DeliveryTimeSlot findByCode(String code);

    @Query(""" 
            from DeliveryTimeSlot
            where method = :method
            and (day > :date or (day = :date and start > :time))
            and remainingSlots > 0
            order by start
            """)
    List<DeliveryTimeSlot> findAvailableSLots(DeliveryMethod method, LocalDate date, LocalTime time);
}
