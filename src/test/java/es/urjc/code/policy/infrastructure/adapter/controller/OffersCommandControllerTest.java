package es.urjc.code.policy.infrastructure.adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import es.codeurjc.policy.command.bus.Bus;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferCommand;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferResult;

public class OffersCommandControllerTest {

	private Bus bus;
	private OffersCommandController sut;
	
	@BeforeEach
	public void setUp() {
		this.bus = Mockito.mock(Bus.class);
		this.sut = new OffersCommandController(bus);
	}
	
	@Test
	void shouldBeInvokeCreateOfferUseCase() {
		// given
		final CreateOfferCommand cmd =  getCreateOfferCommand();
		final CreateOfferResult result = getCreateOfferResult();
		when(bus.executeCommand(cmd)).thenReturn(result);
		// when
		ResponseEntity<CreateOfferResult> response = this.sut.create(cmd);
		// then
		verify(bus).executeCommand(cmd);
		assertEquals(response.getBody(), result);
	}
	
	private CreateOfferCommand getCreateOfferCommand() {
		return new CreateOfferCommand.Builder().build();
	}
	
	private CreateOfferResult getCreateOfferResult() {
		return new CreateOfferResult.Builder().build();
	}
}
