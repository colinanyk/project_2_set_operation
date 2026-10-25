package tests.sets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sets.FuzzySet;
import sets.Set;

class FuzzySetTest {
	private static final double TOLERANCE = 1.0e-9;

	private Set universe;
	private FuzzySet a;
	private FuzzySet b;

	@BeforeEach
	void setUp() {
		universe = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		a = new FuzzySet(universe, Map.of(3, 1.0, 5, 0.6, 7, 0.1, 9, 0.9));
		b = new FuzzySet(universe, Map.of(1, 0.4, 2, 0.5, 9, 0.5, 10, 0.8, 11, 0.2, 12, 0.7));
	}

	@Test
	void retrievesStoredAndMissingMemberships() {
		assertEquals(0.6, a.membershipOf(5), TOLERANCE);
		assertEquals(0.0, a.membershipOf(4), TOLERANCE);
	}

	@Test
	void complementAppliesToEveryUniverseElement() {
		double[] expected = { 1.0, 1.0, 0.0, 1.0, 0.4, 1.0, 0.9, 1.0, 0.1, 1.0, 1.0, 1.0 };

		assertArrayEquals(expected, a.complement().memberships(), TOLERANCE);
	}

	@Test
	void unionUsesMaximumMembershipForStoredAndMissingEntries() {
		double[] expected = { 0.4, 0.5, 1.0, 0.0, 0.6, 0.0, 0.1, 0.0, 0.9, 0.8, 0.2, 0.7 };

		assertArrayEquals(expected, a.union(b).memberships(), TOLERANCE);
	}

	@Test
	void intersectionUsesMinimumMembershipForStoredAndMissingEntries() {
		double[] expected = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0 };

		assertArrayEquals(expected, a.intersection(b).memberships(), TOLERANCE);
	}

	@Test
	void supportsZeroFullAndIntermediateMemberships() {
		FuzzySet result = FuzzySet.fromDegrees(Set.of(1, 2, 3), 0.0, 1.0, 0.7);

		assertArrayEquals(new double[] { 0.0, 1.0, 0.7 }, result.memberships(), TOLERANCE);
	}

	@Test
	void complementOfUnassignedElementIsFullMembership() {
		FuzzySet empty = new FuzzySet(universe, Map.of());
		double[] expected = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };

		assertArrayEquals(expected, empty.complement().memberships(), TOLERANCE);
	}

	@Test
	void rejectsInvalidMembershipDegrees() {
		assertAll(
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, Map.of(1, -0.1))),
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, Map.of(1, 1.1))),
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, Map.of(1, Double.NaN))),
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, Map.of(1, Double.POSITIVE_INFINITY))));
	}

	@Test
	void rejectsNullInputsAndElementsOutsideUniverse() {
		Map<Integer, Double> nullElement = new HashMap<>();
		nullElement.put(null, 0.5);
		Map<Integer, Double> nullDegree = new HashMap<>();
		nullDegree.put(1, null);

		assertAll(
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(null, Map.of())),
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, null)),
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, nullElement)),
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, nullDegree)),
				() -> assertThrows(IllegalArgumentException.class,
						() -> new FuzzySet(universe, Map.of(13, 0.5))),
				() -> assertThrows(IllegalArgumentException.class,
						() -> a.membershipOf(13)));
	}

	@Test
	void rejectsDegreeArraysWithWrongLength() {
		assertAll(
				() -> assertThrows(IllegalArgumentException.class,
						() -> FuzzySet.fromDegrees(universe, 0.1, 0.2)),
				() -> assertThrows(IllegalArgumentException.class,
						() -> FuzzySet.fromDegrees(universe, (double[]) null)));
	}

	@Test
	void binaryOperationsRequireMatchingUniverses() {
		FuzzySet otherUniverse = new FuzzySet(Set.of(1, 2, 3), Map.of(1, 0.4));

		assertAll(
				() -> assertThrows(IllegalArgumentException.class, () -> a.union(otherUniverse)),
				() -> assertThrows(IllegalArgumentException.class, () -> a.intersection(otherUniverse)),
				() -> assertThrows(IllegalArgumentException.class, () -> a.union(null)));
	}

	@Test
	void constructorDefensivelyCopiesAssignments() {
		Map<Integer, Double> assignments = new HashMap<>();
		assignments.put(1, 0.4);
		FuzzySet set = new FuzzySet(universe, assignments);

		assignments.put(1, 0.9);
		double[] returnedDegrees = set.memberships();
		returnedDegrees[0] = 0.8;

		assertEquals(0.4, set.membershipOf(1), TOLERANCE);
	}
}
