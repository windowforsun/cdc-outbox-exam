package com.windowforsun.cdc.outbox.common.message;

import com.windowforsun.cdc.outbox.common.model.ShipmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentUpdateMessage {
	private long shipmentId;
	private long orderId;
	private ShipmentStatus newStatus;
	private ShipmentStatus oldStatus;
}
