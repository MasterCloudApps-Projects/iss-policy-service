package es.urjc.code.policy.infrastructure.adapter.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class DateRangeEmbeddable {
	
    private LocalDate from;
    private LocalDate to;
    
    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DateRangeEmbeddable)) return false;

        DateRangeEmbeddable that = (DateRangeEmbeddable) o;

        return new EqualsBuilder()
                .append(from, that.from)
                .append(to, that.to)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(from)
                .append(to)
                .toHashCode();
    }
    
    public static DateRangeEmbeddable between(LocalDate from, LocalDate to) {
        return new DateRangeEmbeddable.Builder().withFrom(from).withTo(to).build();
    }

    public boolean contains(LocalDate eventDate) {
        if (eventDate.isAfter(to))
            return false;

        if (eventDate.isBefore(from))
            return false;

        return true;
    }

    public DateRangeEmbeddable endOn(LocalDate endDate) {
        return DateRangeEmbeddable.between(from, endDate);
    }

    public BigDecimal days() {
        return BigDecimal.valueOf(ChronoUnit.DAYS.between(from,to) + 1);
    }
    
    public static final class Builder {

        private final DateRangeEmbeddable object;

        public Builder() {
            object = new DateRangeEmbeddable();
        }

        public Builder withFrom(LocalDate value) {
            object.from = value;
            return this;
        }

        public Builder withTo(LocalDate value) {
            object.to = value;
            return this;
        }

        public DateRangeEmbeddable build() {
            return object;
        }

    }
}
