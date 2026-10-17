package tests.sets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sets.MultiSet;
import sets.Set;

class MultiSetTest {
	
	private Set U = Set.of(1,2,3,4,5);
	private Set custom = Set.of(0, 2, 4);
	
	MultiSet a = new MultiSet(U, new int[] {2, 1, 0, 3, 1});
	MultiSet b = new MultiSet(U, new int[] {1, 2, 1, 0, 2});


    @Test
    void unionUsesMaxMultiplicity() {

        MultiSet result = a.union(b);

        assertEquals("[2, 2, 1, 3, 2]", result.display(U));
    }

    @Test
    void intersectionUsesMinMultiplicity() {

        MultiSet result = a.intersection(b);
        
        assertEquals("[1, 1, 0, 0, 1]", result.display(U));
    }

    @Test
    void differenceSubtractsCountsWithoutGoingNegative() {

        MultiSet result = a.difference(b);
        
        assertEquals("[1, 0, 0, 3, 0]", result.display(U));
    }

    @Test
    void sumAddsCounts() {

        MultiSet result = a.sum(b);
        
        assertEquals("[3, 3, 1, 3, 3]", result.display(U));
    }

    @Test
    void displayUsesUniverseOrder() {
        MultiSet set = new MultiSet(custom, new int[] {2, 0, 3});
        
        assertEquals("[2, 0, 3]", set.display(custom));
    }
}

