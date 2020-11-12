package es.urjc.code.policy.infrastructure.adapter.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import es.urjc.code.policy.application.port.outgoing.PricingClientPort;
import es.urjc.code.policy.infrastructure.adapter.feign.client.PricingFeignClient;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;

@ConditionalOnProperty(name = "pricing-client.enabled", havingValue = "true", matchIfMissing = true)
public class PricinngClientAdapter implements PricingClientPort  {
	
	private final PricingFeignClient pricingFeignClient;
	
	@Autowired
	public PricinngClientAdapter(PricingFeignClient pricingFeignClient) {
		this.pricingFeignClient = pricingFeignClient;
	}

	@Override
	public CalculatePriceResult calculatePrice(CalculatePriceCommand cmd) {
		return pricingFeignClient.calculatePrice(cmd);
	}

}
