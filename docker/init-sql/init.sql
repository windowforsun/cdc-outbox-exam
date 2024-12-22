create database exam;

use exam;

CREATE TABLE `order_line`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `item`       VARCHAR(255) NOT NULL,
    `quantity`   INT          NOT NULL,
    `total_price` BIGINT       NOT NULL,
    `status`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `outbox`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT,
    `aggregatetype` VARCHAR(255) NOT NULL,
    `aggregateid`   VARCHAR(255) NOT NULL,
    `type`          VARCHAR(255) NOT NULL,
    `payload`       TEXT         NOT NULL,
    PRIMARY KEY (`id`)
);



CREATE TABLE `consumed_message`
(
    `event_id`            BIGINT       NOT NULL,
    `time_of_received` DATETIME(6) NOT NULL,
    PRIMARY KEY (`event_id`)
);


CREATE TABLE `shipment`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT NOT NULL,
    `status`   VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);