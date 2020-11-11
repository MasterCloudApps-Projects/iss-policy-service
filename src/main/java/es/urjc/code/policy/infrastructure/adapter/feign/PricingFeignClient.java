package es.urjc.code.policy.infrastructure.adapter.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.urjc.code.policy.application.port.outgoing.PricingClientPort;
import es.urjc.code.policy.config.DefaultDecoderConfiguration;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;

@FeignClient(name="iss-pricing-service",configuration=DefaultDecoderConfiguration.class)
@ConditionalOnProperty(name = "pricing-service.enabled", havingValue = "true", matchIfMissing = true)
public interface PricingFeignClient extends PricingClientPort {
	
	@PostMapping("/api/v1/calculate")
	CalculatePriceResult calculatePrice(@RequestBody CalculatePriceCommand cmd);
}
