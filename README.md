# DNC_Algorithms

## Project Overview

Implementation and testing of four classic Divide and Conquer (D&C) algorithms in Java, built with Maven. The project focuses on measuring performance metrics (time, comparisons, recursion depth) for comparative analysis.

## Implemented Algorithms

1.  **Merge Sort:** O(N log N) stable sorting.
2.  **Quick Sort:** O(N log N) average case sorting.
3.  **Deterministic Select:** O(N) worst-case time complexity for finding the k-th element.
4.  **Closest Pair of Points:** O(N log N) geometric algorithm for finding the minimum Euclidean distance.

## Directory Structure

Standard Maven structure for source code and tests:

.
├── src/
│   ├── main/java/dnc/             # Algorithm source files (.java)
│   └── test/java/dnc/              # Unit tests and performance utilities
├── pom.xml                         # Maven configuration
└── README.md


## Metrics Collection

Performance is tracked using a dedicated `Metrics` utility within the tests. Recorded metrics include:

* **Execution Time (ms)**
* **Comparison Operations**
* **Max Recursion Depth**

## Execution

The project requires **JDK 8+** and **Maven**. To run all algorithms and generate performance logs:

Execute the test suite in the `src/test/java/dnc/` package using the IDE (Run 'Tests in
