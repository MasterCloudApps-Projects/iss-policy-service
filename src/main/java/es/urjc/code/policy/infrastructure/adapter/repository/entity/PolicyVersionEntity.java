package es.urjc.code.policy.infrastructure.adapter.repository.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "policy_version", schema = "policy")
public class PolicyVersionEntity {
	
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private PolicyEntity policy;

    private Long versionNumber;

    private String productCode;

    private PersonEmbeddable policyHolder;

    private String accountNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "from", column = @Column(name = "cover_from")),
            @AttributeOverride(name = "to", column = @Column(name = "cover_to"))
    })
    private DateRangeEmbeddable coverPeriod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "from", column = @Column(name = "version_from")),
            @AttributeOverride(name = "to", column = @Column(name = "version_to"))
    })
    private DateRangeEmbeddable versionValidityPeriod;

    @OneToMany(mappedBy = "policyVersion", cascade = CascadeType.ALL)
    private Set<CoverEntity> covers = new HashSet<>();
    
    private BigDecimal totalPremiumAmount;
    
    public UUID getId() {
        return id;
    }

    public PolicyEntity getPolicy() {
        return policy;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public PersonEmbeddable getPolicyHolder() {
        return policyHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public DateRangeEmbeddable getCoverPeriod() {
        return coverPeriod;
    }

    public DateRangeEmbeddable getVersionValidityPeriod() {
        return versionValidityPeriod;
    }

    public Set<CoverEntity> getCovers() {
        return covers;
    }

    public BigDecimal getTotalPremiumAmount() {
        return totalPremiumAmount;
    }

    public void setPolicy(PolicyEntity policy) {
		this.policy = policy;
	}

    public void addCover(CoverEntity coverEntity){
    	covers.add(coverEntity);
    	coverEntity.setPolicyVersion(this);
    }
    
    public void removeCover(CoverEntity coverEntity) {
    	covers.remove(coverEntity);
    	coverEntity.setPolicyVersion(null);
    }
    
    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyVersionEntity)) return false;

        PolicyVersionEntity that = (PolicyVersionEntity) o;

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
    
    public static final class Builder {

        private final PolicyVersionEntity object;

        public Builder() {
            object = new PolicyVersionEntity();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withPolicy(PolicyEntity value) {
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

        public Builder withPolicyHolder(PersonEmbeddable value) {
            object.policyHolder = value;
            return this;
        }

        public Builder withAccountNumber(String value) {
            object.accountNumber = value;
            return this;
        }

        public Builder withCoverPeriod(DateRangeEmbeddable value) {
            object.coverPeriod = value;
            return this;
        }

        public Builder withVersionValidityPeriod(DateRangeEmbeddable value) {
            object.versionValidityPeriod = value;
            return this;
        }

        public Builder withCovers(Set<CoverEntity> value) {
            object.covers = value;
            return this;
        }

        public Builder withTotalPremiumAmount(BigDecimal value) {
            object.totalPremiumAmount = value;
            return this;
        }

        public PolicyVersionEntity build() {
            return object;
        }

    }
}
