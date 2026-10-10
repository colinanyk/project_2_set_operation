Team 4  
Rowan Bailey, Yakez Owens, Cassandra Keiley, Colin Nykanen  
Programming Project 2: Set Operations 

## 

# Pseudocode 

## Universe

Universe stores all possible elements that can belong to a set or multiset. Both classes use the same universe of at least 10 elements 

Class universe  
	Create a collection of atleast 10 unique elements  
		EX: A, B, C, D, E, F, G, H, I, J  
	Provide a way to access the universal elements  
	Return the universal Elements

## Set

Represent each subset using a Boolean array or bit string. Each position corresponds to an element in the universe 

Class set  
	Store the universal elements  
	Store a boolean array for the set membership

	Constructor(universe, elements)  
		Save the universe  
		Create a boolean array initialized to false  
	  
		For each element in the provided elements  
			Find it’s position in the universe  
			Set that position to true  
	  
Method Display  
	Display the boolean array   
	Display the set elements

Method Complement   
	Create an empty result array  
	  
	For each position i in the universe  
		If this sets boolean\[i\] is false  
			Add universe\[i\] to the result

Method union(otherset)  
	Create an empty result array   
For each position i in the universe  
            If this Boolean\[i\] is true or otherSet's Boolean\[i\] is true  
                Add universe\[i\] to the result

Method intersection(otherSet)  
        Create an empty result set

        For each position i in the universe  
            If this Boolean\[i\] is true and otherSet's Boolean\[i\] is true  
                Add universe\[i\] to the result

Method difference(otherSet)  
        Create an empty result set

        For each position i in the universe  
            If this Boolean\[i\] is true and otherSet's Boolean\[i\] is false  
                Add universe\[i\] to the result

	Method symmetricDifference(otherSet)  
        		Create an empty result set

For each position i in the universe  
      If this Boolean\[i\] is not equal otherSet's Boolean\[i\]  
                Add universe\[i\] to the result

## Multiset

A multiset tracks how many times each element appears. Instead of Boolean values, use a non-negative integer count for each universe element.

Class multiset  
Store the universal elements  
Store an integer array count

Constructor(universe, elements)  
Save the universe  
Initialize every count to 0

For each element in the provided elements  
       Find its position in the universe increase its count by 1

Method display()  
        Display the count array  
        Display the elements and their multiplicities

Method union(otherMultiset)  
        Create an empty result multiset

        For each position i in the universe  
            result.count\[i\] \= MAX(this.count\[i\], otherMultiset.count\[i\])

Method intersection(otherMultiset)  
        Create an empty result multiset

        For each position i in the universe  
            result.count\[i\] \= MIN(this.count\[i\], otherMultiset.count\[i\])

Method difference(otherMultiset)  
        Create an empty result multiset

        For each position i in the universe  
            result.count\[i\] \= MAX(0, this.count\[i\] \- otherMultiset.count\[i\])

Method sum(otherMultiset)  
        Create an empty result multiset

        For each position i in the universe  
            result.count\[i\] \= this.count\[i\] \+ otherMultiset.count\[i\]

 

