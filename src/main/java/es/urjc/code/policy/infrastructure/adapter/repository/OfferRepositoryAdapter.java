package es.urjc.code.policy.infrastructure.adapter.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.policy.application.port.outgoing.LoadOfferPort;
import es.urjc.code.policy.application.port.outgoing.UpdateOfferPort;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.OfferStatus;
import es.urjc.code.policy.exception.BusinessException;
import es.urjc.code.policy.infrastructure.adapter.repository.jpa.OfferJpaRepository;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.QuestionAnswer;

@Service
@Transactional
public class OfferRepositoryAdapter implements LoadOfferPort, UpdateOfferPort {
	
	private final OfferJpaRepository offerJpaRepository;
	
	@Autowired
	public OfferRepositoryAdapter(OfferJpaRepository offerJpaRepository) {
		this.offerJpaRepository = offerJpaRepository;
	}

	@Override
	public Offer getOffer(String offerNumber) {
		final Offer offer = offerJpaRepository.getByNumber(offerNumber);
		if (offer.isExpired(LocalDate.now())) {
			throw new BusinessException("OFFER_HAS_EXPIRED"); 
		}
		return offer;
	}

	@Override
	public Offer createOffer(CalculatePriceCommand calcPriceCmd, CalculatePriceResult calcPriceResult) {
		final Offer offer = newOffer(calcPriceCmd, calcPriceResult);
		offerJpaRepository.save(offer);
		return offer;
	}

	private Offer newOffer(CalculatePriceCommand calcPriceCmd, CalculatePriceResult calcPriceResult) {
		return new Offer.Builder()
		         .withNumber(UUID.randomUUID().toString())
		         .withProductCode(calcPriceCmd.getProductCode())
		         .withPolicyFrom(calcPriceCmd.getPolicyFrom())
		         .withPolicyTo(calcPriceCmd.getPolicyTo())
		         .withAnswers(constructAnswers(calcPriceCmd.getAnswers()))
		         .withTotalPrice(calcPriceResult.getTotalPrice())
		         .withCoversPrices(calcPriceResult.getCoversPrices())
		         .withStatus(OfferStatus.NEW)
		         .withCreationDate(LocalDate.now())
		         .build();
	}

    private static Map<String, String> constructAnswers(List<QuestionAnswer> answers) {
        return answers.stream()
                .collect(Collectors.toMap(QuestionAnswer::getQuestionCode,
                        a -> a.getAnswer() != null ? a.getAnswer().toString() : null)
                );
    }
}
