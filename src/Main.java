import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;

/**
 * GILLES Compiler Project - Part 1
 * 
 * This class is part of the lexical analyzer for the GILLES language, developed
 * for the Info-F403 course (2024-2025). The analyzer tokenizes program elements
 * such as keywords, variable names, numbers, and comments, and manages a symbol table.
 * 
 * Project Supervisor: [Instructor's Name, if applicable]
 * 
 * @version 1.0
 * @author 
 * Herma ELEZI
 * MA-Computer science
 * Université Libre de Bruxelles
 * 
 * @date October 2024
 */

public class Main {
    /**
     * This is the main method of the program. It reads the source file and
     * analyzes it using the lexical analyzer. It then prints the lexical units
     * recognized by the analyzer (and the according symbol table).
     * 
     * @param args[0] : the path to the source file to analyze
     * @throws IOException if the file is not found
     * @throws FileNotFoundException if the file is not found
     *
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {
        if (args.length != 1) {
            System.err.println("Usage: java -cp src src/Main.java <sourceFile.gls>");
            return;
        }
        
        FileReader reader;
        
        try {
            reader = new FileReader(args[0]); // Open the source file given as argument
            
        } catch (FileNotFoundException exception)  {
            System.err.println("ERROR : File " + args[0] + " not Found.");
            return;
        }

        // Create the lexical analyzer using JFlex
        LexicalAnalyzer lexer = new LexicalAnalyzer(reader);


        Symbol symbol;
        TreeMap<String, Symbol> symbolTable;
        try {
            symbol = lexer.nextToken();
            symbolTable = new TreeMap<String, Symbol>(); // Create the symbol table
            
            while (!symbol.getType().equals(LexicalUnit.EOS)) { // While the end of the file is not reached, analyze the file and print the lexical units
                System.out.println(symbol.toString());

                // Add the variable to the symbol table
                if(symbol.getType().equals(LexicalUnit.VARNAME)){
                    if(!symbolTable.containsKey(symbol.getValue())) {
                        symbolTable.put(symbol.getValue().toString(), symbol);
                    }
                }
                
                symbol = lexer.nextToken();
            }
        } catch (PatternSyntaxException exception) {
            System.err.println("ERROR : " + exception);
            return;
        }

        System.out.println("\nVariables");
        // Content of the symbol table with the variables and the number of the line where they were first encountered
        for (String key : symbolTable.keySet()) {
            System.out.println(key + "\t" + symbolTable.get(key).getLine());
        }
    }
}