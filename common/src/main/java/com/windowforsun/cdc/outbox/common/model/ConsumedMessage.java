package com.windowforsun.cdc.outbox.common.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumedMessage {
	@Id
	private Long eventId;
	private Instant timeOfReceived;
}
