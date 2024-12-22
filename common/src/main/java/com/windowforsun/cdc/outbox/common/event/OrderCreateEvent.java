package com.windowforsun.cdc.outbox.common.event;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.message.OrderCreateMessage;
import com.windowforsun.cdc.outbox.common.model.OrderLine;

public class OrderCreateEvent  implements OutboxEvent{
	private final OrderCreateMessage orderCreateMessage;
	private final Instant timestamp;

	private OrderCreateEvent(long id, OrderLine order) {
		this.orderCreateMessage = OrderCreateMessage.builder()
			.id(order.getId())
			.item(order.getItem())
			.status(order.getStatus())
			.quantity(order.getQuantity())
			.totalPrice(order.getTotalPrice())
			.build();
		this.timestamp = Instant.now();
	}

	public static OrderCreateEvent of(OrderLine order) {
		return new OrderCreateEvent(order.getId(), order);
	}

	@Override
	public String getAggregateId() {
		return String.valueOf(this.orderCreateMessage.getId());
	}

	@Override
	public String getAggregateType() {
		return "Order";
	}

	@Override
	public String getOutboxPayload() throws JsonProcessingException {
		return OutboxEvent.objectMapper.writeValueAsString(this.orderCreateMessage);
	}

	@Override
	public String getType() {
		return "OrderCreate";
	}

	@Override
	public Instant getTimestamp() {
		return this.timestamp;
	}
}
