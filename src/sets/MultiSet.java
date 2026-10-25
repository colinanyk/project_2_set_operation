package sets;

import java.util.Arrays;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * 
 * Constructs a multiset (bag) of integers from a specified universe. Each element allows for
 * non-negative multiplicity.
 * Uses Guava's Multiset implementation to store and perform multiset operations. Calls to quava 
 * functions already check for null inputs against both multisets
 * 
 * Source: guava-33.7.2-jre (Included in project)
 * 
 * Modifications: Additional helper methods that allow compatability between reference library-based 
 * multisets and project specific implementations in class {main} and allows modification of
 * independent objects from reference library's view-only multisets
 * 
 * @Author Cassandra Keiley
 */
public class MultiSet {
	
	private final Set universe;
	private final Multiset<Integer> multiset;
	
	/**
	 * Constructs a guava-based multiset using the given universe and multiplicity inputs.
	 * Each index in {counts} corresponds to its equivilent index in {universe}
	 * 
	 * @param universe		set of elements to be modified
	 * @param counts		multiplicities for each element to take on
	 */
	public MultiSet(Set universe, int[] counts) {
		
		if (universe == null) throw new IllegalArgumentException("Universe cannot be null");
		if (counts == null) throw new IllegalArgumentException("Counts array cannot be null");
		
		int[] elements = universe.elements();
		
		if (counts.length != elements.length) {
			throw new IllegalArgumentException("Counts array must match universe size");
		}
		for (int count : counts) {
			if (count < 0) throw new IllegalArgumentException("Counts array inputs cannot be negative");
		}
		
		this.universe = universe;
		this.multiset = HashMultiset.create();
		
		for (int i = 0; i < elements.length; i++) {
			multiset.add(elements[i], counts[i]);
		}
		
	}
	
	/**
	 * Converts guava-based Multiset into main-class compatable MultiSet
	 * 
	 * @param universe
	 * @param source
	 */
	private MultiSet(Set universe, Multiset<Integer> source) {
		this.universe = universe;
		this.multiset = HashMultiset.create(source);
	}
	
	/**
	 * Returns a string representation of the multiset multiplicities.
	 * Absent elements have a count of 0
	 * 
	 * @param universe
	 * @return string representation of multiplicity array
	 */
	public String display(Set universe) {
		requireSameUniverse(universe);
		int[] elements = universe.elements();
		int[] counts = new int[elements.length];

		
		for (int i = 0; i < elements.length; i++) {
			counts[i] = multiset.count(elements[i]);
		}
		return Arrays.toString(counts);
	}

	/** Returns multiplicities in this multiset's fixed universe order. */
	public String display() {
		return display(universe);
	}
	
	/**
	 * Returns the union of two multisets, using the greater multiplicity per element
	 * 
	 * @param other		multiset to be combined
	 * @return			new multiset containing maximum count of each element
	 */
	public MultiSet union(MultiSet other){
		requireSameUniverse(other);
		return new MultiSet(universe, Multisets.union(multiset, other.multiset));
	}
	
	/**
	 * Returs the intersection of two multisets, using the smaller multiplicity per element
	 * 
	 * @param other		multiset to be intersected
	 * @return			new multiset containing minimum count of each element
	 */
	public MultiSet intersection(MultiSet other){
		requireSameUniverse(other);
		return new MultiSet(universe, Multisets.intersection(multiset, other.multiset));
	}
		
	/**
	 * Returns the difference between two multisets by subtracting {other} multisets
	 * multiplicities from {this} multisets, with a minimum count of 0
	 * 
	 * @param other		multiset with counts that are subtracted
	 * @return			new multiset containing the difference between the two
	 */
	public MultiSet difference(MultiSet other){
		requireSameUniverse(other);
		return new MultiSet(universe, Multisets.difference(multiset, other.multiset));
	}
	
	/**
	 * Returns multiset sum by adding the multiplicities of corresponding elements between each
	 * 
	 * @param other		multiset whose counts are added to {this} multiset
	 * @return			new multiset containing the sum of each elements count
	 */
	public MultiSet sum(MultiSet other){
		requireSameUniverse(other);
		return new MultiSet(universe, Multisets.sum(multiset, other.multiset));
	}

	private void requireSameUniverse(MultiSet other) {
		if (other == null) {
			throw new IllegalArgumentException("Other multiset cannot be null");
		}
		requireSameUniverse(other.universe);
	}

	private void requireSameUniverse(Set otherUniverse) {
		if (otherUniverse == null || !Arrays.equals(universe.elements(), otherUniverse.elements())) {
			throw new IllegalArgumentException("Multisets must use the same universe");
		}
	}
	
	@Override
	public String toString() {
		return multiset.toString();
	}

}

