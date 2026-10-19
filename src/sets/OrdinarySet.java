package sets;

import java.util.Arrays;
import java.util.Objects;

public final class OrdinarySet {
	private final Set universe;
	private final Set members;

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

	public static OrdinarySet of(Set universe, int... elements) {
		return new OrdinarySet(universe, elements);
	}

	public boolean contains(int element) {
		return members.contains(element);
	}

	public int size() {
		return members.size();
	}

	public int[] elements() {
		return members.elements();
	}

	public String display() {
		return members.display(universe);
	}

	public String display(Set displayUniverse) {
		requireSameUniverse(displayUniverse);
		return members.display(displayUniverse);
	}

	public OrdinarySet complement() {
		return new OrdinarySet(universe, members.complement(universe));
	}

	public OrdinarySet complement(Set complementUniverse) {
		requireSameUniverse(complementUniverse);
		return complement();
	}

	public OrdinarySet union(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.union(other.members));
	}

	public OrdinarySet intersection(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.intersection(other.members));
	}

	public OrdinarySet difference(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.difference(other.members));
	}

	public OrdinarySet symmetricDifference(OrdinarySet other) {
		requireSameUniverse(other);
		return new OrdinarySet(universe, members.symmetricDifference(other.members));
	}

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

	@Override
	public String toString() {
		return members.toString();
	}

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

	@Override
	public int hashCode() {
		return 31 * Arrays.hashCode(universe.elements()) + Arrays.hashCode(elements());
	}

}

