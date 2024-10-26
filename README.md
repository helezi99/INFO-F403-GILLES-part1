# GILLES Compiler Project
**INFO-F403 - Language Theory and Compiling (2024/25) - ULB**  
Work: Herma Elezi

![ULB Logo]([https://camo.githubusercontent.com/656087fe3b1930601b1e17c7bf8c39417c9293e5fe7b89761786fc00b19021d0/68747470733a2f2f63646e2e6a7364656c6976722e6e65742f67682f617572656f6f6d732d756c622f6c6f676f2f66696c65732f756c622d6c6f676f2d626c75652d7365616c2d756c622e737667](https://actus.ulb.be/medias/photo/logo-universite-libre-bruxelles_1661952138925-png?ID_FICHE=19524))

---

## Background

In the world of programming languages, defining and recognizing valid code syntax is fundamental. Lexical analyzers, also known as scanners, play a vital role by transforming source code into tokens for further processing by parsers and compilers. The Genial Imperative Language for Learning and the Enlightenment of Students (GILLES) was crafted with a similar goal—to provide students a hands-on experience with compiler theory.

Our project is dedicated to developing a **lexical analyzer for GILLES**, designed using **JFlex**, to recognize keywords, variables, numbers, and other language tokens. Through this process, we delve into core concepts of **language theory, regex matching,** and **token handling**.

---

## Motivation and Objective of the Project

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
