package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sets.Set;

class SetTest {

	private Set U;
	private Set A;
	private Set B;
	private Set empty;
	private Set indentical;

	@BeforeEach
	void setUp() {

		A = Set.of(1,3,5,7,9,11);

		B = Set.of(2,3,6,7,10,11,12);

		U = Set.of(1,2,3,4,5,6,7,8,9,10,11,12);

		empty = Set.of();

		indentical = Set.of(1, 2, 3);
	}

	//ensures a sets present elements in relation to the universal set are correctly represented
	//by corresponding boolean values
	@Test
	void booleanRepresentation() {
		String expected = "011001100111";

		assertEquals(expected, B.display(U));
	}

	@Test
	void complementOfA() {
		Set result = A.complement(U);

		int[] expected = {2, 4, 6, 8, 10, 12};
		for (int value : expected) {
			assertTrue(result.contains(value));
		}
		assertEquals(expected.length, result.size());
	}

	@Test
	void complementOfEmpty() {
		Set result = empty.complement(U);

		int[] expected = {1,2,3,4,5,6,7,8,9,10,11,12};
		for (int value : expected) {
			assertTrue(result.contains(value));
		}
		assertEquals(expected.length, result.size());
	}

	@Test
	void complementOfUniversal() {
		Set result = U.complement(U);

		assertEquals(0, result.size());
	}

	@Test
	void unionOfAandB() {
		Set result = A.union(B);

		int[] expected = {1, 2, 3, 5, 6, 7, 9, 10, 11, 12};
		for (int value : expected) {
			assertTrue(result.contains(value));
		}
		assertEquals(expected.length, result.size());
	}

	@Test
	void unionOfEmptyandUniversal() {
		Set result = empty.union(U);

		int[] expected = {1,2,3,4,5,6,7,8,9,10,11,12};
		for (int value : expected) {
			assertTrue(result.contains(value));
		}
		assertEquals(expected.length, result.size());
	}

	@Test
	void intersectionOfAandB() {
		Set result = A.intersection(B);

		int[] expected = {3, 7, 11};
		for (int value : expected) {
			assertTrue(result.contains(value));
		}
		assertEquals(expected.length, result.size());
	}

	@Test
	void intersectionOfEmpty() {
		Set result = empty.intersection(U);

		assertEquals(0, result.size());
	}

	@Test
	void differenceOfAandB() {
		Set result = A.difference(B);

		int[] expected = {1, 5, 9};
		for (int value : expected) {
			assertTrue(result.contains(value));
		}
		assertEquals(expected.length, result.size());
	}

	@Test
	void differenceOfEmptyandUniversal() {
		Set result = empty.difference(U);

		assertEquals(0, result.size());
	}

	@Test
	void symmetricDifferenceOfAandB() {
		Set result = A.symmetricDiff(B);

		int[] expected = {1, 2, 5, 6, 9, 10, 12};
		for (int value : expected) {
			assertTrue(result.contains(value));
		}
		assertEquals(expected.length, result.size());
	}

	@Test
	void symmetricDifferenceOfIdentical() {
		Set result = indentical.symmetricDiff(indentical);

		assertEquals(0, result.size());
	}


}
