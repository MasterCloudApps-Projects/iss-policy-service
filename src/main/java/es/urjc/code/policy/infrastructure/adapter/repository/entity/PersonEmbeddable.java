package es.urjc.code.policy.infrastructure.adapter.repository.entity;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class PersonEmbeddable {
	
    private String firstName;
    private String lastName;
    private String pesel;
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PersonEmbeddable)) return false;

        PersonEmbeddable that = (PersonEmbeddable) o;

        return new EqualsBuilder()
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(pesel, that.pesel)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(lastName)
                .append(pesel)
                .toHashCode();
    }
    
    public static final class Builder {

        private final PersonEmbeddable object;

        public Builder() {
            object = new PersonEmbeddable();
        }

        public Builder withFirstName(String value) {
            object.firstName = value;
            return this;
        }

        public Builder withLastName(String value) {
            object.lastName = value;
            return this;
        }

        public Builder withPesel(String value) {
            object.pesel = value;
            return this;
        }

        public PersonEmbeddable build() {
            return object;
        }

    }
}
