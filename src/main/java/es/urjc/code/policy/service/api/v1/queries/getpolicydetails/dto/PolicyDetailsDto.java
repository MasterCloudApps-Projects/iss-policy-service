package es.urjc.code.policy.service.api.v1.queries.getpolicydetails.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class PolicyDetailsDto {
	
	@NotEmpty
    private String number;
	@NotNull
    private LocalDate dateFrom;
	@NotNull
    private LocalDate dateTo;
	@NotEmpty
    private String policyHolder;
	@NotNull
    private BigDecimal totalPremium;
	@NotEmpty
    private String productCode;
	@NotEmpty
	private String accountNumber;
	@NotNull
    private Set<String> covers;

    public String getNumber() {
        return number;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Set<String> getCovers() {
        return covers;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyDetailsDto)) return false;

        PolicyDetailsDto that = (PolicyDetailsDto) o;

        return new EqualsBuilder()
                .append(number, that.number)
                .append(dateFrom, that.dateFrom)
                .append(dateTo, that.dateTo)
                .append(policyHolder, policyHolder)
                .append(totalPremium, totalPremium)
                .append(productCode, productCode)
                .append(accountNumber, accountNumber)
                .append(covers, covers)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(number)
                .append(dateFrom)
                .append(dateTo)
                .append(policyHolder)
                .append(totalPremium)
                .append(productCode)
                .append(accountNumber)
                .append(covers)
                .toHashCode();
    }    

    public static final class Builder {

        private final PolicyDetailsDto object;

        public Builder() {
            object = new PolicyDetailsDto();
        }

        public Builder withNumber(String value) {
            object.number = value;
            return this;
        }

        public Builder withDateFrom(LocalDate value) {
            object.dateFrom = value;
            return this;
        }

        public Builder withDateTo(LocalDate value) {
            object.dateTo = value;
            return this;
        }

        public Builder withPolicyHolder(String value) {
            object.policyHolder = value;
            return this;
        }

        public Builder withTotalPremium(BigDecimal value) {
            object.totalPremium = value;
            return this;
        }

        public Builder withProductCode(String value) {
            object.productCode = value;
            return this;
        }

        public Builder withAccountNumber(String value) {
            object.accountNumber = value;
            return this;
        }

        public Builder withCovers(Set<String> value) {
            object.covers = value;
            return this;
        }

        public PolicyDetailsDto build() {
            return object;
        }

    }
}
