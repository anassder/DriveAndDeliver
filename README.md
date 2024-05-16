# 🚚 DriveAndDeliver - kata

This application allows users to choose a delivery time slots, and book one of the available slots.

## Features

The application offers the following features:

- Retrieve time slots: Users can get available time slots based on delivery method.
- Book: Users can book a time slot.

## Technologies Used

- Spring Boot 3.2.5
- Java 21
- Spring Data JPA
- Hibernate
- Mysql
- Mapstruct
- Lombok
- RabbitMq

## Initializing the Database

To initialize the database, you can use the `init-db.sql` located in `src/main/resources`.

## Setting up the Infrastructure

A `docker-compose.yml` file is available in the root directory of the project to set up the infrastructure needed for the application.

## REST Endpoints

### GET /delivery/{methodCode}/slots

Retrieve the list of available delivery slots time.

### GET /delivery/{customer}/book/{timeSlotCode}

book delivery slot time for customer.