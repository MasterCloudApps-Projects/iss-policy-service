package es.urjc.code.policy.infrastructure.adapter.feign;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.QuestionAnswer;


@SpringBootTest(classes = {PricingFeignClientTest.FakeFeignConfiguration.class,PricingFeignClientTest.FakePricingController.class},webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude={
		LiquibaseAutoConfiguration.class,
	    DataSourceAutoConfiguration.class, 
	    DataSourceTransactionManagerAutoConfiguration.class, 
	    HibernateJpaAutoConfiguration.class})
class PricingFeignClientTest {
	
	private static final String PRODUCT_CODE = "CAR";
	private static final BigDecimal TOTAL_PRICE = new BigDecimal(100);
	
	@Autowired
	private PricingFeignClient client;

	@Test
	void testCalculatePrice() {
		ArrayList<QuestionAnswer> questionAnswers = new ArrayList();
		questionAnswers.add(new QuestionAnswer("NUM_OF_CLAIM", 1));
		CalculatePriceCommand command = new CalculatePriceCommand.Builder()
				                                                 .withProductCode(PRODUCT_CODE)
				                   				                 .withPolicyFrom(LocalDate.of(2017, 4, 16))
				                				                 .withPolicyTo(LocalDate.of(2018, 4, 15))
				                				                 .withAnswers(questionAnswers)
				                				                 .withSelectedCovers(Collections.singletonList("C1"))
				                                                 .build();
		var result  = client.calculatePrice(command);
		assertThat(result.getTotalPrice()).isEqualTo(TOTAL_PRICE);
	}
	
	@RestController
	static class FakePricingController {
		@PostMapping("/api/v1/calculate")
		public ResponseEntity<CalculatePriceResult> create(@RequestBody final CalculatePriceCommand command) {
			assertThat(command.getProductCode()).isEqualTo(PRODUCT_CODE);
			Map<String, BigDecimal> coversPrices = Collections.singletonMap("C1",TOTAL_PRICE);
			var result = new CalculatePriceResult.Builder().withTotalPrice(TOTAL_PRICE).withCoversPrices(coversPrices).build();
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		}
	}
	
	@Configuration(proxyBeanMethods = false)
	  static class FakeRibbonConfiguration {

	    @LocalServerPort int port;

	    @Bean
	    public ServerList<Server> serverList() {
	      return new StaticServerList<>(new Server("localhost", port));
	    }
	  }

	  @Configuration(proxyBeanMethods = false)
	  @EnableFeignClients(clients = PricingFeignClient.class)
	  @EnableAutoConfiguration
	  @RibbonClient(name = "iss-pricing-service", configuration = PricingFeignClientTest.FakeRibbonConfiguration.class)
	  static class FakeFeignConfiguration {}
}
