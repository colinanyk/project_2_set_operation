# project_2_set_operation

## Project Description

Implements ordinary sets, multisets, and fuzzy sets over finite integer universes. Fuzzy-set membership degrees are finite values from `0.0` through `1.0`; missing entries have degree `0.0`.

## Project Structure

- `src/Main.java` — runnable demonstration with expected and actual results
- `src/sets/Set.java` — ordinary boolean-membership set
- `src/sets/OrdinarySet.java` — universe-aware ordinary set
- `src/sets/MultiSet.java` — Guava-backed multiset
- `src/sets/FuzzySet.java` — immutable fuzzy set
- `src/tests/sets/` — JUnit 5 tests
- `src/lib/` — checked-in runtime and test dependencies

## Running the Project

The project uses `src` as its source root and does not use Maven. On macOS with a JDK installed:

```zsh
JAVA_HOME="$(/usr/libexec/java_home -v 24)"
COMPILE_CP="src/lib/junit-platform-console-standalone-1.10.2.jar:src/lib/guava-33.7.2-jre.jar:src/lib/algs4.jar"
RUNTIME_CP="target/all-test-classes:src/lib/guava-33.7.2-jre.jar:src/lib/algs4.jar"

rm -rf target/all-test-classes
mkdir -p target/all-test-classes
"$JAVA_HOME/bin/javac" --release 21 -cp "$COMPILE_CP" -d target/all-test-classes $(find src -name '*.java' -print)
"$JAVA_HOME/bin/java" -jar src/lib/junit-platform-console-standalone-1.10.2.jar execute \
  --class-path "$RUNTIME_CP" --scan-class-path
"$JAVA_HOME/bin/java" -cp "$RUNTIME_CP" Main
```

Use the corresponding installed JDK path on another operating system.

## Documentation

- [Pseudocode](docs/pseudocode.md)
- [Project plan](docs/project2-plan.md)
