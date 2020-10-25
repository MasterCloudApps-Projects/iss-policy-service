package es.urjc.code.policy.infrastructure.adapter.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.policy.command.bus.Bus;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQuery;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQueryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "policies", description = "the policies API")
public class PoliciesQueryController {

	private final Bus bus;
	
	@Autowired
	public PoliciesQueryController(Bus bus) {
		this.bus = bus;
	}

	@Operation(summary = "Get policy by policy number", description = "Returns a single policy", tags = { "policies" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = GetPolicyDetailsQueryResult.class))),

			@ApiResponse(responseCode = "404", description = "Policy not found") })
    @GetMapping("/api/v1/policies/{policyNumber}")
    public ResponseEntity<GetPolicyDetailsQueryResult> get(@Parameter(description = "policy number of the policy to be obtained. Cannot be empty.", required = true) @PathVariable("policyNumber") @NotEmpty  String policyNumber) {
    	return ResponseEntity.status(HttpStatus.OK).body(bus.executeQuery(new GetPolicyDetailsQuery.Builder().withNumber(policyNumber).build()));
    }
}
