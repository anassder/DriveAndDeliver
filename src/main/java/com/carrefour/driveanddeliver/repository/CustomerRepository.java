package com.carrefour.driveanddeliver.repository;

import com.carrefour.driveanddeliver.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByEmail(String email);
}
