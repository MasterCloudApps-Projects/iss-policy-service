package es.urjc.code.policy.domain.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DateRange {
	
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

        if (!(o instanceof DateRange)) return false;

        DateRange that = (DateRange) o;

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
    
    public static DateRange between(LocalDate from, LocalDate to) {
        return new DateRange.Builder().withFrom(from).withTo(to).build();
    }

    public boolean contains(LocalDate eventDate) {
        if (eventDate.isAfter(to))
            return false;

        if (eventDate.isBefore(from))
            return false;

        return true;
    }

    public DateRange endOn(LocalDate endDate) {
        return DateRange.between(from, endDate);
    }

    public BigDecimal days() {
        return BigDecimal.valueOf(ChronoUnit.DAYS.between(from,to) + 1);
    }
    
    public static final class Builder {

        private final DateRange object;

        public Builder() {
            object = new DateRange();
        }

        public Builder withFrom(LocalDate value) {
            object.from = value;
            return this;
        }

        public Builder withTo(LocalDate value) {
            object.to = value;
            return this;
        }

        public DateRange build() {
            return object;
        }

    }
}
