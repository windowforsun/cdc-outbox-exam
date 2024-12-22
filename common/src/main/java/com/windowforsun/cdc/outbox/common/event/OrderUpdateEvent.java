package com.windowforsun.cdc.outbox.common.event;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.message.OrderUpdateMessage;
import com.windowforsun.cdc.outbox.common.model.OrderStatus;

import lombok.Getter;

@Getter
public class OrderUpdateEvent implements OutboxEvent {
	private final OrderUpdateMessage payload;
	private final Instant timestamp;

	private OrderUpdateEvent(long orderId, OrderStatus newStatus, OrderStatus oldStatus) {
		this.payload = OrderUpdateMessage.builder()
			.orderId(orderId)
			.newStatus(newStatus)
			.oldStatus(oldStatus)
			.build();
		this.timestamp = Instant.now();
	}

	public static OrderUpdateEvent of(long orderId, OrderStatus newStatus, OrderStatus oldStatus) {
		return new OrderUpdateEvent(orderId, newStatus, oldStatus);
	}

	@Override
	public String getAggregateId() {
		return String.valueOf(this.payload.getOrderId());
	}

	@Override
	public String getAggregateType() {
		return "Order";
	}

	@Override
	public String getOutboxPayload() throws JsonProcessingException {
		return OutboxEvent.objectMapper.writeValueAsString(this.payload);
	}

	@Override
	public String getType() {
		return "OrderUpdate";
	}

	@Override
	public Instant getTimestamp() {
		return timestamp;
	}
}
