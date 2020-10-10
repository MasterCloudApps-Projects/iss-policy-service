package es.urjc.code.policy.service.api.v1.queries.getpolicydetails;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.codeurjc.policy.command.api.Query;

public class GetPolicyDetailsQuery implements Query<GetPolicyDetailsQueryResult> {
	
	@NotEmpty
    private String number;
    
    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GetPolicyDetailsQuery)) return false;

        GetPolicyDetailsQuery that = (GetPolicyDetailsQuery) o;

        return new EqualsBuilder()
                .append(number, that.number)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(number)
                .toHashCode();
    }
    
    public static final class Builder {

        private final GetPolicyDetailsQuery object;

        public Builder() {
            object = new GetPolicyDetailsQuery();
        }

        public Builder withNumber(String value) {
            object.number = value;
            return this;
        }

        public GetPolicyDetailsQuery build() {
            return object;
        }

    }    
}
