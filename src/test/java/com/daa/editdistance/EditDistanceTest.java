package com.daa.editdistance;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EditDistanceTest {

    private final EditDistance[] algorithms = {
            new RecursiveEditDistance(),
            new TableEditDistance(),
            new TwoRowsEditDistance()
    };

    @Test
    void testReferenceValues() {
        String[][] inputs = {
                {"kitten", "sitting"},
                {"flaw", "lawn"},
                {"intention", "execution"},
                {"", "abc"},
                {"abc", "abc"}
        };

        int[] expected = {3, 2, 5, 3, 0};

        for (int i = 0; i < inputs.length; i++) {
            for (EditDistance algorithm : algorithms) {
                assertEquals(
                        expected[i],
                        algorithm.distance(inputs[i][0], inputs[i][1])
                );
            }
        }
    }

    @Test
    void testEmptyStrings() {
        for (EditDistance algorithm : algorithms) {
            assertEquals(0, algorithm.distance("", ""));
            assertEquals(5, algorithm.distance("", "hello"));
            assertEquals(5, algorithm.distance("hello", ""));
        }
    }

    @Test
    void testRandomStrings() {
        Random random = new Random(42);
        String alphabet = "abcde";

        for (int test = 0; test < 100; test++) {
            String a = randomString(random, alphabet);
            String b = randomString(random, alphabet);

            int expected = algorithms[0].distance(a, b);

            assertEquals(expected, algorithms[1].distance(a, b));
            assertEquals(expected, algorithms[2].distance(a, b));
        }
    }

    private String randomString(Random random, String alphabet) {
        int length = random.nextInt(7);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return result.toString();
    }
}