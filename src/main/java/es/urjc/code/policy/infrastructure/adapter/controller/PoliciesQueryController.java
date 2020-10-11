package es.urjc.code.policy.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.policy.command.bus.Bus;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQuery;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQueryResult;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "policies", description = "the policies API")
public class PoliciesQueryController {

	private final Bus bus;
	
	@Autowired
	public PoliciesQueryController(Bus bus) {
		this.bus = bus;
	}
	
    @GetMapping("/api/v1/policies/{policyNumber}")
    public ResponseEntity<GetPolicyDetailsQueryResult> get(@PathVariable("policyNumber") String policyNumber) {
    	return ResponseEntity.status(HttpStatus.OK).body(bus.executeQuery(new GetPolicyDetailsQuery.Builder().withNumber(policyNumber).build()));
    }
}