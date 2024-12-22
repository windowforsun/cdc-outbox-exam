package com.windowforsun.cdc.outbox.shipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.windowforsun.cdc.outbox.common.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	Shipment findByOrderId(long orderId);
}
