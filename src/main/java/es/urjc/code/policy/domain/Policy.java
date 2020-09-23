package es.urjc.code.policy.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.urjc.code.policy.domain.vo.DateRange;
import es.urjc.code.policy.exception.BusinessException;

public class Policy {

	private UUID id;

    private String number;

    private AgentRef agent;

    private Set<PolicyVersion> versions=new HashSet<>();
    
    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public AgentRef getAgent() {
        return agent;
    }

    public Set<PolicyVersion> getVersions() {
        return versions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Policy)) return false;

        Policy that = (Policy) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(number, that.number)
                .append(agent, agent)
                .append(versions, versions)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(number)
                .append(agent)
                .append(versions)
                .toHashCode();
    }
    
    public PolicyVersionCollection versions() {
        return new PolicyVersionCollection.Builder().withPolicy(this).withVersions(versions).build();
    }

    public void terminate(LocalDate terminationDate) {
        PolicyVersion lastVersion = versions().lastVersion();

        if (!lastVersion.getCoverPeriod().contains(terminationDate))
        {
            throw new BusinessException("TERMINATION_DATE_OUTSIDE_VALIDITY_PERIOD");
        }


        versions().addTerminalVersion(terminationDate);
    }

    public void addVersion(Offer offer, Person policyHolder) {
        versions().add(
                1L,
                offer.getProductCode(),
                policyHolder,
                UUID.randomUUID().toString(),
                DateRange.between(offer.getPolicyFrom(), offer.getPolicyTo()),
                DateRange.between(offer.getPolicyFrom(), offer.getPolicyTo()),
                offer.getTotalPrice(),
                offer.getCoversPrices()
        );
    }
    
    public static final class Builder {

        private final Policy object;

        public Builder() {
            object = new Policy();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withNumber(String value) {
            object.number = value;
            return this;
        }

        public Builder withAgent(AgentRef value) {
            object.agent = value;
            return this;
        }

        public Builder withVersions(Set<PolicyVersion> value) {
            object.versions = value;
            return this;
        }

        public Policy build() {
            return object;
        }

    }    
}
