# Divide and Conquer Algorithms

**Assignment 1 — Design and Analysis of Algorithms** **Student:** Altynay Bolatova  

---

## 🎯 Learning Goals

- Implement classic divide-and-conquer algorithms with safe recursion patterns.
- Analyse running-time recurrences using Master Theorem and Akra–Bazzi intuition.
- Collect metrics (time, recursion depth, comparisons/allocations) and validate with experiments.
- Present results in a clear report with clean Git history.

---

## 📌 Implemented Algorithms

### 1. MergeSort ($\Theta(n \log n)$, Master Theorem Case 2)
- Uses divide-and-conquer with a reusable buffer.
- Small-$n$ cutoff switches to insertion sort for efficiency.
- Recurrence: $$T(n) = 2T(n/2) + \Theta(n) \to \Theta(n \log n)$$ (Master Case 2)

### 2. QuickSort (Average $\Theta(n \log n)$)
- Randomized pivot selection for robustness.
- Always recurses into the smaller partition, iterates over the larger one $\to$ ensures recursion depth $O(\log n)$.
- Recurrence (average case): $$T(n) = T(n/2) + T(n/2) + \Theta(n) \to \Theta(n \log n)$$
- Worst case $O(n^2)$, but randomized pivot avoids this with high probability.

### 3. Deterministic Select (Median-of-Medians, $\Theta(n)$)
- Groups elements by 5, computes median of medians as pivot.
- Recurse only into the side that contains the desired element.
- Always recurses into the smaller partition to keep depth bounded.
- Recurrence: $$T(n) = T(n/5) + T(7n/10) + \Theta(n) \to \Theta(n)$$ (Akra–Bazzi intuition)

### 4. Closest Pair of Points in 2D ($\Theta(n \log n)$)
- Points sorted by x-coordinate.
- Recursively split into left and right halves.
- In the “strip”, only $\sim 7–8$ neighbors per point are checked (sorted by y).
- Recurrence: $$T(n) = 2T(n/2) + \Theta(n) \to \Theta(n \log n)$$

---

## ⚙️ Architecture Notes

- **Recursion depth control:**
  - QuickSort recurses only into the smaller side $\to$ stack depth $\le 2 \cdot \log_2 n$.
  - Select recurses only into one side.
- **Memory usage:**
  - MergeSort reuses a single buffer to avoid repeated allocations.
- **Metrics collected:**
  - Execution time, recursion depth, number of comparisons, number of allocations.

---

## 📊 Experimental Results

### Time vs Input Size ($n$)
*(Insert plot here)* - MergeSort and QuickSort scale as $n \log n$.
- Select is linear and outperforms sorting when $k$ is small.
- Closest Pair matches theory with $\Theta(n \log n)$.

### Recursion Depth vs Input Size ($n$)
*(Insert plot here)* - QuickSort depth $\approx O(\log n)$.
- MergeSort depth $= \log_2 n$.
- Select depth linear in $\log n$.

### Discussion of Constant Factors
- QuickSort often faster than MergeSort in practice due to better cache locality.
- MergeSort more stable on adversarial inputs.
- Garbage collector (GC) and buffer reuse affect timings.

---

## ✅ Summary

- Theoretical analysis and experimental results are consistent.
- Small constant factors (cache effects, buffer reuse, randomization) explain practical performance differences.
- Git workflow followed: feature branches, structured commits, final release.

---

## 🛠️ GitHub Workflow

Branches:  
- `main` — stable release (v1.0).  
- `feature/mergesort`, `feature/quicksort`, `feature/select`, `feature/closest`, `feature/metrics`.

Commit storyline includes:  
- `init: maven, junit5, readme`  
- `feat(metrics): counters, depth tracker, CSV writer`  
- `feat(mergesort): baseline + reuse buffer + cutoff + tests`  
- `feat(quicksort): smaller-first recursion, randomized pivot + tests`  
- `feat(select): deterministic select (MoM5) + tests`  
- `feat(closest): divide-and-conquer implementation + tests`  
- `docs(report): master cases & AB intuition, initial plots`  
- `release: v1.0`

---

## 🧪 Testing

- Sorting correctness verified on random and adversarial arrays.
- QuickSort depth $\le 2 \cdot \text{floor}(\log_2 n)$.
- Select matches `Arrays.sort(a)[k]` in 100 trials.
- Closest Pair validated against $O(n^2)$ algorithm for $n \le 2000$.
