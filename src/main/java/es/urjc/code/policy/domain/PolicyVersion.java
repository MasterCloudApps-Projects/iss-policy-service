package es.urjc.code.policy.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.urjc.code.policy.domain.vo.DateRange;

public class PolicyVersion {
	
    private UUID id;

    private Policy policy;

    private Long versionNumber;

    private String productCode;

    private Person policyHolder;

    private String accountNumber;

    private DateRange coverPeriod;

    private DateRange versionValidityPeriod;

    private Set<Cover> covers=new HashSet<>();
    
    private BigDecimal totalPremiumAmount;
    
    public UUID getId() {
        return id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public Person getPolicyHolder() {
        return policyHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public DateRange getCoverPeriod() {
        return coverPeriod;
    }

    public DateRange getVersionValidityPeriod() {
        return versionValidityPeriod;
    }

    public Set<Cover> getCovers() {
        return covers;
    }

    public BigDecimal getTotalPremiumAmount() {
        return totalPremiumAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyVersion)) return false;

        PolicyVersion that = (PolicyVersion) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
    
    public CoverCollection covers() {
        return new CoverCollection.Builder().withPolicyVersion(this).withCovers(covers).build();
    }
    
    public static final class Builder {

        private final PolicyVersion object;

        public Builder() {
            object = new PolicyVersion();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withPolicy(Policy value) {
            object.policy = value;
            return this;
        }

        public Builder withVersionNumber(Long value) {
            object.versionNumber = value;
            return this;
        }

        public Builder withProductCode(String value) {
            object.productCode = value;
            return this;
        }

        public Builder withPolicyHolder(Person value) {
            object.policyHolder = value;
            return this;
        }

        public Builder withAccountNumber(String value) {
            object.accountNumber = value;
            return this;
        }

        public Builder withCoverPeriod(DateRange value) {
            object.coverPeriod = value;
            return this;
        }

        public Builder withVersionValidityPeriod(DateRange value) {
            object.versionValidityPeriod = value;
            return this;
        }

        public Builder withCovers(Set<Cover> value) {
            object.covers = value;
            return this;
        }

        public Builder withTotalPremiumAmount(BigDecimal value) {
            object.totalPremiumAmount = value;
            return this;
        }

        public PolicyVersion build() {
            return object;
        }

    }
}
