package com.daa.stringsearch;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StringSearchTest {

    private final StringSearch naive = new NaiveSearch();
    private final KMPSearch kmp = new KMPSearch();

    @Test
    void testLpsReferenceValues() {
        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void testSearchReferenceValues() {
        assertSearch("ABABDABACDABABCABAB", "ABABCABAB", List.of(10));
        assertSearch("AAAAA", "AA", List.of(0, 1, 2, 3));
        assertSearch("ABABABA", "ABA", List.of(0, 2, 4));
    }

    @Test
    void testNoMatches() {
        assertSearch("ABCDEFG", "XYZ", List.of());
    }

    @Test
    void testPatternLongerThanText() {
        assertSearch("ABC", "ABCDE", List.of());
    }

    @Test
    void testEmptyPattern() {
        assertThrows(
                IllegalArgumentException.class,
                () -> naive.search("ABC", "")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> kmp.search("ABC", "")
        );
    }

    @Test
    void testRandomStrings() {
        Random random = new Random(42);
        String alphabet = "ABC";

        for (int test = 0; test < 100; test++) {
            String text = randomString(random, alphabet, random.nextInt(21));
            String pattern = randomString(random, alphabet, random.nextInt(6) + 1);

            assertEquals(
                    naive.search(text, pattern),
                    kmp.search(text, pattern)
            );
        }
    }

    private void assertSearch(
            String text,
            String pattern,
            List<Integer> expected
    ) {
        assertEquals(expected, naive.search(text, pattern));
        assertEquals(expected, kmp.search(text, pattern));
    }

    private String randomString(Random random, String alphabet, int length) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return result.toString();
    }
}