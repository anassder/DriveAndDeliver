package com.carrefour.driveanddeliver.mapper;

import com.carrefour.driveanddeliver.dto.CustomerDTO;
import com.carrefour.driveanddeliver.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDTO(Customer entity);
}
