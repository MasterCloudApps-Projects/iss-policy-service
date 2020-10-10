package es.urjc.code.policy.service.api.v1.commands.terminatepolicy;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.codeurjc.policy.command.api.Command;

public class TerminatePolicyCommand implements Command<TerminatePolicyResult> {
    
	@NotEmpty
	private String policyNumber;
	
    public String getPolicyNumber() {
        return policyNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TerminatePolicyCommand)) return false;

        TerminatePolicyCommand that = (TerminatePolicyCommand) o;

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

        private final TerminatePolicyCommand object;

        public Builder() {
            object = new TerminatePolicyCommand();
        }

        public Builder withPolicyNumber(String value) {
            object.policyNumber = value;
            return this;
        }

        public TerminatePolicyCommand build() {
            return object;
        }

    }
}
