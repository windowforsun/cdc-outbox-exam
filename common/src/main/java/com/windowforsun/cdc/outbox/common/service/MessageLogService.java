package com.windowforsun.cdc.outbox.common.service;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.windowforsun.cdc.outbox.common.model.ConsumedMessage;
import com.windowforsun.cdc.outbox.common.repository.ConsumedMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageLogService {
	private final ConsumedMessageRepository consumedMessageRepository;

	@Transactional(value = Transactional.TxType.MANDATORY)
	public void processed(Long eventId) {
		this.consumedMessageRepository.save(ConsumedMessage.builder()
				.eventId(eventId)
				.timeOfReceived(Instant.now())
			.build());
	}

	@Transactional(value = Transactional.TxType.MANDATORY)
	public boolean isProcessed(Long eventId) {
		return this.consumedMessageRepository.findById(eventId).orElse(null) != null;
	}
}
