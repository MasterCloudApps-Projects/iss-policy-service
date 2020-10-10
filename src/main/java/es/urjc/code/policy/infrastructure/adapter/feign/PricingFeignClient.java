package es.urjc.code.policy.infrastructure.adapter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.urjc.code.policy.application.port.outgoing.PricingClient;
import es.urjc.code.policy.config.PolicyDecoderConfiguration;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;

@FeignClient(name="pricing-service",url = "${pricing-service.url}",configuration=PolicyDecoderConfiguration.class)
public interface PricingFeignClient extends PricingClient {
	
	@PostMapping("/api/v1/calculate")
	CalculatePriceResult calculatePrice(@RequestBody CalculatePriceCommand cmd);
}
