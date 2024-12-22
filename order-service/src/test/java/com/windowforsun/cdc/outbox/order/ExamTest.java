package com.windowforsun.cdc.outbox.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.windowforsun.cdc.outbox.order.model.OrderLine;
import com.windowforsun.cdc.outbox.order.model.OrderStatus;
import com.windowforsun.cdc.outbox.order.repository.OrderLineRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ExamTest {
	@Autowired
	private OrderLineRepository orderRepository;

	@Test
	public void kkekekekekekek() {
		this.orderRepository.save(OrderLine.builder()
				.item("testtest")
				.quantity(1)
				.totalPrice(11)
				.status(OrderStatus.ENTERED)
			.build());

	}
}
