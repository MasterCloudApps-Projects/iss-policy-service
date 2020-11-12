package es.urjc.code.policy.infrastructure.adapter.feign;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import es.urjc.code.policy.application.port.outgoing.PricingClientPort;
import es.urjc.code.policy.exception.BusinessException;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;

@Component
@ConditionalOnMissingBean(PricinngClientAdapter.class)
public class MockPricingClient implements PricingClientPort {

	private static final BigDecimal TOTAL_PRICE_CAR = new BigDecimal(100);
	private static final BigDecimal TOTAL_PRICE_HOUSE = new BigDecimal(30);
	private static final BigDecimal TOTAL_PRICE_TRAVEL = new BigDecimal(60);
	private static final BigDecimal TOTAL_PRICE_FARM = new BigDecimal(120);
	@Override
	public CalculatePriceResult calculatePrice(CalculatePriceCommand cmd) {
		final String productCode = cmd.getProductCode();
		CalculatePriceResult result = null;
		Map<String, BigDecimal> coversPrices = null;
		switch (productCode) {
			case "CAR":
				coversPrices = Collections.singletonMap("C1",TOTAL_PRICE_CAR);
				result = new CalculatePriceResult.Builder().withTotalPrice(TOTAL_PRICE_CAR).withCoversPrices(coversPrices).build();
				break;
			case "FAI":
				coversPrices = Collections.singletonMap("C4",TOTAL_PRICE_FARM);
				result = new CalculatePriceResult.Builder().withTotalPrice(TOTAL_PRICE_FARM).withCoversPrices(coversPrices).build();
				break;
			case "HSI":
				coversPrices = Collections.singletonMap("C2",TOTAL_PRICE_HOUSE);
				result = new CalculatePriceResult.Builder().withTotalPrice(TOTAL_PRICE_HOUSE).withCoversPrices(coversPrices).build();
				break;
			case "TRI":
				coversPrices = Collections.singletonMap("C3",TOTAL_PRICE_TRAVEL);
				result = new CalculatePriceResult.Builder().withTotalPrice(TOTAL_PRICE_TRAVEL).withCoversPrices(coversPrices).build();
				break;
		    default:
		    	 throw new BusinessException("Product code " + productCode + " does not exist.");
		}
		return result;
	}

}
