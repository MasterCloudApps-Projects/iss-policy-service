package es.urjc.code.policy.infrastructure.adapter.repository.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "policy", schema = "policy")
public class PolicyEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "number")
    private String number;

    @Embedded
    private AgentRefEmbeddable agent;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private Set<PolicyVersionEntity> versions = new HashSet<>();
    
    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public AgentRefEmbeddable getAgent() {
        return agent;
    }

    public Set<PolicyVersionEntity> getVersions() {
        return versions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyEntity)) return false;

        PolicyEntity that = (PolicyEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(number, that.number)
                .append(agent, agent)
                .append(versions, versions)
                .isEquals();
    }


    public void addPolicyVersion(PolicyVersionEntity policyVersionEntity){
    	versions.add(policyVersionEntity);
    	policyVersionEntity.setPolicy(this);
    }
    
    public void removePolicyVersion(PolicyVersionEntity policyVersionEntity) {
    	versions.remove(policyVersionEntity);
    	policyVersionEntity.setPolicy(null);
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
    

    public static final class Builder {

        private final PolicyEntity object;

        public Builder() {
            object = new PolicyEntity();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withNumber(String value) {
            object.number = value;
            return this;
        }

        public Builder withAgent(AgentRefEmbeddable value) {
            object.agent = value;
            return this;
        }

        public Builder withVersions(Set<PolicyVersionEntity> value) {
            object.versions = value;
            return this;
        }

        public PolicyEntity build() {
            return object;
        }

    }    
}
