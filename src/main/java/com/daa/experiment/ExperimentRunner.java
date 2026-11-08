package com.daa.experiment;

import com.daa.editdistance.EditDistance;
import com.daa.editdistance.RecursiveEditDistance;
import com.daa.editdistance.TableEditDistance;
import com.daa.editdistance.TwoRowsEditDistance;
import com.daa.stringsearch.KMPSearch;
import com.daa.stringsearch.NaiveSearch;
import com.daa.stringsearch.StringSearch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ExperimentRunner {
    private static final int RUNS = 5;

    private final InputGenerator generator = new InputGenerator(42);
    private final List<String> csvRows = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new ExperimentRunner().runExperiments();
    }

    public void runExperiments() throws IOException {
        runEditDistanceExperiments();
        runStringSearchExperiments();
        saveResults();

        System.out.println("Experiments completed.");
        System.out.println("Results saved to results/results.csv");
    }

    private void runEditDistanceExperiments() {
        int[] recursiveSizes = {2, 4, 6, 8, 10};
        int[] dynamicSizes = {100, 250, 500, 1000};

        for (int size : recursiveSizes) {
            String randomA = generator.randomString(size);
            String randomB = generator.randomString(size);

            String challengingA = generator.repeatedString('A', size);
            String challengingB = generator.repeatedString('B', size);

            measureEditDistance(
                    "RecursiveEditDistance", new RecursiveEditDistance(),
                    "random", randomA, randomB, size
            );

            measureEditDistance(
                    "RecursiveEditDistance", new RecursiveEditDistance(),
                    "challenging", challengingA, challengingB, size
            );
        }

        for (int size : dynamicSizes) {
            String randomA = generator.randomString(size);
            String randomB = generator.randomString(size);

            String challengingA = generator.repeatedString('A', size);
            String challengingB = generator.repeatedString('B', size);

            measureEditDistance(
                    "TableEditDistance", new TableEditDistance(),
                    "random", randomA, randomB, size
            );

            measureEditDistance(
                    "TableEditDistance", new TableEditDistance(),
                    "challenging", challengingA, challengingB, size
            );

            measureEditDistance(
                    "TwoRowsEditDistance", new TwoRowsEditDistance(),
                    "random", randomA, randomB, size
            );

            measureEditDistance(
                    "TwoRowsEditDistance", new TwoRowsEditDistance(),
                    "challenging", challengingA, challengingB, size
            );
        }
    }

    private void runStringSearchExperiments() {
        int[] sizes = {1000, 5000, 10000, 20000};

        for (int size : sizes) {
            int patternLength = Math.max(1, size / 20);

            String randomText = generator.randomString(size);
            String randomPattern = generator.randomString(patternLength);

            String challengingText = generator.repeatedString('A', size);
            String challengingPattern =
                    generator.challengingPattern(patternLength);

            measureSearch(
                    "NaiveSearch", new NaiveSearch(),
                    "random", randomText, randomPattern, size
            );

            measureSearch(
                    "KMPSearch", new KMPSearch(),
                    "random", randomText, randomPattern, size
            );

            measureSearch(
                    "NaiveSearch", new NaiveSearch(),
                    "challenging", challengingText, challengingPattern, size
            );

            measureSearch(
                    "KMPSearch", new KMPSearch(),
                    "challenging", challengingText, challengingPattern, size
            );
        }
    }

    private void measureEditDistance(
            String name,
            EditDistance algorithm,
            String inputType,
            String a,
            String b,
            int size
    ) {
        List<Double> times = new ArrayList<>();
        List<Long> counters = new ArrayList<>();

        for (int run = 1; run <= RUNS; run++) {
            long start = System.nanoTime();
            int result = algorithm.distance(a, b);
            long end = System.nanoTime();

            times.add((end - start) / 1_000_000.0);
            counters.add(algorithm.getOperations());

            csvRows.add(String.format(
                    Locale.US,
                    "%s,%s,%d,%d,%.6f,%d",
                    name, inputType, size, run,
                    times.get(times.size() - 1),
                    counters.get(counters.size() - 1)
            ));
        }

        addMedianRow(name, inputType, size, times, counters);
    }

    private void measureSearch(
            String name,
            StringSearch algorithm,
            String inputType,
            String text,
            String pattern,
            int size
    ) {
        List<Double> times = new ArrayList<>();
        List<Long> counters = new ArrayList<>();

        for (int run = 1; run <= RUNS; run++) {
            long start = System.nanoTime();
            algorithm.search(text, pattern);
            long end = System.nanoTime();

            times.add((end - start) / 1_000_000.0);
            counters.add(algorithm.getComparisons());

            csvRows.add(String.format(
                    Locale.US,
                    "%s,%s,%d,%d,%.6f,%d",
                    name, inputType, size, run,
                    times.get(times.size() - 1),
                    counters.get(counters.size() - 1)
            ));
        }

        addMedianRow(name, inputType, size, times, counters);
    }

    private void addMedianRow(
            String name,
            String inputType,
            int size,
            List<Double> times,
            List<Long> counters
    ) {
        List<Double> sortedTimes = new ArrayList<>(times);
        List<Long> sortedCounters = new ArrayList<>(counters);

        Collections.sort(sortedTimes);
        Collections.sort(sortedCounters);

        double medianTime = sortedTimes.get(RUNS / 2);
        long medianCounter = sortedCounters.get(RUNS / 2);

        csvRows.add(String.format(
                Locale.US,
                "%s,%s,%d,median,%.6f,%d",
                name, inputType, size, medianTime, medianCounter
        ));
    }

    private void saveResults() throws IOException {
        Path directory = Path.of("results");
        Files.createDirectories(directory);

        Path file = directory.resolve("results.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(
                file, StandardCharsets.UTF_8
        )) {
            writer.write("algorithm,input,n,run,time_ms,counter");
            writer.newLine();

            for (String row : csvRows) {
                writer.write(row);
                writer.newLine();
            }
        }
    }
}