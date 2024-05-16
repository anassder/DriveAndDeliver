package com.carrefour.driveanddeliver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DriveAndDeliverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriveAndDeliverApplication.class, args);
    }

}
