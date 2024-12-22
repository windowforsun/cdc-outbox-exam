package com.windowforsun.cdc.outbox.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.windowforsun.cdc.outbox.common.model.Outbox;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
}
