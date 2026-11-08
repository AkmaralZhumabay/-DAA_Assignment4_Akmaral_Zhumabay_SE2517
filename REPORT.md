# Experiment Design Card

## 1. Research Questions

1. How does the recursive Edit Distance algorithm scale compared with the dynamic programming implementations?
2. How much memory and execution time can the two-row Edit Distance implementation save compared with the full-table implementation?
3. How do Naive Search and KMP differ in the number of character comparisons and execution time?
4. How do the algorithms behave on random inputs compared with challenging inputs?

## 2. Input Sizes

| Algorithm | Input sizes |
|---|---|
| Recursive Edit Distance | 2, 4, 6, 8, 10 |
| Table Edit Distance | 100, 250, 500, 1000 |
| Two-Rows Edit Distance | 100, 250, 500, 1000 |
| Naive Search | 1000, 5000, 10000, 20000 |
| KMP Search | 1000, 5000, 10000, 20000 |

The largest input is at least four times the smallest input in each size range.

## 3. Input Types

**Edit Distance**
- Random: strings generated from a fixed alphabet using a fixed random seed.
- Challenging: strings containing different repeated characters, such as `AAAA...` and `BBBB...`, to trigger mismatches.

**String Search**
- Random: random text and a randomly generated pattern.
- Challenging: text consisting of repeated `A` characters and a pattern consisting of repeated `A` characters ending in `B`. This creates many partial matches for Naive Search.

## 4. Metrics

- Median execution time in milliseconds, measured over 5 runs using `System.nanoTime()`.
- Number of recursive calls or DP cell calculations for Edit Distance.
- Number of character comparisons for String Search.
- Memory complexity based on the algorithm implementation.

## 5. Predictions

- Recursive Edit Distance will grow exponentially and become significantly slower as input length increases.
- Table Edit Distance will have O(n × m) time and O(n × m) memory complexity.
- Two-Rows Edit Distance will have O(n × m) time and O(m) auxiliary memory complexity.
- Naive Search will perform many comparisons on challenging inputs.
- KMP will generally perform fewer comparisons on challenging inputs because it reuses information from previous matches.

## 6. Planned Plots

1. Edit Distance execution time versus input size.
2. String Search execution time versus input size.
3. String Search comparison counts versus input size.

At least one plot will use a logarithmic axis if needed to make differences easier to compare.

## 7. Reproducibility

All random inputs will be generated using a fixed seed (`42`). Each measurement will be repeated five times, and the median execution time will be recorded. Each run will be stored as a separate row in `results/results.csv`.