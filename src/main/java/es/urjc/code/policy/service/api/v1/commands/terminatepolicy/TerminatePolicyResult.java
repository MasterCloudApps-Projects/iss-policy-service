package es.urjc.code.policy.service.api.v1.commands.terminatepolicy;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TerminatePolicyResult {

	@NotEmpty
    private String policyNumber;

    public String getPolicyNumber() {
        return policyNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TerminatePolicyResult)) return false;

        TerminatePolicyResult that = (TerminatePolicyResult) o;

        return new EqualsBuilder()
                .append(policyNumber, that.policyNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(policyNumber)
                .toHashCode();
    }
    
    public static final class Builder {

        private final TerminatePolicyResult object;

        public Builder() {
            object = new TerminatePolicyResult();
        }

        public Builder withPolicyNumber(String value) {
            object.policyNumber = value;
            return this;
        }

        public TerminatePolicyResult build() {
            return object;
        }

    }
}
