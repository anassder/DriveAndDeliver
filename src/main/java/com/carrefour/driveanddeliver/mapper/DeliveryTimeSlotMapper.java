package com.carrefour.driveanddeliver.mapper;

import com.carrefour.driveanddeliver.dto.DeliveryTimeSlotDTO;
import com.carrefour.driveanddeliver.model.DeliveryTimeSlot;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryTimeSlotMapper {

    DeliveryTimeSlotDTO toDTO(DeliveryTimeSlot entity);

    List<DeliveryTimeSlotDTO> toDTO(List<DeliveryTimeSlot> entities);
}
