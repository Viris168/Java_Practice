# Student Scores Analyzer

A mini Java project demonstrating the **Stream API** through a practical example — analyzing a list of students' scores across subjects and grades.

Covers grouping, sorting, top-N selection, partitioning, and summary statistics using `java.util.stream`.

## Features

This project walks through 10 common Stream operations:

| # | Operation | Stream API Used |
|---|-----------|------------------|
| 1 | Sort all students by score (descending) | `sorted()` |
| 2 | Group students by grade | `Collectors.groupingBy()` |
| 3 | Group by subject and compute average score | `groupingBy()` + `Collectors.averagingDouble()` |
| 4 | Top 3 students overall | `sorted()` + `limit()` |
| 5 | Top 2 students per subject | nested `groupingBy()` + `sorted()` + `limit()` |
| 6 | Highest scorer per grade | `groupingBy()` + `Collectors.maxBy()` |
| 7 | Count of students per subject | `groupingBy()` + `Collectors.counting()` |
| 8 | Pass/Fail split (score > 80) | `Collectors.partitioningBy()` |
| 9 | Score statistics (min, max, avg, sum) | `mapToDouble()` + `summaryStatistics()` |
| 10 | Alphabetically sorted, comma-joined names | `map()` + `sorted()` + `Collectors.joining()` |

## Project Structure

```
Java_Practice/
└── week_2/
    └── student_scoresAnalyzer.java
```

> The class uses `package Java_Practice.week_2;`, so it must be compiled/run from a source root that contains the `Java_Practice/week_2/` folder structure above.

## Requirements

- JDK 17 or later (uses `List.of()`, `.toList()`, and modern Stream collectors)

## How to Run

From the project root (the folder **above** `Java_Practice/`):

```bash
# Compile
javac Java_Practice/week_2/student_scoresAnalyzer.java -d out

# Run
java -cp out Java_Practice.week_2.student_scoresAnalyzer
```

## Sample Output

```
Student{name='Ivan', score=99.0}
Student{name='Farah', score=95.0}
Student{name='Alice', score=92.0}
...

Subject: English
Farah - 95.0
Kevin - 78.0

Grade: A | Top Student: Ivan (99.0)
Grade: B | Top Student: Ethan (81.0)
Grade: C | Top Student: Julia (65.0)

Count: 12
Sum: 942.0
Average: 78.5
Min: 55.0
Max: 99.0
```

## Concepts Practiced

- `Stream` creation and terminal operations (`forEach`, `toList`, `collect`)
- `Comparator.comparingDouble(...).reversed()`
- `Collectors.groupingBy` (single-level and nested/downstream collectors)
- `Collectors.collectingAndThen` for post-processing grouped results
- `Optional` handling with `maxBy`
- `partitioningBy` for boolean-based splitting
- `DoubleSummaryStatistics` for aggregate metrics

## Possible Extensions

- Load student data from a CSV file instead of a hardcoded list
- Add percentile/ranking calculations per subject
- Export results to a report (e.g. CSV or JSON) using Streams
- Add unit tests (JUnit) for each analysis method

## License

Free to use for learning and practice.
