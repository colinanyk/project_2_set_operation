package tests.sets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import sets.MultiSet;
import sets.Set;

class MultiSetTest {
	
	private Set U = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
	
	MultiSet A = new MultiSet(U, new int[] {2, 1, 0, 3, 0, 1, 2, 0, 0, 1, 2, 0});
	MultiSet B = new MultiSet(U, new int[] {1, 0, 2, 1, 0, 1, 1, 0, 0, 2, 1, 3});
	
	MultiSet a = new MultiSet(U, new int[] {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
	MultiSet b = new MultiSet(U, new int[] {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
	
	MultiSet empty1 = new MultiSet(U, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
	MultiSet empty2 = new MultiSet(U, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
	

    @Test
    void multisetUnion() {

        MultiSet result = A.union(B);

        assertEquals("[2, 1, 2, 3, 0, 1, 2, 0, 0, 2, 2, 3]", result.display(U));
    }
    
    @Test
    void multisetIntersection() {
    	
    	MultiSet result = A.intersection(B);
    	
    	assertEquals("[1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0]", result.display(U));
    	
    }
    
    @Test
    void multisetDifferenceOfAandB() {

    	MultiSet result = A.difference(B);
    	
    	assertEquals("[1, 1, 0, 2, 0, 0, 1, 0, 0, 0, 1, 0]", result.display(U));
    	
    }

    @Test
    void multisetSum() {

    	MultiSet result = A.sum(B);
        
    	assertEquals("[3, 1, 2, 4, 0, 2, 3, 0, 0, 3, 3, 3]", result.display(U));
    }

    @Test
    void unionofTwoEmptyMultisets() {

        MultiSet result = empty1.union(empty2);
        
        assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", result.display(U));
    }

    @Test
    void multisetUnionWithUnequalMultiplicities() {

        MultiSet result = a.union(b);
        
        assertEquals("[5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", result.display(U));
    }

    @Test
    void multisetIntersectionwithUnequalMultiplicities() {

    	MultiSet result = a.intersection(b);
        
        assertEquals("[2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", result.display(U));
    }
    
    @Test
    void multisetSumWithUnequalMultiplicities() {
    	
    	MultiSet result = a.sum(b);
        
        assertEquals("[7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", result.display(U));
    }
    
    @Test
    void multisetDifferenceWithNegativeResult() {

        MultiSet result = a.difference(b);
        
        assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", result.display(U));
    }
    
    @Test
    void nullUniverseIllegalArgumentException() {
    	
    	int[] counts = {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    	
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new MultiSet(null, counts));
    			
    	assertEquals("Universe cannot be null", exception.getMessage());
    	
    }
    
    @Test
    void nullCountIllegalArgumentException() {
    	
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new MultiSet(U, null));
    			
    	assertEquals("Counts array cannot be null", exception.getMessage());
    	
    }
    
    @Test
    void countLengthMismatchIllegalArgumentException() {
    	
    	int[] counts = {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    	
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new MultiSet(U, counts));
    			
    	assertEquals("Counts array must match universe size", exception.getMessage());
    }
    
    @Test
    void negativeMultiplicityIllegalArgumentException() {
    	
    	int[] counts = {-2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    	
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> new MultiSet(U, counts));
    			
    	assertEquals("Counts array inputs cannot be negative", exception.getMessage());
    	
    }
    
}

