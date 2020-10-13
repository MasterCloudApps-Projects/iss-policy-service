package es.urjc.code.policy.application.port.incoming;

import es.codeurjc.policy.command.bus.CommandHandler;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyResult;

public interface TerminatePolicyUseCase extends CommandHandler<TerminatePolicyResult,TerminatePolicyCommand> {

	public TerminatePolicyResult handle(TerminatePolicyCommand command);
}
