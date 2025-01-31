package seedu.siasa.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.siasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.siasa.model.contact.Contact;
import seedu.siasa.model.policy.exceptions.DuplicatePolicyException;
import seedu.siasa.model.policy.exceptions.PolicyNotFoundException;

public class UniquePolicyList implements Iterable<Policy> {

    private final ObservableList<Policy> internalList = FXCollections.observableArrayList();
    private final ObservableList<Policy> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent policy as the given argument.
     */
    public boolean contains(Policy toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePolicy);
    }

    /**
     * Returns true if the list contains a policy with a similar title and same
     * owner as {@code toCheck}. Similar is defined as the policy titles having an
     * edit distance of zero or one (case insensitive).
     */
    public Optional<Policy> getSimilar(Policy toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().filter(toCheck::isSimilarPolicy).findFirst();
    }

    /**
     * Adds a policy to the list.
     * The policy must not already exist in the list.
     */
    public void add(Policy toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePolicyException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the policy {@code target} in the list with {@code editedPolicy}.
     * {@code target} must exist in the list.
     * The policy identity of {@code editedPolicy} must not be the same as another existing policy in the list.
     */
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireAllNonNull(target, editedPolicy);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PolicyNotFoundException();
        }

        if (!target.isSamePolicy(editedPolicy) && contains(editedPolicy)) {
            throw new DuplicatePolicyException();
        }

        internalList.set(index, editedPolicy);
    }

    /**
     * Removes the equivalent policy from the list.
     * The policy must exist in the list.
     */
    public void remove(Policy toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PolicyNotFoundException();
        }
    }

    /**
     * Removes the policies belonging to the {@code owner} from the list.
     */
    public void removeBelongingTo(Contact owner) {
        requireNonNull(owner);
        internalList.removeIf(policy -> policy.getOwner().equals(owner));
    }

    /**
     * Replaces the contents of this list with {@code policies}.
     * {@code policies} must not contain duplicate policies.
     */
    public void setPolicies(UniquePolicyList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code policies}.
     * {@code policies} must not contain duplicate policies.
     */
    public void setPolicies(List<Policy> policies) {
        requireAllNonNull(policies);
        if (!policiesAreUnique(policies)) {
            throw new DuplicatePolicyException();
        }

        internalList.setAll(policies);
    }

    /**
     * Returns the total commission from the policy list.
     */
    public int getTotalCommission() {
        float total = 0;
        for (Policy policy : internalList) {
            int commissionPercentage = policy.getCommission().commissionPercentage;
            int numberPayments = policy.getCommission().numberOfPayments;
            int paymentAmt = policy.getPaymentStructure().paymentAmount;
            total = total + ((float) commissionPercentage / 100)
                    * numberPayments * paymentAmt;
        }
        return (int) total;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Policy> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Policy> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePolicyList // instanceof handles nulls
                && internalList.equals(((UniquePolicyList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code policies} contains only unique policy.
     */
    private boolean policiesAreUnique(List<Policy> policies) {
        for (int i = 0; i < policies.size() - 1; i++) {
            for (int j = i + 1; j < policies.size(); j++) {
                if (policies.get(i).isSamePolicy(policies.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
