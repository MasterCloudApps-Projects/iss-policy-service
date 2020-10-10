package es.urjc.code.policy.service.api.v1.commands.calculateprice;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class CalculatePriceResult {

	@NotNull
	private BigDecimal totalPrice;
	@NotNull
	private Map<String, BigDecimal> coversPrices;

    public CalculatePriceResult() {

	}

	public CalculatePriceResult(BigDecimal totalPrice, Map<String, BigDecimal> coversPrices) {
		super();
		this.totalPrice = totalPrice;
		this.coversPrices = coversPrices;
	}

	public static CalculatePriceResult empty() {
        return new CalculatePriceResult();
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

        if (!(o instanceof CalculatePriceResult)) return false;

        CalculatePriceResult that = (CalculatePriceResult) o;

        return new EqualsBuilder()
                .append(totalPrice, that.totalPrice)
                .append(coversPrices, that.coversPrices)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(totalPrice)
                .append(coversPrices)
                .toHashCode();
    } 
    
	public static final class Builder {

        private final CalculatePriceResult object;

        public Builder() {
            object = new CalculatePriceResult();
        }

        public Builder withTotalPrice(BigDecimal value) {
            object.totalPrice = value;
            return this;
        }

        public Builder withCoversPrices(Map<String, BigDecimal> value) {
            object.coversPrices = value;
            return this;
        }

        public CalculatePriceResult build() {
            return object;
        }

    }

}
