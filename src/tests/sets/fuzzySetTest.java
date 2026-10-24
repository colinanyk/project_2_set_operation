package tests.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import sets.Set;

/**
 * NOTES:
 * 
 * Once the design plan for fuzzy set creation is established, just modify the relevant parts 
 * for the sets here accordingly (and uncomment some at the bottom)
 * tests are representations until then
 * 
 * - no membership assignment for an element should default that elements membership to 0.0
 * - memberships < 0.0 or > 1.0 should throw illegal argument exception
 * - ensure documentation a small tolerance for comparing floating-point results (canvas req)
 * 
 */
class FuzzySetTest {

	private Set U = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
	private Set A = Set.of{(3, 1.0), (5, 0.6), (7, 0.1), (9, 0.9)};
	private Set B = Set.of{(1, 0.4), (2, 0.5), (9, 0.5), (10, 0.8), (11, 0.2), (12, 0.7)};
	private Set a = Set.of();
	
	@Test
	void complementUsesFuzzySet() {
		
		FuzzySet result = A.complement(U);
    	
    	assertEquals("{(1, 1.0), (2, 1.0), (3, 0.0), (4, 1.0), (5, 0.4), (6, 1.0), (7, 0.9), "
    			+ "(8, 1.0), (9, 0.1), (10, 1.0), (11, 1.0), (12, 1.0)}", result.display(U));
	}
	
	@Test
	void unionUsesFuzzySet() {
		
		FuzzySet result = A.union(B);
    	
    	assertEquals("{(1, 0.4), (2, 0.5), (3, 1.0), (5, 0.6), (7, 0.1), (9, 0.9), "
    			+ "(10, 0.8), (11, 0.2), (12, 0.7)}", result.display(U));
	}
	
	@Test
	void intersectionUsesFuzzySet() {
		
		FuzzySet result = A.intersection(B);
    	
    	assertEquals("{(9, 0.5)}", result.display(U));
		
	}
	
	@Test
	void emptyMembershipFuzzySet() {
		
		FuzzySet result = a.complement(U);
    	
    	assertEquals("{(1, 1.0), (2, 1.0), (3, 1.0), (4, 1.0), (5, 1.0), (6, 1.0), (7, 1.0), "
    			+ "(8, 1.0), (9, 1.0), (10, 1.0), (11, 1.0), (12, 1.0)}", result.display(U));
	}
	
	@Test
	void positiveMembershipException() {
		
		//private Set C = Set.of{(1, 0.4), (2, 1.1), (3, 0.7), (4, 0.8), (5, 0.6)}
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new FuzzySet(U, C));
		
//TODO add catch message here about membership index out of bounds
    	assertEquals("Message here", exception.getMessage());
	}
	
	//memberships < 0.0 should throw illegal argument exception
	@Test
	void negativeMembershipException() {
		
		//private Set c = Set.of{(1, 0.4), (2, -0.1), (3, 0.7), (4, 0.8), (5, 0.6)}
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new FuzzySet(U, c));
		
//TODO add catch message here about membership index out of bounds
    	assertEquals("Message here", exception.getMessage());
		
	}
	
	@Test
	void nullUniverseException() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new FuzzySet(null, A));
		
//TODO add catch message here about null universe
    	assertEquals("Message here", exception.getMessage());
	}
	
	@Test
	void nullMembershipException() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new FuzzySet(U, null));
    	
//TODO add catch message here about null membership assignment
    	assertEquals("Message here", exception.getMessage());
		
	}
	

}
