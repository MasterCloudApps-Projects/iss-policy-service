package es.urjc.code.policy.service.api.v1.commands.createpolicy.dto;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PersonDto {
	
	@NotEmpty
    private String firstName;
	@NotEmpty
    private String lastName;
	@NotEmpty
    private String taxId;
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTaxId() {
        return taxId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PersonDto)) return false;

        PersonDto that = (PersonDto) o;

        return new EqualsBuilder()
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(taxId, that.taxId)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(lastName)
                .append(taxId)
                .toHashCode();
    }    

    public static final class Builder {

        private final PersonDto object;

        public Builder() {
            object = new PersonDto();
        }

        public Builder withFirstName(String value) {
            object.firstName = value;
            return this;
        }

        public Builder withLastName(String value) {
            object.lastName = value;
            return this;
        }

        public Builder withTaxId(String value) {
            object.taxId = value;
            return this;
        }

        public PersonDto build() {
            return object;
        }

    }
}
