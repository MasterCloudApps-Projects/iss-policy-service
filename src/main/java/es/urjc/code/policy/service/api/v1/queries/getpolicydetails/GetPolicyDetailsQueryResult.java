package es.urjc.code.policy.service.api.v1.queries.getpolicydetails;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.dto.PolicyDetailsDto;

public class GetPolicyDetailsQueryResult {
	@NotNull
    private PolicyDetailsDto policy;

    public PolicyDetailsDto getPolicy() {
        return policy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GetPolicyDetailsQueryResult)) return false;

        GetPolicyDetailsQueryResult that = (GetPolicyDetailsQueryResult) o;

        return new EqualsBuilder()
                .append(policy, that.policy)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(policy)
                .toHashCode();
    }
        
    public static final class Builder {

        private final GetPolicyDetailsQueryResult object;

        public Builder() {
            object = new GetPolicyDetailsQueryResult();
        }

        public Builder withPolicy(PolicyDetailsDto value) {
            object.policy = value;
            return this;
        }

        public GetPolicyDetailsQueryResult build() {
            return object;
        }

    }
}
