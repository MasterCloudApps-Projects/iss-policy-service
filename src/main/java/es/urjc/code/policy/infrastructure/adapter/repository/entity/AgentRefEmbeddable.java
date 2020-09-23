package es.urjc.code.policy.infrastructure.adapter.repository.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class AgentRefEmbeddable {
	
    @Column(name = "agent_login")
    private String login;
    
    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AgentRefEmbeddable)) return false;

        AgentRefEmbeddable that = (AgentRefEmbeddable) o;

        return new EqualsBuilder()
                .append(login, that.login)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(login)
                .toHashCode();
    }
    
    public static final class Builder {

        private final AgentRefEmbeddable object;

        public Builder() {
            object = new AgentRefEmbeddable();
        }

        public Builder withLogin(String value) {
            object.login = value;
            return this;
        }

        public AgentRefEmbeddable build() {
            return object;
        }

    }   
}
