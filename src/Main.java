import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


public class Main {
    private static HashMap<String, Integer> symbolTable = new HashMap<>();

    
    /** 
     * @param symbol
     */
    public static void addSymbolToTable(Symbol symbol){
        if (symbol.getType() == LexicalUnit.VARNAME && !symbolTable.containsKey(symbol.getValue())) {
            String varName = (String) symbol.getValue();
            symbolTable.put(varName, symbol.getLine());
        }
    }

    /**
     * Print the symbol table
     */
    public static void printSymbolTable() {
        System.out.println("\nVariables");
        for (String varName : symbolTable.keySet()) {
            System.out.println(varName + "   " + symbolTable.get(varName));
        }
    }
    /**
     * Run the main
     * @param args
     */
    public static void main(String[] args){
        String inputPath = null;
        String outputPath = null;
        boolean requiresOutput = false;
        if (args.length == 1){
            inputPath = args[0];
            System.out.println("Usage: java Main <input_file>");
            System.exit(1);
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

        TreeMap<String, Symbol> variableTable = new TreeMap<>();

        try{
            Parser parser = new Parser(new LexicalAnalyzer(new FileReader(inputPath)));
            ParseTree parseTree = parser.startParsing();

            /*Symbol symbol = lexicalAnalyzer.nextSymbol();
            while(symbol.getType() != LexicalUnit.EOS){
                System.out.println(symbol.toString());
                addSymbolToTable(symbol);
                symbol = lexicalAnalyzer.nextSymbol();
            }*/
            //printSymbolTable(); //print the symbol table
            // Retrieve the variable table
            variableTable = parser.getVariableTable();
            if (requiresOutput) {
                // Write the parse tree in the LaTeX file
                FileWriter fileWriter = new FileWriter(outputPath);
                fileWriter.write(parseTree.toLaTeX());
                fileWriter.close();
            }
            
        }
        catch(IOException e){
            System.out.println("Error while opening file: "+args[0]);
            System.exit(1);
        }

        if (!variableTable.isEmpty()) {
            System.out.println("\nVariables");

            for (Map.Entry<String, Symbol> variable : variableTable.entrySet()) {
                System.out.println(variable.getKey() + "\t" + variable.getValue().getLine());
            }
        }
    
    }
}