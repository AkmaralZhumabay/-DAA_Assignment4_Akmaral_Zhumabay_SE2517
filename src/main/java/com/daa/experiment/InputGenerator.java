package com.daa.experiment;

import java.util.Random;

public class InputGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Random random;

    public InputGenerator(long seed) {
        this.random = new Random(seed);
    }

    public String randomString(int length) {
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return result.toString();
    }

    public String repeatedString(char character, int length) {
        return String.valueOf(character).repeat(length);
    }

    public String challengingPattern(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Pattern length must be positive");
        }

        return "A".repeat(length - 1) + "B";
    }
}