package es.urjc.code.policy.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.urjc.code.policy.domain.vo.DateRange;
import es.urjc.code.policy.exception.BusinessException;


public class PolicyVersionCollection {

    private Policy policy;
    private Set<PolicyVersion> versions=new HashSet<>();

    public Policy getPolicy() {
        return policy;
    }

    public Set<PolicyVersion> getVersions() {
        return versions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyVersionCollection)) return false;

        PolicyVersionCollection that = (PolicyVersionCollection) o;

        return new EqualsBuilder()
                .append(policy, that.policy)
                .append(versions, versions)
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(policy)
                .append(versions)
                .toHashCode();
    }
      
    
    public static final class Builder {

        private final PolicyVersionCollection object;

        public Builder() {
            object = new PolicyVersionCollection();
        }

        public Builder withPolicy(Policy value) {
            object.policy = value;
            return this;
        }

        public Builder withVersions(Set<PolicyVersion> value) {
            object.versions = value;
            return this;
        }

        public PolicyVersionCollection build() {
            return object;
        }

    }
    
    
    public PolicyVersion withNumber(Long number) {
        return versions
                .stream()
                .filter(v -> v.getVersionNumber().equals(number))
                .findFirst()
                .orElseThrow(() -> new BusinessException("POLICY NOT FOUND"));
    }

    public PolicyVersion lastVersion() {
        return versions
                .stream()
                .min(Comparators.BY_VERSION_NUMBER_DESC)
                .orElseThrow(() -> new BusinessException("POLICY NOT FOUND"));
    }


    public PolicyVersion add(
            Long versionNumber,
            String productCode,
            Person policyHolder,
            String accountNumber,
            DateRange coverPeriod,
            DateRange versionPeriod,
            BigDecimal totalPremiumAmount,
            Map<String, BigDecimal> coversPrices) {

        if (hasVersion(versionNumber)) {
            throw new BusinessException("POLICY_VERSION_EXISTS");
        }

        PolicyVersion ver = new PolicyVersion.Builder()
        		                             .withPolicy(policy)
        		                             .withVersionNumber(versionNumber)
        		                             .withProductCode(productCode)
        		                             .withPolicyHolder(policyHolder)
        		                             .withAccountNumber(accountNumber)
        		                             .withCoverPeriod(coverPeriod)
        		                             .withVersionValidityPeriod(versionPeriod)
        		                             .withCovers(new HashSet<>())
        		                             .withTotalPremiumAmount(totalPremiumAmount)
        		                             .build();
        versions.add(ver);
        coversPrices.forEach((key, value) -> ver.covers().add(key, value));

        return ver;
    }

    public void addTerminalVersion(LocalDate terminationDate) {
        PolicyVersion baseVersion = lastVersion();

        DateRange newCoverPeriod = baseVersion.getCoverPeriod().endOn(terminationDate);

        DateRange newVersionPeriod = DateRange.between(
                terminationDate.plusDays(1),
                baseVersion.getVersionValidityPeriod().getTo());

        BigDecimal correctionFactor = newCoverPeriod.days().divide(
                baseVersion.getCoverPeriod().days(),
                20,
                RoundingMode.HALF_UP);
        Map<String, BigDecimal> correctedCovers = baseVersion
                .covers()
                .correct(correctionFactor);

        add(
                baseVersion.getVersionNumber()+1L,
                baseVersion.getProductCode(),
                baseVersion.getPolicyHolder(),
                baseVersion.getAccountNumber(),
                newCoverPeriod,
                newVersionPeriod,
                correctedCovers.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add),
                correctedCovers);
    }

    private boolean hasVersion(Long versionNumber) {
        return versions.stream().anyMatch(v -> v.getVersionNumber().equals(versionNumber));
    }

    static class Comparators {
        static final Comparator<PolicyVersion> BY_VERSION_NUMBER_ASC = Comparator.comparing(PolicyVersion::getVersionNumber);
        static final Comparator<PolicyVersion> BY_VERSION_NUMBER_DESC = (v1, v2) -> v2.getVersionNumber().compareTo(v1.getVersionNumber());
    }
}
