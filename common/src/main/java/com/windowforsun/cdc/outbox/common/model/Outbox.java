package com.windowforsun.cdc.outbox.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.event.OutboxEvent;

import lombok.Getter;

@Entity
@Getter
public class Outbox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "aggregatetype")
	private String aggregateType;
	@Column(name = "aggregateid")
	private String aggregateId;
	private String type;
	private String payload;

	public Outbox() {

	}

	private Outbox(String aggregateType, String aggregateId, String type, String payload) {
		this.aggregateType = aggregateType;
		this.aggregateId = aggregateId;
		this.type = type;
		this.payload = payload;
	}


	public static Outbox of(OutboxEvent outboxEvent) throws JsonProcessingException {
		return new Outbox(outboxEvent.getAggregateType(), outboxEvent.getAggregateId(), outboxEvent.getType(),
			outboxEvent.getOutboxPayload());
	}
}
