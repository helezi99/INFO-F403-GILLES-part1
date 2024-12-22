import java.io.*;
import java.util.Objects;


/**
* The Main class is the entry point for the program. 
 * It handles the parsing of an input file, and optionally outputs the corresponding parse tree 
 * into a LaTeX file based on the user's command-line arguments.
 */
public class Main {
    //private static HashMap<String, Integer> symbolTable = new HashMap<>();
    /*
     * @param symbol
    public static void addSymbolToTable(Symbol symbol){
        if (symbol.getType() == LexicalUnit.VARNAME && !symbolTable.containsKey(symbol.getValue())) {
            String varName = (String) symbol.getValue();
            symbolTable.put(varName, symbol.getLine());
        }
    }
    // Print the symbol table 
    public static void printSymbolTable() {
        System.out.println("\nVariables");
        for (String varName : symbolTable.keySet()) {
            System.out.println(varName + "   " + symbolTable.get(varName));
        }
    }*/

    /**
     * The main entry point of the parser application. 
     * It parses a given input file and optionally outputs the parse tree to a LaTeX file.
     * @param args the command-line arguments:
     *             - 1 argument: input file path.
     *             - 3 arguments: `-wt`, output file path, and input file path.
     * @throws IllegalArgumentException if the arguments provided are not in the expected format.
     */
    public static void main(String[] args){
        String inputPath = null;
        String outputPath = null;
        boolean requiresOutput = false;
        
        if (args.length == 1){
            inputPath = args[0];
        } else if (args.length == 3){
            if (Objects.equals(args[0], "-wt")) {
                requiresOutput = true;
                inputPath = args[2];
                outputPath = args[1];
            }
        } else {
            System.out.println("Arguments expected : [-wt output_file] input_file");
            throw new IllegalArgumentException("Wrong arguments");
        }
        try{
            Parser parser = new Parser(new LexicalAnalyzer(new FileReader(inputPath)));
            ParseTree parseTree = parser.startParsing();
            AST_tree astTree = new AST_tree();
            parseTree = astTree.castParseTreeToAST(parseTree, null);
            if (requiresOutput) {
                // Write the parse tree in the LaTeX file
                FileWriter fileWriter = new FileWriter(outputPath);
                fileWriter.write(parseTree.toLaTeX());
                fileWriter.close();
            }
            Compiler compiler = new Compiler(parseTree);
            compiler.compile();

            /*Symbol symbol = lexicalAnalyzer.nextSymbol();
            while(symbol.getType() != LexicalUnit.EOS){
                System.out.println(symbol.toString());
                addSymbolToTable(symbol);
                symbol = lexicalAnalyzer.nextSymbol();
            }*/
            //printSymbolTable(); //print the symbol table
            // Retrieve the variable table    
        }
        catch(IOException e){
            System.out.println("Error while opening file: "+args[0]);
            System.exit(1);
        }
    }
}