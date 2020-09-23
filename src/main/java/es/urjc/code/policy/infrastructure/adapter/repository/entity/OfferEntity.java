package es.urjc.code.policy.infrastructure.adapter.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "offer")
public class OfferEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "number")
    private String number;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "policy_from")
    private LocalDate policyFrom;

    @Column(name = "policy_to")
    private LocalDate policyTo;

    @ElementCollection
    @CollectionTable(name = "offer_answers", joinColumns = @JoinColumn(name = "offer_id"))
    @MapKeyColumn(name = "question_code")
    @Column(name = "answer")
    private Map<String, String> answers;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ElementCollection
    @CollectionTable(name = "offer_cover", joinColumns = @JoinColumn(name = "offer_id"))
    @MapKeyColumn(name = "cover_code")
    @Column(name = "price")
    private Map<String, BigDecimal> coversPrices;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OfferStatusEnum status;
    
    @Column(name = "creation_date")
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

    public OfferStatusEnum getStatus() {
        return status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OfferEntity)) return false;

        OfferEntity that = (OfferEntity) o;

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

        private final OfferEntity object;

        public Builder() {
            object = new OfferEntity();
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

        public Builder withStatus(OfferStatusEnum value) {
            object.status = value;
            return this;
        }

        public Builder withCreationDate(LocalDate value) {
            object.creationDate = value;
            return this;
        }

        public OfferEntity build() {
            return object;
        }

    }
    
}
