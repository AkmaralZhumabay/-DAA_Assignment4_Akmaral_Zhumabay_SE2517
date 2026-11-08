package com.daa.editdistance;

public class TwoRowsEditDistance implements EditDistance {

    private long operations = 0;

    @Override
    public int distance(String a, String b) {
        operations = 0;

        int n = a.length();
        int m = b.length();

        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            previous[j] = j;
        }

        // State: previous and current store distances for consecutive rows.
        // Transition: calculate each cell using the previous row and current row.
        // Base case: previous[j] = j and current[0] = i.

        for (int i = 1; i <= n; i++) {
            current[0] = i;

            for (int j = 1; j <= m; j++) {
                operations++;

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    int delete = previous[j];
                    int insert = current[j - 1];
                    int replace = previous[j - 1];

                    current[j] = 1 + Math.min(
                            delete,
                            Math.min(insert, replace)
                    );
                }
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[m];
    }

    @Override
    public long getOperations() {
        return operations;
    }
}