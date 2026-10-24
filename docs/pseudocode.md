Team 4  
Rowan Bailey, Yakez Owens, Cassandra Keiley, Colin Nykanen  
Programming Project 2: Set Operations 

# Pseudocode 

## **1\. Main Class**

The Main class creates the universal set, example sets, and example multisets. It executes the required operations and displays their results.

### **Method main(args)**

1. Create the universal set using the integers 0 through 9\.  
2. Create Set A containing the integers 0, 2, 4, and 6\.  
3. Create Set B containing the integers 2, 3, 5, and 6\.  
4. Display the universal set, Set A, and Set B.  
5. Calculate and display the complement of Set A using the universal set.  
6. Calculate and display the union of Set A and Set B.  
7. Calculate and display the intersection of Set A and Set B.  
8. Calculate and display the difference of Set A and Set B.  
9. Calculate and display the symmetric difference of Set A and Set B.  
10. Create an integer array containing the multiplicities for Multiset A  
11. Create an integer array containing the multiplicities for Multiset B  
12. Create Multiset A and Multiset B using the universal set and their corresponding count arrays.  
13. Display both multisets.  
14. Calculate and display the multiset union.  
15. Calculate and display the multiset intersection.  
16. Calculate and display the multiset difference.  
17. Calculate and display the multiset sum.  
18. Create an empty ordinary set using Set.of()  
19. Calculate and display the union and intersection of Set A with the empty set.  
20. Create an empty multiset using an array of ten zeros.  
21. Calculate and display the sum and difference of Multiset A with the empty multiset.

### 

### 

### **Method displaySet(label, result, universe)**

**Parameters:** A label, a Set object to display, and the universal set.

1. Display the label.  
2. Call result.display(universe) to obtain the bit string  
3. Display the bit string.  
4. Display the set elements using result.toString()

### **Method displayMultiset(label, result, universe)**

**Parameters:** A label, a MultiSet object to display, and the universal set.

1. Display the label.  
2. Call result.display(universe) to obtain the multiplicity array.  
3. Display the multiplicity array.  
4. Display the multiset elements and their multiplicities using result.toString()

## **2\. Set Class**

The Set class represents a set of nonnegative integers using a Boolean array. Each index corresponds to an integer, and true indicates that the integer belongs to the set.

### **Data**

* Declare a private Boolean array named values.

### **Constructor Set(length)**

1. Create a Boolean array of the specified length.  
2. Initialize all positions to false.

### **Method length()**

1. Return the length of the Boolean array.

### **Method elements()**

1. Call size() to determine how many elements belong to the set.  
2. Create an integer array with that many positions.  
3. Initialize an index counter to zero.  
4. Iterate through the Boolean array.  
5. If a position contains true, store its index in the result array and increment the counter.  
6. Return the result array.

### **Static Method of(numbers)**

**Parameter:** A variable number of integer arguments.

1. Initialize the largest number to zero.  
2. Iterate through the provided numbers.  
3. If any number is negative, throw an IllegalArgumentException  
4. Find the largest provided number.  
5. Create a Set with an array length equal to the largest number plus one.  
6. Iterate through the provided numbers again.  
7. Set the Boolean array position corresponding to each number to true.  
8. Return the completed Set.

### **Method contains(number)**

1. Check whether the number is nonnegative and less than the array length.  
2. If the number is within bounds, return the Boolean value at its index.  
3. Otherwise, return false.

### **Method size()**

1. Initialize a total counter to zero.  
2. Iterate through every Boolean value.  
3. If the value is true, increment the counter.  
4. Return the total.

### **Method display(universe)**

**Parameter:** The universal Set defining which elements and indices to display.

1. Initialize an empty string.  
2. Iterate through every index in the universe's Boolean array.  
3. If the universe contains the element at that index:  
   * Append 1 if the current set contains the element.  
   * Otherwise, append 0  
4. Return the completed bit string.

### **Method display()**

1. Print the Boolean array using an array-to-string conversion.  
2. Print the current Set using its string representation.

### 

### **Method complement(universe)**

1. Create a new Set with the same array length as the universe.  
2. Iterate through every index in the universe.  
3. Set the result position to true if the universe contains the element and the current set does not.  
4. Otherwise, set the result position to false.  
5. Return the result Set.

### **Method union(other)**

1. Create a new Set with a length equal to the greater of the current set's length and the other set's length.  
2. Iterate through every index in the result array.  
3. Set the result position to true if either set contains the corresponding integer.  
4. Otherwise, set it to false.  
5. Return the result Set.

### **Method intersection(other)**

1. Create a new Set with a length equal to the greater of the two set lengths.  
2. Iterate through every index in the result array.  
3. Set the result position to `true` if both sets contain the corresponding integer.  
4. Otherwise, set it to `false`.  
5. Return the result Set.

### **Method difference(other)**

1. Create a new Set with the same array length as the current set.  
2. Iterate through every index of the current set.  
3. Set the result position to `true` if the current set contains the integer and the other set does not.  
4. Return the result Set.

### **Method symmetricDiff(other)**

1. Create a new Set with a length equal to the greater of the two set lengths.  
2. Iterate through every index in the result array.  
3. Compare whether each set contains the corresponding integer.  
4. Set the result position to `true` if the two membership values are different.  
5. Otherwise, set it to `false`.  
6. Return the result Set.

### **Method symmetricDifference(other)**

1. Call symmetricDiff(other)  
2. Return the resulting Set

### **Method toString()**

1. Initialize a string with an opening curly brace.  
2. Initialize a Boolean flag indicating that no elements have been added yet.  
3. Iterate through the Boolean array.  
4. For each position containing true:  
   * If an element has already been added, append a comma and a space.  
   * Append the index of the element.  
   * Update the flag to indicate that an element has been added.  
5. Append a closing curly brace.  
6. Return the completed string.

## **3\. MultiSet Class**

The MultiSet class represents a collection of nonnegative integers in which elements may occur multiple times. It uses Google's Guava HashMultiset to store the elements and track their multiplicities.

### **Data**

* Declare a private final Set named universe.  
* Declare a private final Guava Multiset\<Integer\> named multiset.

### **Constructor MultiSet(universe, counts)**

**Parameters:** The universal Set and an integer array of multiplicities.

1. Obtain the universe's elements using universe.elements()  
2. Store the universe in the universe field.  
3. Create an empty Guava HashMultiset.  
4. Iterate through the universe's elements.  
5. For each element, add it to the multiset the number of times specified by the corresponding position in counts.  
6. Finish constructing the multiset.

### 

### 

### **Private Constructor MultiSet(universe, source)**

**Parameters:** The universal Set and a source Guava multiset.

1. Store the universe in the universe field.  
2. Create a new HashMultiset using the source multiset.  
3. Store the independent copy in the multiset field.

### **Method display(universe)**

**Parameter:** The Set defining the element order for display.

1. Obtain the universe's elements using universe.elements()  
2. Create an integer array with the same length as the elements array.  
3. Iterate through every element in the universe.  
4. Obtain the multiplicity of the current element using multiset.count(element)  
5. Store the multiplicity at the corresponding position in the count array.  
6. Convert the count array to a string using an array-to-string conversion.  
7. Return the resulting string.

### **Method union(other)**

1. Call Guava's Multisets.union() with the current multiset and the other multiset.  
2. Obtain a multiset view in which each element has the greater of its two multiplicities.  
3. Create and return a new MultiSet using the current universe and the resulting multiset.

### **Method intersection(other)**

1. Call Guava's Multisets.intersection() with the current multiset and the other multiset.  
2. Obtain a multiset view in which each element has the smaller of its two multiplicities.  
3. Create and return a new MultiSet using the current universe and the resulting multiset.

### **Method difference(other)**

1. Call Guava's Multisets.difference() with the current multiset and the other multiset.  
2. Obtain a multiset view containing the remaining multiplicities after subtracting the other multiset.  
3. Elements with zero remaining multiplicity are absent from the result.  
4. Create and return a new MultiSet using the current universe and the resulting multiset.

### **Method sum(other)**

1. Call Guava's Multisets.sum() with the current multiset and the other multiset.  
2. Obtain a multiset view in which corresponding multiplicities are added together.  
3. Create and return a new MultiSet using the current universe and the resulting multiset.

### **Method toString()**

1. Convert the Guava multiset into its string representation.  
2. Return the string showing the elements and their multiplicities.

