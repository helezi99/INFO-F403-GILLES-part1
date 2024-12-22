# Tools
JAVAC = javac
JFLEX = jflex
JAR = jar

# Directories
SRC_DIR = src
TEST_DIR = test/test3
DIST_DIR = dist

# Source and Target Files
SOURCES = $(SRC_DIR)/LexicalAnalyzer.java $(SRC_DIR)/Main.java $(SRC_DIR)/Symbol.java $(SRC_DIR)/LexicalUnit.java $(SRC_DIR)/Parser.java $(SRC_DIR)/ParseTree.java $(SRC_DIR)/AST_tree.java $(SRC_DIR)/Compiler.java
TEST_FILES = $(wildcard $(TEST_DIR)/gls_test/*.gls)
OUTPUT_JAR = $(DIST_DIR)/part3.jar
MAIN_CLASS = Main

# Default Target
all: $(SRC_DIR)/LexicalAnalyzer.java $(OUTPUT_JAR)

# Generate the LexicalAnalyzer.java file from the JFlex file
$(SRC_DIR)/LexicalAnalyzer.java: $(SRC_DIR)/LexicalAnalyzer.flex
	@echo "Generating LexicalAnalyzer.java from JFlex..."
	$(JFLEX) $<

# Compile Java files and create the JAR file
$(OUTPUT_JAR): $(SOURCES)
	@echo "Compiling Java files and creating JAR..."
	$(JAVAC) -d $(DIST_DIR) $(SOURCES)
	$(JAR) cfe $(OUTPUT_JAR) $(MAIN_CLASS) -C $(DIST_DIR) .

# Run the compiler with a specific test file
run:
	@echo "Running compiler on default test file..."
	java -jar $(OUTPUT_JAR) $(TEST_DIR)/gls_test/if_test.gls

# Run all tests in the test directory
test:
	@echo "Running all test cases in $(TEST_DIR)..."
	for file in $(TEST_FILES); do \
		echo "\nTesting $$file..."; \
		java -jar $(OUTPUT_JAR) $$file; \
	done
	@echo "All tests completed!"

# Clean up generated files
clean:
	@echo "Cleaning up generated files..."
	rm -f $(SRC_DIR)/*.class $(DIST_DIR)/*.jar $(SRC_DIR)/LexicalAnalyzer.java
	@echo "Clean-up completed!"

# Display help information
help:
	@echo "Available targets:"
	@echo "  all       - Build the project (generate lexer and compile)"
	@echo "  run       - Run the compiler on a default test file"
	@echo "  test      - Run all tests in the test directory"
	@echo "  clean     - Remove generated files"
