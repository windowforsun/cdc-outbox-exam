package com.windowforsun.cdc.outbox.shipment.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.event.ShipmentUpdateEvent;
import com.windowforsun.cdc.outbox.common.message.OrderCreateMessage;
import com.windowforsun.cdc.outbox.common.message.OrderUpdateMessage;
import com.windowforsun.cdc.outbox.common.model.Outbox;
import com.windowforsun.cdc.outbox.common.model.Shipment;
import com.windowforsun.cdc.outbox.common.model.ShipmentStatus;
import com.windowforsun.cdc.outbox.common.repository.OutboxRepository;
import com.windowforsun.cdc.outbox.common.service.OutboxService;
import com.windowforsun.cdc.outbox.shipment.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {
	private final ShipmentRepository shipmentRepository;
	private final OutboxService outboxService;

	public Shipment addShipment(OrderCreateMessage orderCreateEvent) {
		Shipment shipment = Shipment.builder()
			.orderId(orderCreateEvent.getId())
			.status(ShipmentStatus.ENTERED)
			.build();

		return this.shipmentRepository.save(shipment);
	}

	public Shipment updateShipment(OrderUpdateMessage orderUpdateEvent) {
		Shipment shipment = this.shipmentRepository.findByOrderId(orderUpdateEvent.getOrderId());

		if (shipment == null) {
			return null;
		}

		switch (orderUpdateEvent.getNewStatus()) {
			case ENTERED:
				shipment.setStatus(ShipmentStatus.ENTERED);
				break;
			case CANCELLED:
				shipment.setStatus(ShipmentStatus.CANCELED);
				break;
			case SHIPPED:
				shipment.setStatus(ShipmentStatus.DONE);
				break;
		}

		return this.shipmentRepository.save(shipment);
	}

	@Transactional
	public Shipment updateShipment(long shipmentId, ShipmentStatus newStatus) throws JsonProcessingException {
		Shipment shipment = this.shipmentRepository.findById(shipmentId).orElse(null);

		if (shipment == null) {
			throw new RuntimeException("shipment id " + shipmentId + " not found");
		}

		ShipmentStatus oldStatus = shipment.getStatus();
		shipment.setStatus(newStatus);
		shipment = this.shipmentRepository.save(shipment);

		ShipmentUpdateEvent shipmentUpdateEvent = ShipmentUpdateEvent.of(shipmentId, shipment.getOrderId(), newStatus, oldStatus);
		this.outboxService.exportEvent(Outbox.of(shipmentUpdateEvent));

		return shipment;
	}

}
