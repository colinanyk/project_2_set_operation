package sets;

import java.util.Arrays;

/**
 * With a boolean array, a set of whole numbers is used.
 * @author Yakez Owens CS2430
 */
public class Set {
    private boolean[] values;

    private Set(int length) {
        values = new boolean[length];
    }

    public int length() {
        return values.length;
    }

    public int[] elements() {
        int[] result = new int[size()];
        int index = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i]) {
                result[index++] = i;
            }
        }
        return result;
    }

    //From given numbers a set will be made.
    public static Set of(int... numbers) {
        int largest = 0;
        for (int number : numbers) {
            if (number < 0) {
                throw new IllegalArgumentException("Numbers cannot be negative");
            }
            if (number > largest) {
                largest = number;
            }
        }

        Set result = new Set(largest + 1);
        for (int number : numbers) {
            result.values[number] = true;
        }
        return result;
    }

    public boolean contains(int number) {
        return number >= 0 && number < values.length && values[number];
    }

    public int size() {
        int total = 0;
        for (boolean value : values) {
            if (value) {
                total++;
            }
        }
        return total;
    }

    //With the connections, it is showed from universal set order. 
    public String display(Set universe) {
        String result = "";
        for (int i = 0; i < universe.values.length; i++) {
            if (universe.values[i]) {
                result += contains(i) ? "1" : "0";
            }
        }
        return result;
    }

    public void display() {
        System.out.println(Arrays.toString(values));
        System.out.println(this);
    }

    public Set complement(Set universe) {
        Set result = new Set(universe.values.length);
        for (int i = 0; i < universe.values.length; i++) {
            result.values[i] = universe.values[i] && !contains(i);
        }
        return result;
    }

    public Set union(Set other) {
        Set result = new Set(Math.max(values.length, other.values.length));
        for (int i = 0; i < result.values.length; i++) {
            result.values[i] = contains(i) || other.contains(i);
        }
        return result;
    }

    public Set intersection(Set other) {
        Set result = new Set(Math.max(values.length, other.values.length));
        for (int i = 0; i < result.values.length; i++) {
            result.values[i] = contains(i) && other.contains(i);
        }
        return result;
    }

    public Set difference(Set other) {
        Set result = new Set(values.length);
        for (int i = 0; i < values.length; i++) {
            result.values[i] = values[i] && !other.contains(i);
        }
        return result;
    }

    public Set symmetricDiff(Set other) {
        Set result = new Set(Math.max(values.length, other.values.length));
        for (int i = 0; i < result.values.length; i++) {
            result.values[i] = contains(i) != other.contains(i);
        }
        return result;
    }

    public Set symmetricDifference(Set other) {
        return symmetricDiff(other);
    }

    @Override
    public String toString() {
        String result = "{";
        boolean first = true;
        for (int i = 0; i < values.length; i++) {
            if (values[i]) {
                if (!first) {
                    result += ", ";
                }
                result += i;
                first = false;
            }
        }
        return result + "}";
    }
}

