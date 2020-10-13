package es.urjc.code.policy.application.port.outgoing;

import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;

public interface PricingClientPort {
	CalculatePriceResult calculatePrice(CalculatePriceCommand cmd);
}
