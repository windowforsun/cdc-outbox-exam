package com.windowforsun.cdc.outbox.common.message;

import com.windowforsun.cdc.outbox.common.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateMessage {
	private long id;
	private String item;
	private int quantity;
	private long totalPrice;
	private OrderStatus status;
}
