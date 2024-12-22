package com.windowforsun.cdc.outbox.shipment.consumer;

import javax.transaction.Transactional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windowforsun.cdc.outbox.common.message.OrderCreateMessage;
import com.windowforsun.cdc.outbox.common.message.OrderUpdateMessage;
import com.windowforsun.cdc.outbox.common.service.MessageLogService;
import com.windowforsun.cdc.outbox.shipment.service.ShipmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxConsumer {
	private final ShipmentService shipmentService;
	private final MessageLogService messageLogService;
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Transactional
	@KafkaListener(topics = "outbox.event.Order")
	public void listen(String outboxEvent, @Header(name = "type") String type, @Header(name = "id") String id) {
		log.info("shipment received outboxEvent : {}", outboxEvent);
		long eventId = Long.parseLong(id);

		if(this.messageLogService.isProcessed(eventId)) {
			log.info("Event id : {} was already processed, ignored", eventId);
			return;
		}

		switch (type) {
			case "OrderCreate":
				OrderCreateMessage orderCreateEvent = this.<OrderCreateMessage>parseMessage(OrderCreateMessage.class, outboxEvent);
				log.info("received orderCreateEvent : {}", orderCreateEvent);
				this.shipmentService.addShipment(orderCreateEvent);
				break;
			case "OrderUpdate":
				OrderUpdateMessage orderUpdateEvent = this.<OrderUpdateMessage>parseMessage(OrderUpdateMessage.class, outboxEvent);
				log.info("received orderUpdateEvent : {}", orderUpdateEvent);
				this.shipmentService.updateShipment(orderUpdateEvent);
				break;
		}

		this.messageLogService.processed(eventId);
	}

	public <T> T parseMessage(Class<T> clazz, String event) {
		T msg = null;
		try {
			msg = this.objectMapper.readValue(event, clazz);
		} catch (Exception e) {
			log.error("order event convert fail : " + event, e);
		}

		return msg;
	}
}
