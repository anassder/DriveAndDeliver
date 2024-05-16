package com.carrefour.driveanddeliver.mapper;

import com.carrefour.driveanddeliver.dto.DeliveryDTO;
import com.carrefour.driveanddeliver.model.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryDTO toDTO(Delivery entity);
}
