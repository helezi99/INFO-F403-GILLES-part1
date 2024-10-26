# GILLES Compiler Project Part-1
**INFO-F403 - Language Theory and Compiling (2024/25) - ULB**  
Work: Herma Elezi

![ULB Logo](https://camo.githubusercontent.com/347eaad2540c5565afffac408a52ca9cbf077a574f201fe970c131b6044ea177/68747470733a2f2f61637475732e756c622e62652f6d65646961732f70686f746f2f6c6f676f2d756e69766572736974652d6c696272652d62727578656c6c65735f313636313935323133383932352d706e673f49445f46494348453d3139353234)

---

## Introduction

In the world of programming languages, defining and recognizing valid code syntax is fundamental. Lexical analyzers, also known as scanners, play a vital role by transforming source code into tokens for further processing by parsers and compilers. The Genial Imperative Language for Learning and the Enlightenment of Students (GILLES) was crafted with a similar goal—to provide students a hands-on experience with compiler theory.

Our project is dedicated to developing a **lexical analyzer for GILLES**, designed using **JFlex**, to recognize keywords, variables, numbers, and other language tokens. Through this process, we delve into core concepts of **language theory, regex matching,** and **token handling**.

---

## Objective of the Project

The objective of this project is to implement a **reliable lexical analyzer** capable of:
1. Recognizing essential GILLES language tokens.
2. Managing a **symbol table** for variable tracking.
3. Handling **comments** and **errors** gracefully.
4. Providing **useful output** for further language processing stages.

Ultimately, our goal is to demonstrate the practical steps of language theory by translating the defined syntax of GILLES into a functional tool, preparing for further parsing and compilation phases. Additionally, our project emphasizes clear documentation and test-driven design.

---

## Demo: A Step into GILLES Language Lexing

Welcome to our demonstration of the GILLES lexical analyzer, an educational project designed to highlight the fundamental tasks of a lexer in compiler development.

Here, the **Main.java** file reads and tokenizes a source file written in GILLES syntax, sequentially recognizing each unit and building a symbol table for variables. Sample GILLES programs include:
- **Euclidean Algorithm Calculation**: a simple program that calculates the greatest common divisor.
- **Arithmetic and Conditional Statements**: examples showcasing arithmetic operations, conditional statements, and while loops.

### How the Lexical Analyzer Works:
1. **Token Recognition**: When run, the analyzer processes GILLES code, recognizing tokens such as keywords (e.g., `LET`, `IF`), variables, numbers, and symbols.
2. **Symbol Table Management**: The program maintains a list of variables encountered, marking their first line of appearance for reference.
3. **Error Handling**: The analyzer detects unknown symbols and manages unclosed comments gracefully.

### Sample Output:
For a GILLES file with the content:
```plaintext
LET Euclidean_algorithm BE
IN(a):
IN(b):
WHILE {0 < b} REPEAT
...
END
