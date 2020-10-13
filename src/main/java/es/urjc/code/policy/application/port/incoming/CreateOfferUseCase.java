package es.urjc.code.policy.application.port.incoming;

import es.codeurjc.policy.command.bus.CommandHandler;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferCommand;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferResult;

public interface CreateOfferUseCase extends CommandHandler<CreateOfferResult, CreateOfferCommand> {
	
	public CreateOfferResult handle(CreateOfferCommand cmd);
}
