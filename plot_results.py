import csv
from pathlib import Path
from collections import defaultdict

import matplotlib.pyplot as plt

CSV_FILE = Path("results/results.csv")
PLOT_DIR = Path("results/plots")
PLOT_DIR.mkdir(parents=True, exist_ok=True)

# Read only median measurements.
data = []

with CSV_FILE.open("r", newline="", encoding="utf-8") as file:
    reader = csv.DictReader(file)

    for row in reader:
        if row["run"] == "median":
            data.append({
                "algorithm": row["algorithm"],
                "input": row["input"],
                "n": int(row["n"]),
                "time_ms": float(row["time_ms"]),
                "counter": int(row["counter"])
            })


def draw_plot(title, xlabel, ylabel, groups, output, log_y=False):
    fig, ax = plt.subplots(figsize=(10, 6))

    for label, points in groups.items():
        points.sort(key=lambda point: point[0])
        x = [point[0] for point in points]
        y = [point[1] for point in points]

        ax.plot(x, y, marker="o", label=label)

    ax.set_title(title)
    ax.set_xlabel(xlabel)
    ax.set_ylabel(ylabel)
    ax.set_xscale("log")

    if log_y:
        ax.set_yscale("log")

    ax.grid(True, which="both", linestyle="--", alpha=0.5)
    ax.legend()
    fig.tight_layout()
    fig.savefig(PLOT_DIR / output, dpi=300)
    plt.close(fig)


# Plot 1: Edit Distance execution time.
edit_data = defaultdict(list)

for row in data:
    if "EditDistance" in row["algorithm"]:
        label = f'{row["algorithm"]} - {row["input"]}'
        edit_data[label].append((row["n"], row["time_ms"]))

draw_plot(
    "Edit Distance: Median Execution Time",
    "Input size (characters)",
    "Median execution time (ms)",
    edit_data,
    "edit_distance_time.png",
    log_y=True
)


# Plot 2: String Search execution time.
search_time_data = defaultdict(list)

for row in data:
    if row["algorithm"] in ("NaiveSearch", "KMPSearch"):
        label = f'{row["algorithm"]} - {row["input"]}'
        search_time_data[label].append((row["n"], row["time_ms"]))

draw_plot(
    "String Search: Median Execution Time",
    "Text size (characters)",
    "Median execution time (ms)",
    search_time_data,
    "string_search_time.png",
    log_y=True
)


# Plot 3: String Search comparison counts.
search_counter_data = defaultdict(list)

for row in data:
    if row["algorithm"] in ("NaiveSearch", "KMPSearch"):
        label = f'{row["algorithm"]} - {row["input"]}'
        search_counter_data[label].append((row["n"], row["counter"]))

draw_plot(
    "String Search: Character Comparisons",
    "Text size (characters)",
    "Number of comparisons",
    search_counter_data,
    "string_search_comparisons.png",
    log_y=True
)

print("All three plots were generated successfully.")
