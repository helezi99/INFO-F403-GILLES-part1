# Tools
JAVAC = javac
JFLEX = jflex
JAR = jar
JAVADOC = javadoc

# Source and target files
SRC_DIR = src
TEST_DIR = test/test1
SOURCES = $(SRC_DIR)/LexicalAnalyzer.java $(SRC_DIR)/Main.java $(SRC_DIR)/Symbol.java $(SRC_DIR)/LexicalUnit.java $(SRC_DIR)/Parser.java $(SRC_DIR)/ParseTree.java
TEST_FILES := $(wildcard $(TEST_DIR)/*.gls)
INPUT_FILE = test/Euclid.gls
OUTPUT_JAR = dist/part1.jar
MAIN_CLASS = Main
DOC_DIR = doc/javadoc

# Default target (compile everything)
all: $(SRC_DIR)/LexicalAnalyzer.java $(OUTPUT_JAR)

# Generate the LexicalAnalyzer.java file from the JFlex file
$(SRC_DIR)/LexicalAnalyzer.java: $(SRC_DIR)/LexicalAnalyzer.flex
	$(JFLEX) $(SRC_DIR)/LexicalAnalyzer.flex

# Compile the Java files
$(SRC_DIR)/Main.class: $(SOURCES)
	$(JAVAC) -Xlint $(SOURCES)

# Create the dist directory if needed and generate the JAR file in this directory
$(OUTPUT_JAR): $(SRC_DIR)/Main.class
	$(JAR) cfe $(OUTPUT_JAR) $(MAIN_CLASS) -C $(SRC_DIR) .

# Compile files before running tests
compile: $(SRC_DIR)/Main.class

# Run tests on all .gls files in the test directory
tests: compile
	for test_file in $(TEST_FILES); do \
		echo "\nTesting $$test_file\n"; \
		java -cp $(SRC_DIR) $(MAIN_CLASS) "$$test_file"; \
	done
	echo "Done testing"



# Run the program from the JAR file with a .gls file as input
run: $(OUTPUT_JAR)
	java -jar $(OUTPUT_JAR) $(INPUT_FILE)

# Generate the Javadoc
javadoc: $(SOURCES)
	$(JAVADOC) -d $(DOC_DIR) $(SOURCES)

# Clean up generated files (.class, .java, and the JAR file)
clean:
	rm -f $(SRC_DIR)/*.class $(SRC_DIR)/LexicalAnalyzer.java
	echo "Done cleaning"

# Clean up generated Javadoc
javadoclean:
	rm -rf $(DOC_DIR)
	echo "Done cleaning Javadoc"
