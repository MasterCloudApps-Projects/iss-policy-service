package es.urjc.code.policy.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class Offer {

    private UUID id;

    private String number;

    private String productCode;

    private LocalDate policyFrom;

    private LocalDate policyTo;

    private Map<String, String> answers;

    private BigDecimal totalPrice;

    private Map<String, BigDecimal> coversPrices;

    private OfferStatus status;
    
    private LocalDate creationDate;
    
    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getProductCode() {
        return productCode;
    }

    public LocalDate getPolicyFrom() {
        return policyFrom;
    }

    public LocalDate getPolicyTo() {
        return policyTo;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Map<String, BigDecimal> getCoversPrices() {
        return coversPrices;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Offer)) return false;

        Offer that = (Offer) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
    
    public static final class Builder {

        private final Offer object;

        public Builder() {
            object = new Offer();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withNumber(String value) {
            object.number = value;
            return this;
        }

        public Builder withProductCode(String value) {
            object.productCode = value;
            return this;
        }

        public Builder withPolicyFrom(LocalDate value) {
            object.policyFrom = value;
            return this;
        }

        public Builder withPolicyTo(LocalDate value) {
            object.policyTo = value;
            return this;
        }

        public Builder withAnswers(Map<String, String> value) {
            object.answers = value;
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

        public Builder withStatus(OfferStatus value) {
            object.status = value;
            return this;
        }

        public Builder withCreationDate(LocalDate value) {
            object.creationDate = value;
            return this;
        }

        public Offer build() {
            return object;
        }

    }
    
    /*
    Offers are valid only for 30 days
    */
    public boolean isExpired(LocalDate theDate) {
        return creationDate.plusDays(30).isBefore(theDate);
    }

}
