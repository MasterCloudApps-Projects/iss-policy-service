package es.urjc.code.policy.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.policy.command.bus.Bus;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferCommand;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferResult;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "offers", description = "the Offers API")
public class OffersCommandController {

	private final Bus bus;
	
	@Autowired
	public OffersCommandController(Bus bus) {
		this.bus = bus;
	}
	
	@PostMapping("/api/v1/offers")
	public ResponseEntity<CreateOfferResult> create(CreateOfferCommand cmd) {
		final CreateOfferResult response = bus.executeCommand(cmd);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
