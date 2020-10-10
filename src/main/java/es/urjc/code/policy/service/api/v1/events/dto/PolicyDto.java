package es.urjc.code.policy.service.api.v1.events.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class PolicyDto {
	
	@NotEmpty
    private String number;
	@NotNull
    private LocalDate from;
	@NotNull
    private LocalDate to;
	@NotEmpty
    private String policyHolder;
	@NotEmpty
    private String productCode;
	@NotNull
    private BigDecimal totalPremium;
    @NotEmpty
    private String agentLogin;
    
    public String getNumber() {
        return number;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public String getProductCode() {
        return productCode;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public String getAgentLogin() {
        return agentLogin;
    }

    public static final class Builder {

        private final PolicyDto object;

        public Builder() {
            object = new PolicyDto();
        }

        public Builder withNumber(String value) {
            object.number = value;
            return this;
        }

        public Builder withFrom(LocalDate value) {
            object.from = value;
            return this;
        }

        public Builder withTo(LocalDate value) {
            object.to = value;
            return this;
        }

        public Builder withPolicyHolder(String value) {
            object.policyHolder = value;
            return this;
        }

        public Builder withProductCode(String value) {
            object.productCode = value;
            return this;
        }

        public Builder withTotalPremium(BigDecimal value) {
            object.totalPremium = value;
            return this;
        }

        public Builder withAgentLogin(String value) {
            object.agentLogin = value;
            return this;
        }

        public PolicyDto build() {
            return object;
        }

    }
}
