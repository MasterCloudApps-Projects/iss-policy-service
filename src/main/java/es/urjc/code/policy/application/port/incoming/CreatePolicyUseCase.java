package es.urjc.code.policy.application.port.incoming;

import es.codeurjc.policy.command.bus.CommandHandler;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyResult;

public interface CreatePolicyUseCase extends CommandHandler<CreatePolicyResult,CreatePolicyCommand> {
	
	public CreatePolicyResult handle(CreatePolicyCommand command);
}
