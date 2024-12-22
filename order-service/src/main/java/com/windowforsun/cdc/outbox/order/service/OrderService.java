package com.windowforsun.cdc.outbox.order.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.event.OrderCreateEvent;
import com.windowforsun.cdc.outbox.common.event.OrderUpdateEvent;
import com.windowforsun.cdc.outbox.common.message.ShipmentUpdateMessage;
import com.windowforsun.cdc.outbox.common.model.OrderLine;
import com.windowforsun.cdc.outbox.common.model.OrderStatus;
import com.windowforsun.cdc.outbox.common.model.Outbox;
import com.windowforsun.cdc.outbox.common.model.ShipmentStatus;
import com.windowforsun.cdc.outbox.common.repository.OutboxRepository;
import com.windowforsun.cdc.outbox.common.service.OutboxService;
import com.windowforsun.cdc.outbox.order.repository.OrderLineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderLineRepository orderRepository;
	private final OutboxService outboxService;

	@Transactional
	public OrderLine addOrder(OrderLine order) throws JsonProcessingException {
		log.info("addOrder {}", order);
		this.orderRepository.save(order);

		OrderCreateEvent orderCreateEvent = OrderCreateEvent.of(order);

		this.outboxService.exportEvent(Outbox.of(orderCreateEvent));

		return order;
	}

	@Transactional
	public OrderLine updateOrder(long orderId, OrderStatus newStatus) throws JsonProcessingException {
		OrderLine order = this.orderRepository.findById(orderId).orElse(null);

		if(order == null) {
			throw new RuntimeException("order id " + orderId + " not found");
		}

		OrderStatus oldStatus = order.getStatus();
		order.setStatus(newStatus);
		order = this.orderRepository.save(order);

		OrderUpdateEvent orderUpdateEvent = OrderUpdateEvent.of(orderId, newStatus, oldStatus);
		this.outboxService.exportEvent(Outbox.of(orderUpdateEvent));

		return order;
	}

	public OrderLine processShipmentUpdate(ShipmentUpdateMessage shipmentUpdateMessage) {
		long orderId = shipmentUpdateMessage.getOrderId();
		OrderLine order = this.orderRepository.findById(orderId).orElse(null);

		if(order == null) {
			throw new RuntimeException("order id " + orderId + " not found");
		}

		if(order.getStatus() == OrderStatus.ENTERED && shipmentUpdateMessage.getNewStatus() == ShipmentStatus.DONE) {
			order.setStatus(OrderStatus.SHIPPED);
			order = this.orderRepository.save(order);
		}

		return order;
	}
}
