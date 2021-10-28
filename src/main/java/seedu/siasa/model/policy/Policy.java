package seedu.siasa.model.policy;

import static seedu.siasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.siasa.model.person.Person;

/**
 * Represents a Policy in SIASA.
 * Guarantees: details and owner are present and not null, field values are validated, immutable.
 */
public class Policy {

    private final Title title;
    private final PaymentStructure paymentStructure;
    private final CoverageExpiryDate coverageExpiryDate;
    private final Commission commission;

    // Policy should always have an owner
    private final Person owner;

    /**
     * Every field must be present and not null.
     */
    public Policy(Title title, PaymentStructure paymentStructure, CoverageExpiryDate coverageExpiryDate,
                  Commission commission, Person owner) {
        requireAllNonNull(title, paymentStructure, coverageExpiryDate, commission, owner);
        this.title = title;
        this.paymentStructure = paymentStructure;
        this.coverageExpiryDate = coverageExpiryDate;
        this.commission = commission;
        this.owner = owner;
    }

    public Title getTitle() {
        return title;
    }

    public PaymentStructure getPaymentStructure() {
        return paymentStructure;
    }

    public CoverageExpiryDate getCoverageExpiryDate() {
        return coverageExpiryDate;
    }

    public Commission getCommission() {
        return commission;
    }

    public Person getOwner() {
        return owner;
    }

    /**
     *  Returns true if both policies have the same title and owner (by identity).
     *  this defines a weaker notion of equality between two policies.
     */
    public boolean isSamePolicy(Policy otherPolicy) {
        if (otherPolicy == this) {
            return true;
        }

        // Note the use of isSamePerson instead of equals
        return otherPolicy != null
                && otherPolicy.getTitle().equals(getTitle())
                && otherPolicy.getOwner().isSamePerson(getOwner());
    }

    /**
     * Returns true if both policies have the same owner (by identity)
     * and the policy titles differ by less than 2 edit distance.
     */
    public boolean isSimilarPolicy(Policy otherPolicy) {
        return otherPolicy != null
                && otherPolicy.getOwner().isSamePerson(getOwner())
                && otherPolicy.getTitle().isSimilarTo(getTitle());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Policy)) {
            return false;
        }

        Policy otherPolicy = (Policy) other;
        // Note the use of isSamePerson instead of equals
        return otherPolicy.getTitle().equals(getTitle())
                && otherPolicy.getPaymentStructure().equals(getPaymentStructure())
                && otherPolicy.getCommission().equals(getCommission())
                && otherPolicy.getCoverageExpiryDate().equals(getCoverageExpiryDate())
                && otherPolicy.getOwner().isSamePerson(getOwner());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, paymentStructure, commission, coverageExpiryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("; Payment Structure: ")
                .append(getPaymentStructure())
                .append("; Commission: ")
                .append(getCommission())
                .append("; Expiry Date: ")
                .append(getCoverageExpiryDate());

        return builder.toString();
    }
}
