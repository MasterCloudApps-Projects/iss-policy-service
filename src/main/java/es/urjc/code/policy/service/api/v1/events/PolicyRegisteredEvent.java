package es.urjc.code.policy.service.api.v1.events;

import es.urjc.code.policy.service.api.v1.events.dto.PolicyDto;

public class PolicyRegisteredEvent extends PolicyEvent {
    
	public PolicyRegisteredEvent() {
        // to avoid deserialization exception
    }
    
    private PolicyRegisteredEvent(final Builder builder) {
        setPolicy(builder.policy);

    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
    	private PolicyDto policy;
    	
        public Builder withPolicy(final PolicyDto policy) {
            this.policy = policy;
            return this;
        }
        
        public PolicyRegisteredEvent build() {
            return new PolicyRegisteredEvent(this);
        }
    }
}
