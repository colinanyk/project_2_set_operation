package sets;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an immutable ordinary set of non-negative integers within a fixed
 * universal set.
 *
 * <p>Membership is delegated to the project's Boolean-array-backed {Set}
 * implementation. Every element supplied at construction must belong to the
 * universe. Binary operations require both operands to use universes containing
 * the same elements in the same order. Operation results are new
 * {OrdinarySet} instances; the original operands are not modified.</p>
 *
 * <p>The universe determines the left-to-right order used by {#display()}.
 * For example, with universe {{1, 2, 3}}, the set {{1, 3}} is
 * displayed as {"101"}.</p>
 *
 * @author Colin Nykanen
 */
public final class OrdinarySet {
	private final Set universe;
	private final Set members;

	/**
	 * Creates an ordinary set in the given universe.
	 *
	 * @param universe the fixed universe that defines valid elements and display order
	 * @param elements the elements initially belonging to this set
	 * @throws NullPointerException if {universe} or {elements} is null
	 * @throws IllegalArgumentException if an element does not belong to the universe
	 */
	public OrdinarySet(Set universe, int... elements) {
		this.universe = Objects.requireNonNull(universe, "universe cannot be null");
		Objects.requireNonNull(elements, "elements cannot be null");

		for (int element : elements) {
			if (!universe.contains(element)) {
				throw new IllegalArgumentException(element + " is not in the universe");
			}
		}
		this.members = Set.of(elements);
	}

	private OrdinarySet(Set universe, Set members) {
		this.universe = universe;
		this.members = members;
	}

	/**
	 * Creates an ordinary set in the given universe.
	 *
	 * @param universe the fixed universe that defines valid elements and display order
	 * @param elements the elements initially belonging to the set
	 * @return a new ordinary set containing {elements}
	 * @throws NullPointerException if {universe} or {elements} is null
	 * @throws IllegalArgumentException if an element does not belong to the universe
	 */
	public static OrdinarySet of(Set universe, int... elements) {
		return new OrdinarySet(universe, elements);
	}

	/**
	 * Tests whether an element belongs to this set.
	 *
	 * @param element the element to test
	 * @return {true} when the element belongs to this set; otherwise {false}
	 */
	public boolean contains(int element) {
		return members.contains(element);
	}

	/**
	 * Returns the number of elements in this set.
	 *
	 * @return the set cardinality
	 */
	public int size() {
		return members.size();
	}

	/**
	 * Returns this set's elements in universe order.
	 *
	 * @return a new array containing the member elements
	 */
	public int[] elements() {
		return members.elements();
	}

	/**
	 * Returns a bit string representing membership in the fixed universe order.
	 *
	 * @return one {'1'} or {'0'} for each element of the universe
	 */
	public String display() {
		return members.display(universe);
	}

	/**
	 * Returns a bit string using a universe equal to this set's fixed universe.
	 * This overload is provided for compatibility with {Set#display(Set)}.
	 *
	 * @param displayUniverse the universe whose order defines the bit positions
	 * @return the membership bit string
	 * @throws NullPointerException if {displayUniverse} is null
	 * @throws IllegalArgumentException if it differs from this set's universe
	 */
	public String display(Set displayUniverse) {
		requireSameUniverse(displayUniverse);
		return members.display(displayUniverse);
	}

	/**
	 * Returns the complement of this set with respect to its fixed universe.
	 *
	 * @return a new set containing every universe element absent from this set
	 */
	public OrdinarySet complement() {
		return new OrdinarySet(universe, members.complement(universe));
	}

	/**
	 * Returns the complement using a universe equal to this set's fixed universe.
	 * This overload is provided for compatibility with {Set#complement(Set)}.
	 *
	 * @param complementUniverse the universe with respect to which complement is taken
	 * @return the complement of this set
	 * @throws NullPointerException if {complementUniverse} is null
	 * @throws IllegalArgumentException if it differs from this set's universe
	 */
	public OrdinarySet complement(Set complementUniverse) {
		requireSameUniverse(complementUniverse);
		return complement();
	}

	/**
	 * Returns the union of this set and another set.
	 *
	 * @param other the set whose elements are combined with this set
	 * @return a new set containing elements present in either operand
	 * @throws NullPointerException if {other} is null
	 * @throws IllegalArgumentException if the operands use different universes
	 */
	public OrdinarySet union(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.union(other.members));
	}

	/**
	 * Returns the intersection of this set and another set.
	 *
	 * @param other the set to intersect with this set
	 * @return a new set containing elements present in both operands
	 * @throws NullPointerException if {other} is null
	 * @throws IllegalArgumentException if the operands use different universes
	 */
	public OrdinarySet intersection(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.intersection(other.members));
	}

	/**
	 * Returns the set difference {this - other}.
	 *
	 * @param other the set whose elements are removed from this set
	 * @return a new set containing elements present only in this operand
	 * @throws NullPointerException if {other} is null
	 * @throws IllegalArgumentException if the operands use different universes
	 */
	public OrdinarySet difference(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.difference(other.members));
	}

	/**
	 * Returns the symmetric difference of this set and another set.
	 *
	 * @param other the set to compare with this set
	 * @return a new set containing elements present in exactly one operand
	 * @throws NullPointerException if {other} is null
	 * @throws IllegalArgumentException if the operands use different universes
	 */
	public OrdinarySet symmetricDifference(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.symmetricDifference(other.members));
	}

	/**
	 * Alias for {#symmetricDifference(OrdinarySet)}.
	 *
	 * @param other the set to compare with this set
	 * @return a new set containing elements present in exactly one operand
	 * @throws NullPointerException if {other} is null
	 * @throws IllegalArgumentException if the operands use different universes
	 */
	public OrdinarySet symmetricDiff(OrdinarySet other) {
		return symmetricDifference(other);
	}

	private void requireSameUniverse(OrdinarySet other) {
		Objects.requireNonNull(other, "other set cannot be null");
		requireSameUniverse(other.universe);
	}

	private void requireSameUniverse(Set otherUniverse) {
		Objects.requireNonNull(otherUniverse, "universe cannot be null");
		if (!Arrays.equals(universe.elements(), otherUniverse.elements())) {
			throw new IllegalArgumentException("Sets must use the same universe");
		}
	}

	/**
	 * Returns a readable element listing such as {"{1, 3, 5}"}.
	 *
	 * @return the member elements in universe order
	 */
	@Override
	public String toString() {
		return members.toString();
	}

	/**
	 * Compares both universe order and member elements for value equality.
	 *
	 * @param object the object to compare
	 * @return {true} if both ordinary sets have equal universes and members
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof OrdinarySet)) {
			return false;
		}
		OrdinarySet other = (OrdinarySet) object;
		return Arrays.equals(universe.elements(), other.universe.elements())
				&& Arrays.equals(elements(), other.elements());
	}

	/**
	 * Returns a hash code derived from the universe and member elements.
	 *
	 * @return a value consistent with {#equals(Object)}
	 */
	@Override
	public int hashCode() {
		return 31 * Arrays.hashCode(universe.elements()) + Arrays.hashCode(elements());
	}

}

