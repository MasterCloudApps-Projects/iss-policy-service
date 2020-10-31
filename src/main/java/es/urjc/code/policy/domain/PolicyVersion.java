package es.urjc.code.policy.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.urjc.code.policy.domain.vo.DateRange;

@Entity
@Table(name = "policy_version", schema = "policy")
public class PolicyVersion {

	@Id
	@GeneratedValue
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "policy_id")
	private Policy policy;

	private Long versionNumber;

	private String productCode;

	private Person policyHolder;

	private String accountNumber;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "from", column = @Column(name = "cover_from")),
			@AttributeOverride(name = "to", column = @Column(name = "cover_to")) })
	private DateRange coverPeriod;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "from", column = @Column(name = "version_from")),
			@AttributeOverride(name = "to", column = @Column(name = "version_to")) })
	private DateRange versionValidityPeriod;

	@OneToMany(mappedBy = "policyVersion", cascade = CascadeType.ALL)
	private Set<Cover> covers = new HashSet<>();

	private BigDecimal totalPremiumAmount;

	public UUID getId() {
		return id;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public Person getPolicyHolder() {
		return policyHolder;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public DateRange getCoverPeriod() {
		return coverPeriod;
	}

	public DateRange getVersionValidityPeriod() {
		return versionValidityPeriod;
	}

	public Set<Cover> getCovers() {
		return covers;
	}

	public BigDecimal getTotalPremiumAmount() {
		return totalPremiumAmount;
	}

	public void addCover(Cover cover) {
		covers.add(cover);
		cover.setPolicyVersion(this);
	}

	public void removeCover(Cover cover) {
		covers.remove(cover);
		cover.setPolicyVersion(null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof PolicyVersion))
			return false;

		PolicyVersion that = (PolicyVersion) o;

		return new EqualsBuilder().append(id, that.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).toHashCode();
	}

	public CoverCollection covers() {
		return new CoverCollection.Builder().withPolicyVersion(this).withCovers(covers).build();
	}

	public static final class Builder {

		private final PolicyVersion object;

		public Builder() {
			object = new PolicyVersion();
		}

		public Builder withId(UUID value) {
			object.id = value;
			return this;
		}

		public Builder withPolicy(Policy value) {
			object.policy = value;
			return this;
		}

		public Builder withVersionNumber(Long value) {
			object.versionNumber = value;
			return this;
		}

		public Builder withProductCode(String value) {
			object.productCode = value;
			return this;
		}

		public Builder withPolicyHolder(Person value) {
			object.policyHolder = value;
			return this;
		}

		public Builder withAccountNumber(String value) {
			object.accountNumber = value;
			return this;
		}

		public Builder withCoverPeriod(DateRange value) {
			object.coverPeriod = value;
			return this;
		}

		public Builder withVersionValidityPeriod(DateRange value) {
			object.versionValidityPeriod = value;
			return this;
		}

		public Builder withCovers(Set<Cover> value) {
			object.covers = value;
			return this;
		}

		public Builder withTotalPremiumAmount(BigDecimal value) {
			object.totalPremiumAmount = value;
			return this;
		}

		public PolicyVersion build() {
			return object;
		}

	}
}
