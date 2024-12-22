package com.windowforsun.cdc.outbox.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windowforsun.cdc.outbox.common.model.ConsumedMessage;

public interface ConsumedMessageRepository extends JpaRepository<ConsumedMessage, Long> {
}
