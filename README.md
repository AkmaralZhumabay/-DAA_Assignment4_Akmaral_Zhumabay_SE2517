# DAA Assignment 4 — Dynamic Programming and String Algorithms

**Course:** Design and Analysis of Algorithms  
**Student:** Akmaral Zhumabay  
**Group:** SE2517

## Project Overview

This project implements and compares algorithms for Edit Distance and String Search.

### Implemented Algorithms

**Edit Distance**
- Recursive Edit Distance
- Bottom-Up Table Edit Distance
- Two-Rows Edit Distance

**String Search**
- Naive String Search
- Knuth-Morris-Pratt (KMP) Search

All implementations include operation or comparison counters.

## Project Structure

```text
src/
├── main/java/com/daa/
│   ├── editdistance/
│   ├── stringsearch/
│   └── experiment/
└── test/java/com/daa/
    ├── editdistance/
    └── stringsearch/

results/
├── results.csv
└── plots/

README.md
REPORT.md
pom.xml
plot_results.py
```

## Requirements

- Java 17 or later
- Maven
- Python 3
- Matplotlib (for generating plots)

Install Matplotlib:

```bash
python -m pip install matplotlib
```

## Build the Project

```bash
mvn clean compile
```

## Run Tests

```bash
mvn clean test
```

The JUnit 5 test suite checks reference values, edge cases, overlapping matches, LPS arrays, and randomized inputs.

## Run Experiments

Run the `main` method in:

```text
com.daa.experiment.ExperimentRunner
```

In IntelliJ IDEA, open `ExperimentRunner.java` and click the green Run icon next to `main`.

The experiment runner:
- Generates reproducible inputs using seed 42.
- Measures each algorithm five times.
- Calculates median execution time.
- Records operation/comparison counters.
- Saves measurements to `results/results.csv`.

## Generate Plots

After running the experiments, execute:

```bash
python plot_results.py
```

The three plots are saved in `results/plots/`.

## Experimental Report

See [REPORT.md](REPORT.md) for the experiment design, performance measurements, plots, complexity analysis, and conclusions.

## AI Acknowledgement

AI was used as an assistance tool during project development for guidance, suggestions, support and documentation assistance.

