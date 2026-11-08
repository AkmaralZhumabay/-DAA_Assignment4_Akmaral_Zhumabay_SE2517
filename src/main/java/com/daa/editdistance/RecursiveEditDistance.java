package com.daa.editdistance;

public class RecursiveEditDistance implements EditDistance {

    private long operations = 0;

    @Override
    public int distance(String a, String b) {
        operations = 0;
        return calculate(a, b, a.length(), b.length());
    }

    // State: distance between the first i characters of a and j characters of b.
    // Transition: match the last characters or choose the minimum of three edits.
    // Base case: if one prefix is empty, return the length of the other prefix.
    private int calculate(String a, String b, int i, int j) {
        operations++;

        // Base cases: one of the strings is empty.
        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        // If the last characters are equal, no operation is needed.
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return calculate(a, b, i - 1, j - 1);
        }

        // Otherwise, try deletion, insertion and replacement.
        int delete = calculate(a, b, i - 1, j);
        int insert = calculate(a, b, i, j - 1);
        int replace = calculate(a, b, i - 1, j - 1);

        return 1 + Math.min(delete, Math.min(insert, replace));
    }

    @Override
    public long getOperations() {
        return operations;
    }
}