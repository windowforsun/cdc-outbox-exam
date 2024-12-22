package com.windowforsun.cdc.outbox.order.controller;

import com.windowforsun.cdc.outbox.common.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
	private long orderId;
	private OrderStatus newStatus;
}
