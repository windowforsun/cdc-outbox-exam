package com.windowforsun.cdc.outbox.order.controller;

import com.windowforsun.cdc.outbox.common.model.OrderLine;
import com.windowforsun.cdc.outbox.common.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
	private Long id;
	private String item;
	private int quantity;
	private long totalPrice;
	private OrderStatus status;

	public OrderLine toOrder() {
		return OrderLine.builder()
			.id(this.id)
			.item(this.item)
			.quantity(this.quantity)
			.totalPrice(this.totalPrice)
			.status(this.status)
			.build();
	}
}
