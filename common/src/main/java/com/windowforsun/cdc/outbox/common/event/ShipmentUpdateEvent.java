package com.windowforsun.cdc.outbox.common.event;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.message.ShipmentUpdateMessage;
import com.windowforsun.cdc.outbox.common.model.ShipmentStatus;

public class ShipmentUpdateEvent implements OutboxEvent {
	private final ShipmentUpdateMessage payload;
	private final Instant timestamp;

	public ShipmentUpdateEvent(long shipmentId, long orderId, ShipmentStatus newStatus, ShipmentStatus oldStatus) {
		this.payload = ShipmentUpdateMessage.builder()
			.shipmentId(shipmentId)
			.orderId(orderId)
			.newStatus(newStatus)
			.oldStatus(oldStatus)
			.build();
		this.timestamp = Instant.now();
	}

	public static ShipmentUpdateEvent of(long shipmentId, long orderId, ShipmentStatus newStatus, ShipmentStatus oldStatus) {
		return new ShipmentUpdateEvent(shipmentId, orderId,  newStatus, oldStatus);
	}

	@Override
	public String getAggregateId() {
		return String.valueOf(this.payload.getShipmentId());
	}

	@Override
	public String getAggregateType() {
		return "Shipment";
	}

	@Override
	public String getOutboxPayload() throws JsonProcessingException {
		return OutboxEvent.objectMapper.writeValueAsString(this.payload);
	}

	@Override
	public String getType() {
		return "ShipmentUpdate";
	}

	@Override
	public Instant getTimestamp() {
		return this.timestamp;
	}
}
