package es.urjc.code.policy.infrastructure.adapter.feign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.infrastructure.adapter.feign.client.PricingFeignClient;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;

class PricinngClientAdapterTest {
	
    private PricingFeignClient pricingFeignClient;
	private PricinngClientAdapter sut;
	
	@BeforeEach
	public void setUp() {
	   this.pricingFeignClient = Mockito.mock(PricingFeignClient.class);
	   this.sut = new PricinngClientAdapter(pricingFeignClient);
	}
	
	@Test
	void shouldCalculatePrice() {
		// given
		CalculatePriceCommand cmd = getCalculatePriceCommand();
		var calculateResult = getCalculateResult();
		when(pricingFeignClient.calculatePrice(cmd)).thenReturn(calculateResult);
		// when
		var result = this.sut.calculatePrice(cmd);
		// then
		verify(pricingFeignClient).calculatePrice(any());
		assertEquals(calculateResult,result);
	}

	private CalculatePriceResult getCalculateResult() {
		return new CalculatePriceResult.Builder().build();
	}
	
	private CalculatePriceCommand getCalculatePriceCommand() {
		return new CalculatePriceCommand.Builder().build();
	}
}
