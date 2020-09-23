package es.urjc.code.policy.commands;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.policy.command.bus.CommandHandler;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferCommand;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferResult;

@Component
public class CreateOfferHandler implements CommandHandler<CreateOfferResult, CreateOfferCommand> {

    @Transactional
	@Override
	public CreateOfferResult handle(CreateOfferCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

}
