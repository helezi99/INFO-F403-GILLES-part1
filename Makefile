# Jflex jar path
jar = ./more/jflex-1.9.1/lib/jflex-full-1.9.1.jar

# Path & files names
path_src = src/
path_test = test/
path_doc = doc/javadoc/
path_dist = dist/
path_more = more/

lexer = LexicalAnalyzer
symbol = Symbol
lexical_unit = LexicalUnit
main = Main
manifest = manifest

path_lexer = $(path_src)$(lexer)
path_symbol = $(path_src)$(symbol)
path_lexical_unit = $(path_src)$(lexical_unit)
path_main = $(path_src)$(main)

jar_main = $(path_dist)part1.jar

# Files
all_java_src = $(path_src)*.java

# Info for the .jar
path_manifest = $(path_more)$(manifest).txt

flex = $(path_lexer).flex
input = $(path_test)Euclid.gls

all: jar

compile: 
	java -jar $(jar) $(flex)
	javac $(all_java_src)

doc: compile
	javadoc -d $(path_doc) $(all_java_src)

jar: compile
# Create the manifest file
	echo "Main-Class: $(main)" > $(path_manifest)
# Create the .jar to run the program
	jar cfm $(jar_main) $(path_manifest) -C $(path_src) .

run_jar: jar
	java -jar $(jar_main) $(input)

run: compile
	java -cp $(path_src) $(main) $(input)

test: compile
# Run the main program for all the test files on the test folder
	for file in $(path_test)*.gls; do \
		echo $$file; \
		java -cp $(path_src) $(main) $$file; \
		echo; \
	done

clean:
# The 1st * is for the .java~ sometimes created
	rm -f -r $(path_lexer).java* $(path_src)*.class $(path_doc)* $(jar_main) $(path_manifest) $(jar_main)