package es.urjc.code.policy.service.api.v1.commands.createoffer;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CreateOfferResult {

	@NotEmpty
	private String offerNumber;
	@NotNull
    private BigDecimal totalPrice;
	@NotNull
	private Map<String, BigDecimal> coversPrices;
    
    public String getOfferNumber() {
        return offerNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Map<String, BigDecimal> getCoversPrices() {
        return coversPrices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateOfferResult)) return false;

        CreateOfferResult that = (CreateOfferResult) o;

        return new EqualsBuilder()
                .append(offerNumber, that.offerNumber)
                .append(totalPrice, that.totalPrice)
                .append(coversPrices, that.coversPrices)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(offerNumber)
                .append(totalPrice)
                .append(coversPrices)
                .toHashCode();
    }
    
    public static final class Builder {

        private final CreateOfferResult object;

        public Builder() {
            object = new CreateOfferResult();
        }

        public Builder withOfferNumber(String value) {
            object.offerNumber = value;
            return this;
        }

        public Builder withTotalPrice(BigDecimal value) {
            object.totalPrice = value;
            return this;
        }

        public Builder withCoversPrices(Map<String, BigDecimal> value) {
            object.coversPrices = value;
            return this;
        }

        public CreateOfferResult build() {
            return object;
        }

    }
}
