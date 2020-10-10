package es.urjc.code.policy.application.port.outgoing;

import es.urjc.code.policy.domain.Offer;

public interface LoadOfferPort {
  public Offer getOffer(String offerNumber);
}
