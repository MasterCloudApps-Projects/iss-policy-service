package es.urjc.code.policy.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Cover {
	
    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    
    private BigDecimal price;

    @JsonIgnore
    private PolicyVersion policyVersion;
    
    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PolicyVersion getPolicyVersion() {
        return policyVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Cover)) return false;

        Cover that = (Cover) o;

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

        private final Cover object;

        public Builder() {
            object = new Cover();
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

        public Builder withPolicyVersion(PolicyVersion value) {
            object.policyVersion = value;
            return this;
        }

        public Cover build() {
            return object;
        }

    }
}
