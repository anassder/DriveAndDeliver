-- -----------------------------------------------------
-- Schema drive_and_deliver
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `drive_and_deliver` DEFAULT CHARACTER SET utf8;
USE `drive_and_deliver`;

-- -----------------------------------------------------
-- Table `drive_and_deliver`.`delivery_time_slot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `drive_and_deliver`.`delivery_time_slot`;

CREATE TABLE IF NOT EXISTS drive_and_deliver.delivery_time_slot
(
    id              INT AUTO_INCREMENT
        PRIMARY KEY,
    code            VARCHAR(20)   NOT NULL,
    start           TIME          NOT NULL,
    end             TIME          NOT NULL,
    day             DATE          NOT NULL,
    remaining_slots INT DEFAULT 0 NOT NULL,
    method          INT DEFAULT 0 NOT NULL
);

INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (1, 'TIME_SLOT_1', '10:00:00', '12:00:00', '2024-05-17', 10, 0);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (2, 'TIME_SLOT_2', '10:00:00', '12:00:00', '2024-05-15', 10, 1);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (3, 'TIME_SLOT_3', '12:00:00', '14:00:00', '2024-05-15', 10, 1);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (4, 'TIME_SLOT_4', '14:00:00', '16:00:00', '2024-05-16', 6, 2);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (5, 'TIME_SLOT_5', '17:00:00', '18:00:00', '2024-05-17', 3, 0);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (6, 'TIME_SLOT_6', '10:00:00', '12:00:00', '2024-05-17', 10, 0);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (7, 'TIME_SLOT_7', '12:00:00', '14:00:00', '2024-05-17', 9, 2);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (8, 'TIME_SLOT_8', '14:00:00', '16:00:00', '2024-05-17', 6, 3);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (9, 'TIME_SLOT_9', '17:00:00', '18:00:00', '2024-05-17', 3, 3);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (10, 'TIME_SLOT_10', '20:00:00', '21:00:00', '2024-05-17', 9, 0);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (11, 'TIME_SLOT_11', '21:30:00', '22:00:00', '2024-05-17', 10, 0);
INSERT INTO drive_and_deliver.delivery_time_slot (id, code, start, end, day, remaining_slots, method)
VALUES (12, 'TIME_SLOT_12', '22:00:00', '23:00:00', '2024-05-17', 1, 3);


-- -----------------------------------------------------
-- Table `drive_and_deliver`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `drive_and_deliver`.`customer`;

CREATE TABLE IF NOT EXISTS drive_and_deliver.customer
(
    id         INT          NOT NULL
        PRIMARY KEY,
    email      VARCHAR(45)  NOT NULL,
    first_name VARCHAR(45)  NOT NULL,
    last_name  VARCHAR(45)  NOT NULL,
    address    VARCHAR(200) NOT NULL,
    CONSTRAINT email_UNIQUE
        UNIQUE (email)
);

INSERT INTO drive_and_deliver.customer (id, email, first_name, last_name, address)
VALUES (1, 'anass@test.com', 'Anass', 'DER', '21 Rue Test 69100');


-- -----------------------------------------------------
-- Table `drive_and_deliver`.`delivery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `drive_and_deliver`.`delivery`;

CREATE TABLE IF NOT EXISTS drive_and_deliver.delivery
(
    id           INT AUTO_INCREMENT
        PRIMARY KEY,
    code         VARCHAR(45) NOT NULL,
    time_slot_id INT         NOT NULL,
    customer_id  INT         NOT NULL,
    create_date  DATETIME    NULL,
    CONSTRAINT fk_delivery_customer
        FOREIGN KEY (customer_id) REFERENCES drive_and_deliver.customer (id),
    CONSTRAINT fk_delivery_delivery_time_slot1
        FOREIGN KEY (time_slot_id) REFERENCES drive_and_deliver.delivery_time_slot (id)
);

