package es.urjc.code.policy.infrastructure.adapter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.policy.command.bus.Bus;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferCommand;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "offers", description = "the Offers API")
@Validated
public class OffersCommandController {

	private final Bus bus;
	
	@Autowired
	public OffersCommandController(Bus bus) {
		this.bus = bus;
	}

    @Operation(summary = "Create offer", description = "", tags = { "offers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Create offer",
                content = @Content(schema = @Schema(implementation = CreateOfferResult.class))), 
        @ApiResponse(responseCode = "400", description = "Invalid input")})
	@PostMapping("/api/v1/offers")
	public ResponseEntity<CreateOfferResult> create(@Parameter(description="Create offer command. Cannot null or empty.", 
            required=true, schema=@Schema(implementation = CreateOfferCommand.class)) @Valid @RequestBody CreateOfferCommand cmd) {
		final CreateOfferResult response = bus.executeCommand(cmd);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
