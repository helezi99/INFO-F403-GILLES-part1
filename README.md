# GILLES Compiler Project Part-1&2
**INFO-F403 - Language Theory and Compiling (2024/25) - ULB**  
Work: Herma Elezi

<div align="center">
    <img src="https://actus.ulb.be/medias/photo/logo-universite-libre-bruxelles_1661952138925-png?ID_FICHE=19524" alt="ULB Logo" width="300"/>
</div>

## Introduction

In the world of programming languages, defining and recognizing valid code syntax is fundamental. Lexical analyzers, also known as scanners, play a vital role by transforming source code into tokens for further processing by parsers and compilers. The Genial Imperative Language for Learning and the Enlightenment of Students (GILLES) was crafted with a similar goal—to provide students a hands-on experience with compiler theory.

Our project is dedicated to developing a **lexical analyzer for GILLES**, designed using **JFlex**, to recognize keywords, variables, numbers, and other language tokens. Through this process, we delve into core concepts of **language theory, regex matching,** and **token handling**.

---

Here's a README.md file tailored for both Part 1 and Part 2 of the project:

---

# GILLES Compiler Project - Part 1 and Part 2

This project implements a compiler for the **Genial Imperative Language for Learning and the Enlightenment of Students (GILLES)**. It includes both a lexical analyzer (Part 1) and a parser (Part 2) with various features for building, testing, and executing the project.

## Prerequisites

Ensure you have the following tools installed:
- `javac` (Java Compiler)
- `jflex` (JFlex Scanner Generator)
- `jar` (Java Archive Tool)
- `javadoc` (Java Documentation Tool)

---

## Part 1: Lexical Analyzer

### Makefile Targets

#### Compile Everything
To compile all the source files for the lexical analyzer and generate the necessary artifacts, run:
```bash
make all
```

#### Create JAR File
To create the JAR file for the lexical analyzer, run:
```bash
make part1.jar
```

#### Run Tests
To compile the files and run tests on all `.gls` files in the test directory, run:
```bash
make tests
```

#### Run the Program
To run the program from the JAR file with a specified `.gls` input file, run:
```bash
make run
```

#### Generate Javadoc
To generate the Javadoc for Part 1, run:
```bash
make javadoc
```

#### Clean Up
To clean up generated files (`.class`, `.java`, and the JAR file), run:
```bash
make clean
```

To clean up generated Javadoc, run:
```bash
make javadoclean
```

---

## Part 2: Parser

### Makefile Targets

#### Compile Everything
To compile all the source files for the parser and generate the necessary artifacts, run:
```bash
make all
```

#### Create JAR File
To create the JAR file for the parser, run:
```bash
make part2.jar
```

#### Run the Program
To parse a GILLES program and print the leftmost derivation to standard output, run:
```bash
java -jar part2.jar <sourceFile.gls>
```

To generate the parse tree as a LaTeX file, run:
```bash
java -jar part2.jar -wt <outputFile.tex> <sourceFile.gls>
```

#### Run Tests
To run tests on various `.gls` files, use:
```bash
make tests
```

#### Generate Javadoc
To generate the Javadoc for Part 2, run:
```bash
make javadoc
```

#### Clean Up
To clean up generated files (`.class`, `.java`, and the JAR file), run:
```bash
make clean
```

To clean up generated Javadoc, run:
```bash
make javadoclean
```

---

## Directory Structure

- **`src/`**: Contains the source files for both the lexical analyzer and parser.
- **`test/`**: Contains `.gls` test files to validate the functionality of the compiler.
- **`dist/`**: Directory where the JAR files (`part1.jar` and `part2.jar`) are generated.
- **`doc/javadoc/`**: Directory where the Javadoc is generated.

---

## Example Usage

### Part 1 Example
To compile the lexical analyzer, run tests, and clean up:
```bash
make all
make tests
make clean
```

To generate the Javadoc and clean it up:
```bash
make javadoc
make javadoclean
```

### Part 2 Example
To compile the parser, test it, and generate a parse tree:
```bash
make all
java -jar part2.jar -wt output.tex sourceFile.gls
```

To clean up all generated files:
```bash
make clean
```

---
