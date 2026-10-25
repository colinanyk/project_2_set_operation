import java.util.Locale;

import sets.FuzzySet;
import sets.MultiSet;
import sets.Set;

/*
 * Team 4
 * Team Members: Rowan Bailey, Cassandra Keiley, Colin Nykanen, Yakez Owens
 * Course: CS 2430 - 501
 * Project: Programming Project 2: Set Operations
 * Primary Author: rBailey
 */

/**
 * Runs the ordinary set, multiset, and fuzzy set operations for Programming
 * Project 2. Creates example data, performs the required operations, and
 * displays results.
 */
public class Main {

	/**
	 * The main method creates example sets and multisets, then displays the results
	 * of each required operation.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {

		//Define the universal set in a fixed order.
		Set universe = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		//PART 1: ORDINARY SETS
		Set A = Set.of(0, 2, 4, 6);
		Set B = Set.of(2, 3, 5, 6);

		System.out.println("===== PART 1: ORDINARY SETS =====");
		System.out.println("Fixed universal order (left-to-right positions): " + universe);

		displaySet("Universal Set", universe, universe);
		displaySet("Set A", A, universe);
		displaySet("Set B", B, universe);

		displaySet("NOT(A) - Complement", A.complement(universe), universe);

		displaySet("A U B - Union", A.union(B), universe);

		displaySet("A INTERSECTION B", A.intersection(B), universe);

		displaySet("A - B - Difference", A.difference(B), universe);

		displaySet("A XOR B - Symmetric Difference", A.symmetricDifference(B), universe);

		//PART 2: MULTISETS
		//Each position corresponds to an element in the universe.
		//Each integer represents the number of occurrences.
		int[] aCounts = { 2, 1, 0, 3, 1, 0, 0, 0, 0, 0 };
		int[] bCounts = { 1, 2, 1, 0, 0, 2, 0, 0, 0, 0 };

		MultiSet multisetA = new MultiSet(universe, aCounts);
		MultiSet multisetB = new MultiSet(universe, bCounts);

		System.out.println("\n===== PART 2: MULTISETS (BAGS) =====");
		System.out.println("Fixed universal order (multiplicity positions): " + universe);

		displayMultiset("Multiset A", multisetA, universe);
		displayMultiset("Multiset B", multisetB, universe);

		//Union takes the maximum multiplicity.
		displayMultiset("Multiset Union (A U B)", multisetA.union(multisetB), universe);

		//Intersection takes the minimum multiplicity.
		displayMultiset("Multiset Intersection (A INTERSECTION B)", multisetA.intersection(multisetB), universe);

		//Difference subtracts multiplicities, with a minimum of zero.
		displayMultiset("Multiset Difference (A - B)", multisetA.difference(multisetB), universe);

		//Sum adds the multiplicities of corresponding elements.
		displayMultiset("Multiset Sum (A + B)", multisetA.sum(multisetB), universe);

		//EDGE CASES
		Set emptySet = Set.of();

		displaySet("A UNION Empty Set", A.union(emptySet), universe);

		displaySet("A INTERSECTION Empty Set", A.intersection(emptySet), universe);

		//Multiset edge cases.
		int[] emptyCounts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		MultiSet emptyMultiset = new MultiSet(universe, emptyCounts);

		displayMultiset("A + Empty Multiset", multisetA.sum(emptyMultiset), universe);

		displayMultiset("A - Empty Multiset", multisetA.difference(emptyMultiset), universe);

		//PART 3: FUZZY SETS
		//Membership degrees range from 0.0 to 1.0.
		//The arrays correspond to elements 0 through 9 in the universe.
		double[] fuzzyADegrees = { 1.0, 0.0, 0.70, 0.0, 0.50, 0.0, 1.0, 0.0, 0.0, 0.0 };
		double[] fuzzyBDegrees = { 0.0, 0.40, 0.40, 0.0, 1.0, 0.60, 0.0, 0.0, 0.0, 0.0 };
		FuzzySet fuzzyA = FuzzySet.fromDegrees(universe, fuzzyADegrees);
		FuzzySet fuzzyB = FuzzySet.fromDegrees(universe, fuzzyBDegrees);

		System.out.println("\n===== PART 3: FUZZY SET CHANGE REQUEST =====");
		System.out.println("Fixed universal order (membership positions): " + universe);
		displayFuzzySet("Input A", fuzzyA, universe);
		displayFuzzySet("Input B", fuzzyB, universe);

		//Complement: 1.0 - membership degree.
		double[] expectedComplement = { 0.0, 1.0, 0.30, 1.0, 0.50, 1.0, 0.0, 1.0, 1.0, 1.0 };
		displayFuzzyOperation("Complement NOT(A)", expectedComplement, fuzzyA.complement(), universe);

		//Union: maximum membership degree.
		double[] expectedUnion = { 1.0, 0.40, 0.70, 0.0, 1.0, 0.60, 1.0, 0.0, 0.0, 0.0 };
		displayFuzzyOperation("Union (A U B)", expectedUnion, fuzzyA.union(fuzzyB), universe);

		//Intersection: minimum membership degree.
		double[] expectedIntersection = { 0.0, 0.0, 0.40, 0.0, 0.50, 0.0, 0.0, 0.0, 0.0, 0.0 };
		displayFuzzyOperation("Intersection (A INTERSECTION B)", expectedIntersection,
				fuzzyA.intersection(fuzzyB), universe);
	}

	/**
	 * Displays a set as a bit string and an element listing.
	 *
	 * @param label    operation or set name
	 * @param result   set to display
	 * @param universe universal set defining element order
	 */
	private static void displaySet(String label, Set result, Set universe) {

		System.out.println("\n" + label);
		System.out.println("Bit string: " + result.display(universe));
		System.out.println("Elements: " + result);
	}

	/**
	 * Displays a multiset's element listing and multiplicities.
	 *
	 * @param label    operation or multiset name
	 * @param result   multiset to display
	 * @param universe universal set defining element order
	 */
	private static void displayMultiset(String label, MultiSet result, Set universe) {

		System.out.println("\n" + label);
		System.out.println("Multiplicities: " + result.display(universe));
		System.out.println("Elements: " + result);
	}

	private static void displayFuzzySet(String label, FuzzySet result, Set universe) {
		System.out.println("\n" + label + ": " + formatFuzzyEntries(universe, result.memberships()));
	}

	private static void displayFuzzyOperation(String label, double[] expected, FuzzySet actual, Set universe) {
		System.out.println("\n" + label);
		System.out.println("Expected: " + formatFuzzyEntries(universe, expected));
		System.out.println("Actual:   " + formatFuzzyEntries(universe, actual.memberships()));
	}

	private static String formatFuzzyEntries(Set universe, double[] degrees) {
		StringBuilder result = new StringBuilder("{");
		int[] elements = universe.elements();
		for (int i = 0; i < elements.length; i++) {
			if (i > 0) {
				result.append(", ");
			}
			result.append(String.format(Locale.ROOT, "(%d, %.2f)", elements[i], degrees[i]));
		}
		return result.append('}').toString();
	}
}