package es.urjc.code.policy.service.api.v1.commands.createpolicy;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.codeurjc.policy.command.api.Command;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.dto.PersonDto;

public class CreatePolicyCommand implements Command<CreatePolicyResult> {
    
	@NotEmpty
	private String offerNumber;
	@NotNull
    private PersonDto policyHolder;
	@NotEmpty
    private String agentLogin;

    public String getOfferNumber() {
        return offerNumber;
    }

    public PersonDto getPolicyHolder() {
        return policyHolder;
    }

    public String getAgentLogin() {
        return agentLogin;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CreatePolicyCommand)) return false;

        CreatePolicyCommand that = (CreatePolicyCommand) o;

        return new EqualsBuilder()
                .append(offerNumber, that.offerNumber)
                .append(policyHolder, that.policyHolder)
                .append(agentLogin, that.agentLogin)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(offerNumber)
                .append(policyHolder)
                .append(agentLogin)
                .toHashCode();
    }        

    public static final class Builder {

        private final CreatePolicyCommand object;

        public Builder() {
            object = new CreatePolicyCommand();
        }

        public Builder withOfferNumber(String value) {
            object.offerNumber = value;
            return this;
        }

        public Builder withPolicyHolder(PersonDto value) {
            object.policyHolder = value;
            return this;
        }

        public Builder withAgentLogin(String value) {
            object.agentLogin = value;
            return this;
        }

        public CreatePolicyCommand build() {
            return object;
        }

    }

}
