package com.windowforsun.cdc.outbox.order.consumer;

import javax.transaction.Transactional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windowforsun.cdc.outbox.common.message.ShipmentUpdateMessage;
import com.windowforsun.cdc.outbox.common.service.MessageLogService;
import com.windowforsun.cdc.outbox.order.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxConsumer {
	private static ObjectMapper objectMapper = new ObjectMapper();

	private final OrderService orderService;
	private final MessageLogService messageLogService;

	@Transactional
	@KafkaListener(topics = "outbox.event.Shipment")
	public void listen(String outboxEvent, @Header(name = "type") String type, @Header(name = "id") String id) {
		log.info("order received outboxEvent : {}", outboxEvent);
		long eventId = Long.parseLong(id);

		if(this.messageLogService.isProcessed(eventId)) {
			log.info("Event id : {} was already processed, ignored", eventId);
			return;
		}

		switch (type) {
			case "ShipmentUpdate":
				ShipmentUpdateMessage shipmentUpdateMessage = this.<ShipmentUpdateMessage>parseMessage(
					ShipmentUpdateMessage.class, outboxEvent);

				this.orderService.processShipmentUpdate(shipmentUpdateMessage);

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
