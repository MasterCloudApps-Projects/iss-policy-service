package es.urjc.code.policy.infrastructure.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.policy.command.bus.Bus;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyResult;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyResult;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "policies", description = "the policies API")
public class PoliciesCommandController {

	private final Bus bus;
	
	@Autowired
	public PoliciesCommandController(Bus bus) {
		this.bus = bus;
	}
	
	@PostMapping("/api/v1/policies")
	public ResponseEntity<CreatePolicyResult> create(CreatePolicyCommand cmd) {
		final CreatePolicyResult response = bus.executeCommand(cmd);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/api/v1/policies/terminate")
	public ResponseEntity<TerminatePolicyResult> terminate(TerminatePolicyCommand cmd) {
		final TerminatePolicyResult response = bus.executeCommand(cmd);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
