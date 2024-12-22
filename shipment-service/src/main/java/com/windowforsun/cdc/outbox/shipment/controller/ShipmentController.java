package com.windowforsun.cdc.outbox.shipment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windowforsun.cdc.outbox.common.model.Shipment;
import com.windowforsun.cdc.outbox.shipment.service.ShipmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipmentController {
	private final ShipmentService shipmentService;

	@PutMapping
	public Shipment updateShipment(@RequestBody UpdateShipmentRequest updateShipmentRequest) throws
		JsonProcessingException {
		return this.shipmentService.updateShipment(updateShipmentRequest.getShipmentId(), updateShipmentRequest.getNewStatus());
	}
}
