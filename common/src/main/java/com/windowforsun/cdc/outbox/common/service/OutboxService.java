package com.windowforsun.cdc.outbox.common.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.windowforsun.cdc.outbox.common.model.Outbox;
import com.windowforsun.cdc.outbox.common.repository.OutboxRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutboxService {
	private final OutboxRepository outboxRepository;

	@Transactional
	public void exportEvent(Outbox outbox) {
		this.outboxRepository.save(outbox);
		// this.outboxRepository.deleteById(outbox.getId());
	}
}
