package tests.sets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sets.OrdinarySet;
import sets.Set;

class OrdinarySetTest {

    private Set universe;
    private OrdinarySet a;
    private OrdinarySet b;
    private OrdinarySet empty;
    private OrdinarySet universal;

    @BeforeEach
    void setUp() {
        universe = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        a = OrdinarySet.of(universe, 1, 3, 5, 7, 9, 11);
        b = OrdinarySet.of(universe, 2, 3, 6, 7, 10, 11, 12);
        empty = OrdinarySet.of(universe);
        universal = OrdinarySet.of(universe, universe.elements());
    }

    @Test
    void inputsUseUniversalOrder() {
        assertEquals("101010101010", a.display());
        assertEquals("011001100111", b.display());
    }

    @Test
    void set1ComplementOfA() {
        OrdinarySet result = a.complement();

        assertSet(result, "010101010101", 2, 4, 6, 8, 10, 12);
        for (int element : universe.elements()) {
            assertTrue(a.contains(element) ^ result.contains(element));
        }
    }

    @Test
    void set2UnionOfAAndB() {
        assertSet(a.union(b), "111011101111", 1, 2, 3, 5, 6, 7, 9, 10, 11, 12);
    }

    @Test
    void set3IntersectionOfAAndB() {
        assertSet(a.intersection(b), "001000100010", 3, 7, 11);
    }

    @Test
    void set4DifferenceOfAAndB() {
        assertSet(a.difference(b), "100010001000", 1, 5, 9);
    }

    @Test
    void set5SymmetricDifferenceOfAAndB() {
        OrdinarySet result = a.symmetricDifference(b);

        assertSet(result, "110011001101", 1, 2, 5, 6, 9, 10, 12);
        assertFalse(result.contains(3));
        assertFalse(result.contains(7));
        assertFalse(result.contains(11));
    }

    @Test
    void set6ComplementOfEmptyIsUniversal() {
        assertSet(empty.complement(), "111111111111", universe.elements());
    }

    @Test
    void set7EmptyUnionUniversalIsUniversal() {
        assertSet(empty.union(universal), "111111111111", universe.elements());
    }

    @Test
    void set8EmptyIntersectionUniversalIsEmpty() {
        assertSet(empty.intersection(universal), "000000000000");
    }

    @Test
    void set9EmptyDifferenceUniversalIsEmpty() {
        assertSet(empty.difference(universal), "000000000000");
    }

    @Test
    void set10IdenticalSymmetricDifferenceIsEmpty() {
        OrdinarySet identical = OrdinarySet.of(universe, 1, 2, 3);

        assertSet(identical.symmetricDifference(identical), "000000000000");
    }

    @Test
    void set11ComplementOfUniversalIsEmpty() {
        assertSet(universal.complement(), "000000000000");
    }

    private static void assertSet(OrdinarySet actual, String bitString, int... elements) {
        assertEquals(bitString, actual.display());
        assertArrayEquals(elements, actual.elements());
        assertEquals(elements.length, actual.size());
    }
}

