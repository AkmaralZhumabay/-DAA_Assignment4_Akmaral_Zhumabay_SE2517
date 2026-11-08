package com.daa.stringsearch;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch implements StringSearch {

    private long comparisons = 0;

    @Override
    public List<Integer> search(String text, String pattern) {
        comparisons = 0;

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }

        List<Integer> matches = new ArrayList<>();

        if (pattern.length() > text.length()) {
            return matches;
        }

        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;

            while (j < pattern.length()) {
                comparisons++;

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }

                j++;
            }

            if (j == pattern.length()) {
                matches.add(i);
            }
        }

        return matches;
    }

    @Override
    public long getComparisons() {
        return comparisons;
    }
}