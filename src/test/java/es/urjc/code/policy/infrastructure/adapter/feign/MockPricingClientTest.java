package es.urjc.code.policy.infrastructure.adapter.feign;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.code.policy.exception.BusinessException;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;

class MockPricingClientTest {

	private static final BigDecimal TOTAL_PRICE_CAR = new BigDecimal(100);
	private static final BigDecimal TOTAL_PRICE_HOUSE = new BigDecimal(30);
	private static final BigDecimal TOTAL_PRICE_TRAVEL = new BigDecimal(60);
	private static final BigDecimal TOTAL_PRICE_FARM = new BigDecimal(120);
	private MockPricingClient sut;
	
	@BeforeEach
	public void setUp() {
		this.sut = new MockPricingClient();
	}
	
	@Test
	void shouldReturnCalculatePriceResultCar(){
		// when
		var result = this.sut.calculatePrice(getCalculatePriceCommand("CAR"));
		// then
		assertThat(result.getTotalPrice()).isEqualTo(TOTAL_PRICE_CAR);
	}
	
	@Test
	void shouldReturnCalculatePriceResultHouse(){
		// when
		var result = this.sut.calculatePrice(getCalculatePriceCommand("HSI"));
		// then
		assertThat(result.getTotalPrice()).isEqualTo(TOTAL_PRICE_HOUSE);
	}
	
	@Test
	void shouldReturnCalculatePriceResultFarm(){
		// when
		var result = this.sut.calculatePrice(getCalculatePriceCommand("FAI"));
		// then
		assertThat(result.getTotalPrice()).isEqualTo(TOTAL_PRICE_FARM);
	}
	
	@Test
	void shouldReturnCalculatePriceResultTravel(){
		// when
		var result = this.sut.calculatePrice(getCalculatePriceCommand("TRI"));
		// then
		assertThat(result.getTotalPrice()).isEqualTo(TOTAL_PRICE_TRAVEL);
	}
	
	@Test
	void shouldReturnBusinessException(){
		// when
		Assertions.assertThrows(BusinessException.class, () -> {
		  this.sut.calculatePrice(getCalculatePriceCommand("XXX"));
		});
	}
	
	private CalculatePriceCommand getCalculatePriceCommand(String productCode) {
		return new CalculatePriceCommand.Builder().withProductCode(productCode).build();
	}
}
