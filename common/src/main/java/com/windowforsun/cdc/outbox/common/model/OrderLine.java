package com.windowforsun.cdc.outbox.common.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class OrderLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_ids")
	// @SequenceGenerator(name = "order_line_ids", sequenceName = "seq_order_line", allocationSize = 50)
	private Long id;
	private String item;
	private int quantity;
	private long totalPrice;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

}
