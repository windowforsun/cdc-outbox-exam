package com.windowforsun.cdc.outbox.common.event;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface OutboxEvent {
	ObjectMapper objectMapper = new ObjectMapper();
	String getAggregateId();
	String getAggregateType();
	String getOutboxPayload() throws JsonProcessingException;
	String getType();
	Instant getTimestamp();
}
