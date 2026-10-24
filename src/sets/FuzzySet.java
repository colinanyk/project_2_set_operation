package sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

/**
 * An immutable fuzzy set over a finite integer universe.
 *
 * <p>Membership degrees must be finite values in the inclusive range
 * {[0.0, 1.0]}. Missing entries are stored implicitly and have degree
 * {0.0}. Null inputs, elements outside the universe, and invalid degrees
 * cause an {IllegalArgumentException}.</p>
 *
 * @author Colin Nykanen
 */
public final class FuzzySet {
	private final Set universe;
	private final Map<Integer, Double> memberships;

	/**
	 * Creates a fuzzy set from sparse element-to-degree assignments.
	 * Assignments with degree {0.0} are accepted but stored implicitly.
	 *
	 * @param universe finite domain shared by fuzzy-set operations
	 * @param memberships membership assignments; omitted elements default to zero
	 */
	public FuzzySet(Set universe, Map<Integer, Double> memberships) {
		if (universe == null) {
			throw new IllegalArgumentException("Universe cannot be null");
		}
		if (memberships == null) {
			throw new IllegalArgumentException("Membership assignments cannot be null");
		}

		this.universe = universe;
		Map<Integer, Double> validated = new LinkedHashMap<>();
		for (Map.Entry<Integer, Double> entry : memberships.entrySet()) {
			Integer element = entry.getKey();
			Double degree = entry.getValue();
			if (element == null || !universe.contains(element)) {
				throw new IllegalArgumentException("Every assigned element must belong to the universe");
			}
			validateDegree(degree);
			if (degree != 0.0) {
				validated.put(element, degree);
			}
		}
		this.memberships = Collections.unmodifiableMap(validated);
	}

	/**
	 * Creates a fuzzy set from degrees in {Set#elements()} order.
	 *
	 * @param universe finite domain
	 * @param degrees one degree for every universe element
	 * @return a new fuzzy set
	 */
	public static FuzzySet fromDegrees(Set universe, double... degrees) {
		if (universe == null) {
			throw new IllegalArgumentException("Universe cannot be null");
		}
		if (degrees == null) {
			throw new IllegalArgumentException("Membership degrees cannot be null");
		}

		int[] elements = universe.elements();
		if (degrees.length != elements.length) {
			throw new IllegalArgumentException("Membership degree count must match universe size");
		}

		Map<Integer, Double> assignments = new LinkedHashMap<>();
		for (int i = 0; i < elements.length; i++) {
			validateDegree(degrees[i]);
			if (degrees[i] != 0.0) {
				assignments.put(elements[i], degrees[i]);
			}
		}
		return new FuzzySet(universe, assignments);
	}

	public double membershipOf(int element) {
		if (!universe.contains(element)) {
			throw new IllegalArgumentException(element + " is not in the universe");
		}
		return memberships.getOrDefault(element, 0.0);
	}

	public double[] memberships() {
		int[] elements = universe.elements();
		double[] result = new double[elements.length];
		for (int i = 0; i < elements.length; i++) {
			result[i] = membershipOf(elements[i]);
		}
		return result;
	}

	public FuzzySet complement() {
		int[] elements = universe.elements();
		double[] result = new double[elements.length];
		for (int i = 0; i < elements.length; i++) {
			result[i] = 1.0 - membershipOf(elements[i]);
		}
		return fromDegrees(universe, result);
	}

	public FuzzySet union(FuzzySet other) {
		return combine(other, Math::max);
	}

	public FuzzySet intersection(FuzzySet other) {
		return combine(other, Math::min);
	}

	public String display() {
		StringBuilder result = new StringBuilder("{");
		int[] elements = universe.elements();
		for (int i = 0; i < elements.length; i++) {
			if (i > 0) {
				result.append(", ");
			}
			result.append('(').append(elements[i]).append(", ")
					.append(membershipOf(elements[i])).append(')');
		}
		return result.append('}').toString();
	}

	private FuzzySet combine(FuzzySet other, DoubleBinaryOperator operation) {
		requireSameUniverse(other);
		int[] elements = universe.elements();
		double[] result = new double[elements.length];
		for (int i = 0; i < elements.length; i++) {
			result[i] = operation.applyAsDouble(membershipOf(elements[i]), other.membershipOf(elements[i]));
		}
		return fromDegrees(universe, result);
	}

	private void requireSameUniverse(FuzzySet other) {
		if (other == null) {
			throw new IllegalArgumentException("Other fuzzy set cannot be null");
		}
		if (!Arrays.equals(universe.elements(), other.universe.elements())) {
			throw new IllegalArgumentException("Fuzzy sets must use the same universe");
		}
	}

	private static void validateDegree(Double degree) {
		if (degree == null || !Double.isFinite(degree) || degree < 0.0 || degree > 1.0) {
			throw new IllegalArgumentException("Membership degrees must be finite values between 0.0 and 1.0");
		}
	}

	@Override
	public String toString() {
		return display();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof FuzzySet other)) {
			return false;
		}
		return Arrays.equals(universe.elements(), other.universe.elements())
				&& Arrays.equals(memberships(), other.memberships());
	}

	@Override
	public int hashCode() {
		return 31 * Arrays.hashCode(universe.elements()) + Arrays.hashCode(memberships());
	}
}

