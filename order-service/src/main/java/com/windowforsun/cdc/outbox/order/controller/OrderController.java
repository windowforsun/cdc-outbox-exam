package com.windowforsun.cdc.outbox.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.model.OrderLine;
import com.windowforsun.cdc.outbox.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping
	public OrderLine addOrder(@RequestBody CreateOrderRequest createOrderRequest) throws JsonProcessingException {
		return this.orderService.addOrder(createOrderRequest.toOrder());
	}

	@PutMapping
	public OrderLine updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest) throws JsonProcessingException {
		return this.orderService.updateOrder(updateOrderRequest.getOrderId(), updateOrderRequest.getNewStatus());
	}
}
