package es.urjc.code.policy.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CoverCollection {

	private PolicyVersion policyVersion;
	
    private Set<Cover> covers=new HashSet<>();

    public PolicyVersion getPolicyVersion() {
        return policyVersion;
    }

    public Set<Cover> getCovers() {
        return covers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CoverCollection)) return false;

        CoverCollection that = (CoverCollection) o;

        return new EqualsBuilder()
                .append(policyVersion, that.policyVersion)
                .append(covers, covers)
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(policyVersion)
                .append(covers)
                .toHashCode();
    }
      
    
    public static final class Builder {

        private final CoverCollection object;

        public Builder() {
            object = new CoverCollection();
        }

        public Builder withPolicyVersion(PolicyVersion value) {
            object.policyVersion = value;
            return this;
        }

        public Builder withCovers(Set<Cover> value) {
            object.covers = value;
            return this;
        }

        public CoverCollection build() {
            return object;
        }

    }

    
    public  Cover add(String code, BigDecimal price) {
        Cover cover = new Cover.Builder().withPolicyVersion(policyVersion).withCode(code).withPrice(price).build();
        covers.add(cover);
        return cover;
    }

    public Map<String, BigDecimal> correct(BigDecimal correctionFactor) {
        Map<String,BigDecimal> correctedValues = new HashMap<>();
        covers.forEach(c -> correctedValues.put(
                c.getCode(),
                c.getPrice().multiply(correctionFactor).setScale(2, RoundingMode.HALF_UP)));
        return correctedValues;
    }
    
    
}
