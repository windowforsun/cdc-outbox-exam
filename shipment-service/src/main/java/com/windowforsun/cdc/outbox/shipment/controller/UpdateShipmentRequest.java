package com.windowforsun.cdc.outbox.shipment.controller;

import com.windowforsun.cdc.outbox.common.model.ShipmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShipmentRequest {
	private long shipmentId;
	private ShipmentStatus newStatus;
}
