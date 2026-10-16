package tests.sets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sets.MultiSet;

class MultiSetTest {

    @Test
    void unionUsesMaxMultiplicity() {
        MultiSet a = new MultiSet(new int[] {2, 1, 0, 3, 1});
        MultiSet b = new MultiSet(new int[] {1, 2, 1, 0, 2});

        MultiSet result = a.union(b);

        assertEquals(2, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(1, result.get(2));
        assertEquals(3, result.get(3));
        assertEquals(2, result.get(4));
    }

    @Test
    void intersectionUsesMinMultiplicity() {
        MultiSet a = new MultiSet(new int[] {2, 1, 0, 3, 1});
        MultiSet b = new MultiSet(new int[] {1, 2, 1, 0, 2});

        MultiSet result = a.intersection(b);

        assertEquals(1, result.get(0));
        assertEquals(1, result.get(1));
        assertEquals(0, result.get(2));
        assertEquals(0, result.get(3));
        assertEquals(1, result.get(4));
    }

    @Test
    void differenceSubtractsCountsWithoutGoingNegative() {
        MultiSet a = new MultiSet(new int[] {2, 1, 0, 3, 1});
        MultiSet b = new MultiSet(new int[] {1, 2, 1, 0, 2});

        MultiSet result = a.difference(b);

        assertEquals(1, result.get(0));
        assertEquals(0, result.get(1));
        assertEquals(0, result.get(2));
        assertEquals(3, result.get(3));
        assertEquals(0, result.get(4));
    }

    @Test
    void sumAddsCounts() {
        MultiSet a = new MultiSet(new int[] {2, 1, 0, 3, 1});
        MultiSet b = new MultiSet(new int[] {1, 2, 1, 0, 2});

        MultiSet result = a.sum(b);

        assertEquals(3, result.get(0));
        assertEquals(3, result.get(1));
        assertEquals(1, result.get(2));
        assertEquals(3, result.get(3));
        assertEquals(3, result.get(4));
    }

    @Test
    void displayUsesUniverseOrder() {
        MultiSet set = new MultiSet(new int[] {2, 0, 1, 0, 3});
        assertTrue(set.display(sets.Set.of(0, 2, 4)).contains("2"));
    }
}

