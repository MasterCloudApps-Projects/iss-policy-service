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
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyResult;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "policies", description = "the policies API")
@Validated
public class PoliciesCommandController {

	private final Bus bus;
	
	@Autowired
	public PoliciesCommandController(Bus bus) {
		this.bus = bus;
	}
	
    @Operation(summary = "Create policy", description = "", tags = { "policies" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Create policy",
                content = @Content(schema = @Schema(implementation = CreatePolicyResult.class))), 
        @ApiResponse(responseCode = "400", description = "Invalid input")})
	@PostMapping("/api/v1/policies")
	public ResponseEntity<CreatePolicyResult> create(@Parameter(description="Create policy command. Cannot null or empty.", 
            required=true, schema=@Schema(implementation = CreatePolicyCommand.class)) @Valid @RequestBody CreatePolicyCommand cmd) {
		final CreatePolicyResult response = bus.executeCommand(cmd);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    
    @Operation(summary = "Terminate policy", description = "", tags = { "policies" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Terminate policy",
                content = @Content(schema = @Schema(implementation = TerminatePolicyResult.class))), 
        @ApiResponse(responseCode = "400", description = "Invalid input")})	
	@PostMapping("/api/v1/policies/terminate")
	public ResponseEntity<TerminatePolicyResult> terminate(@Parameter(description="Terminate policy command. Cannot null or empty.", 
            required=true, schema=@Schema(implementation = TerminatePolicyCommand.class))@Valid @RequestBody TerminatePolicyCommand cmd) {
		final TerminatePolicyResult response = bus.executeCommand(cmd);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
