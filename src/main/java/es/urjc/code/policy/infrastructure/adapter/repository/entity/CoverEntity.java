package es.urjc.code.policy.infrastructure.adapter.repository.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "cover", schema = "policy")
public class CoverEntity {
	
    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "policy_version_id")
    private PolicyVersionEntity policyVersion;
    
    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PolicyVersionEntity getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(PolicyVersionEntity policyVersionEntity) {
		this.policyVersion = policyVersionEntity;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CoverEntity)) return false;

        CoverEntity that = (CoverEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(code, that.code)
                .append(price, price)
                .append(policyVersion, policyVersion)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(code)
                .append(price)
                .append(policyVersion)
                .toHashCode();
    }
    
    public static final class Builder {

        private final CoverEntity object;

        public Builder() {
            object = new CoverEntity();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withCode(String value) {
            object.code = value;
            return this;
        }

        public Builder withPrice(BigDecimal value) {
            object.price = value;
            return this;
        }

        public Builder withPolicyVersion(PolicyVersionEntity value) {
            object.policyVersion = value;
            return this;
        }

        public CoverEntity build() {
            return object;
        }

    }
}
