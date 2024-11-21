# GILLES Compiler Project Part-1
**INFO-F403 - Language Theory and Compiling (2024/25) - ULB**  
Work: Herma Elezi

<div align="center">
    <img src="https://actus.ulb.be/medias/photo/logo-universite-libre-bruxelles_1661952138925-png?ID_FICHE=19524" alt="ULB Logo" width="300"/>
</div>

## Introduction

In the world of programming languages, defining and recognizing valid code syntax is fundamental. Lexical analyzers, also known as scanners, play a vital role by transforming source code into tokens for further processing by parsers and compilers. The Genial Imperative Language for Learning and the Enlightenment of Students (GILLES) was crafted with a similar goal—to provide students a hands-on experience with compiler theory.

Our project is dedicated to developing a **lexical analyzer for GILLES**, designed using **JFlex**, to recognize keywords, variables, numbers, and other language tokens. Through this process, we delve into core concepts of **language theory, regex matching,** and **token handling**.

---

## Project Overview

This project implements a uniprocessor scheduling simulator, which supports three scheduling algorithms:
- Deadline Monotonic (DM)
- Earliest Deadline First (EDF)
- Round Robin (RR)

The simulator is designed to read tasks with specified computation times and deadlines, and then execute them according to the selected scheduling algorithm. The goal is to compare the performance of these algorithms in terms of task completion times and processor utilization.

---

## INFO-F403 Project Part 1: Build and Run Instructions

This project uses Python to simulate the scheduling algorithms. Below are the instructions for setting up and running the simulator, as well as the structure of the project directory.

### Prerequisites

Before running the project, ensure you have the following tools installed:
- **Python** (3.x or higher)

You can install Python from [here](https://www.python.org/downloads/).

Additionally, you may need the following Python libraries:
- `matplotlib` (for visualizing results, if applicable)
- `numpy` (for numerical operations)

You can install them using `pip`:
```sh
pip install matplotlib numpy
```

### Project Setup

1. **Clone the repository:**

   ```sh
   git clone <your-repository-url>
   cd <project-directory>
   ```

2. **Install required dependencies:**

   If there is a `requirements.txt` file in the repository, install the required Python packages with:
   ```sh
   pip install -r requirements.txt
   ```

3. **Directory Structure:**

   The project follows this directory structure:

   ```
   ├── src/                 # Source files for the simulator
   │   ├── task.py          # Task class definition
   │   ├── scheduler.py     # Scheduling algorithm implementations
   │   └── simulator.py     # Main simulation logic
   ├── test/                # Test files
   │   ├── test_cases.py    # Unit tests
   ├── results/             # Output folder for simulation results
   ├── README.md            # Project documentation (this file)
   ├── requirements.txt     # Required Python libraries
   └── Makefile             # Makefile for managing tasks
   ```

### Makefile Targets

#### Compile All

To compile and set up the environment (if any pre-compilation is needed), run:
```sh
make all
```

#### Run the Simulator

To execute the simulator with a task set and a specified algorithm, use the following command:
```sh
make run
```

#### Run Tests

To run the unit tests for the simulator, use:
```sh
make tests
```

#### Generate Javadoc

To generate documentation for the project (if any is required), run:
```sh
make javadoc
```

#### Clean Up

To clean up generated files (e.g., `.pyc`, `.log`, results), use:
```sh
make clean
```

For cleaning generated Javadoc files:
```sh
make javadoclean
```

### Example Usage

To compile the project, run tests, and then clean up, you can use the following commands:

```sh
make all
make tests
make clean
```

To generate Javadoc and then clean it up, use:

```sh
make javadoc
make javadoclean
```

For more details, refer to the Makefile in the project directory.

---

## INFO-F403 Project Part 2: Algorithm Details and Execution

### Algorithm 1: Deadline Monotonic (DM)

The Deadline Monotonic algorithm assigns priorities to tasks based on their deadlines, with tasks having earlier deadlines given higher priorities. It is a fixed-priority preemptive algorithm.

### Algorithm 2: Earliest Deadline First (EDF)

The Earliest Deadline First algorithm assigns priorities dynamically based on the task's deadline, with the task having the earliest deadline being given the highest priority.

### Algorithm 3: Round Robin (RR)

The Round Robin algorithm divides CPU time into fixed time slices and assigns them cyclically to tasks. If a task doesn't finish within its time slice, it is preempted and moved to the back of the queue.

### Simulator Design

- **Task Representation**: Each task is represented by a class `Task`, which holds information about the task's computation time, deadline, and remaining time.

- **Scheduler**: The `Scheduler` class implements the three scheduling algorithms. It manages the simulation, including task execution and performance tracking.

- **Test Cases**: Several test cases are included to evaluate the performance of each scheduling algorithm.

### Example of Task Set

| Task Name | Computation Time | Deadline |
|-----------|------------------|----------|
| T1        | 4                | 8        |
| T2        | 2                | 6        |
| T3        | 3                | 9        |
| T4        | 1                | 5        |

### Results and Discussion

The simulator tracks task completion times and processor utilization for each algorithm. The results show how each algorithm handles a task set and the efficiency of task completion.

---

## Contributing

If you would like to contribute to this project, feel free to fork the repository, make improvements or fixes, and create a pull request. Please ensure that your contributions are well-documented and tested.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Makefile Overview

Below is the breakdown of the targets in the Makefile for managing the build and execution of the project:

### Prerequisites

Ensure you have the following tools installed:
- `javac` (Java Compiler)
- `jflex` (JFlex Scanner Generator)
- `jar` (Java Archive Tool)
- `javadoc` (Java Documentation Tool)

### Makefile Targets

#### Compile Everything

To compile all the source files and generate the necessary artifacts, run:
```sh
make all
```

#### Create JAR File

To create the JAR file, run:
```sh
make $(OUTPUT_JAR)
```

#### Run Tests

To compile the files and run tests on all `.gls` files in the test directory, run:
```sh
make tests
```

#### Run the Program

To run the program from the JAR file with a specified `.gls` input file, run:
```sh
make run
```

#### Generate Javadoc

To generate the Javadoc for the project, run:
```sh
make javadoc
```

#### Clean Up

To clean up generated files (`.class`, `.java`, and the JAR file), run:
```sh
make clean
```

To clean up generated Javadoc, run:
```sh
make javadoclean
```

### Directory Structure

- `src`: Contains the source files.
- `test/test1`: Contains the test files.
- `dist`: Directory where the JAR file will be generated.
- `doc/javadoc`: Directory where the Javadoc will be generated.

### Example Usage

To compile the project, run tests, and then clean up, you can use the following commands:
```sh
make all
make tests
make clean
```

To generate the Javadoc and then clean it up, use:
```sh
make javadoc
make javadoclean
```
