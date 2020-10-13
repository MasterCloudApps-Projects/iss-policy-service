package es.urjc.code.policy.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.policy.command.bus.CommandHandler;
import es.urjc.code.policy.application.port.outgoing.PricingClientPort;
import es.urjc.code.policy.application.port.outgoing.UpdateOfferPort;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferCommand;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferResult;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.ChoiceQuestionAnswer;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.NumericQuestionAnswer;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.QuestionAnswer;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.TextQuestionAnswer;

@Component
public class CreateOfferHandler implements CommandHandler<CreateOfferResult, CreateOfferCommand> {

	private final PricingClientPort pricingClientPort;
	private final UpdateOfferPort updateOfferPort;

	@Autowired
	public CreateOfferHandler(PricingClientPort pricingClientPort, UpdateOfferPort updateOfferPort) {
		this.pricingClientPort = pricingClientPort;
		this.updateOfferPort = updateOfferPort;
	}

	@Transactional
	@Override
	public CreateOfferResult handle(CreateOfferCommand cmd) {
		final CalculatePriceCommand calcPriceCmd = constructPriceCmd(cmd);
		final CalculatePriceResult calcPriceResult = pricingClientPort.calculatePrice(calcPriceCmd);
		final Offer offer = updateOfferPort.createOffer(calcPriceCmd, calcPriceResult);
		return new CreateOfferResult.Builder().withOfferNumber(offer.getNumber()).withTotalPrice(offer.getTotalPrice())
				.withCoversPrices(offer.getCoversPrices()).build();
	}

	private CalculatePriceCommand constructPriceCmd(CreateOfferCommand cmd) {
		return new CalculatePriceCommand.Builder().withProductCode(cmd.getProductCode())
				.withPolicyFrom(cmd.getPolicyFrom()).withPolicyTo(cmd.getPolicyTo())
				.withSelectedCovers(cmd.getSelectedCovers()).withAnswers(constructAnswers(cmd.getAnswers())).build();
	}

	private List<QuestionAnswer> constructAnswers(
			List<es.urjc.code.policy.service.api.v1.commands.createoffer.dto.QuestionAnswer> answers) {
		List<QuestionAnswer> result = new ArrayList<>();
		for (es.urjc.code.policy.service.api.v1.commands.createoffer.dto.QuestionAnswer answer : answers) {
			if (answer instanceof es.urjc.code.policy.service.api.v1.commands.createoffer.dto.TextQuestionAnswer) {
				result.add(new TextQuestionAnswer(answer.getQuestionCode(), (String) answer.getAnswer()));
			} else if (answer instanceof es.urjc.code.policy.service.api.v1.commands.createoffer.dto.ChoiceQuestionAnswer) {
				result.add(new ChoiceQuestionAnswer(answer.getQuestionCode(), (String) answer.getAnswer()));
			} else if (answer instanceof es.urjc.code.policy.service.api.v1.commands.createoffer.dto.NumericQuestionAnswer) {
				result.add(new NumericQuestionAnswer(answer.getQuestionCode(), (BigDecimal) answer.getAnswer()));
			}
		}
		return result;
	}
}
