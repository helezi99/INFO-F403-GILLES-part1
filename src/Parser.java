import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements a recursive-descent parser for a custom programming language.
 * It relies on a lexical analyzer to tokenize the input and builds a parse tree for
 * the program's syntax.
 */

public class Parser {
    private final LexicalAnalyzer lexer;
    private Symbol currentToken;
    
    /**
     * Constructs a Parser with the specified lexical analyzer.
     *
     * @param lexer the lexical analyzer providing the tokens for parsing.
     */
    public Parser(LexicalAnalyzer lexer){
        this.lexer = lexer;
    }


    /**
     * Starts parsing from the root of the grammar and returns the resulting parse tree.
     *
     * @return the ParseTree representing the program structure.
     */
    public ParseTree startParsing() {
        nextToken();
        return Program();
    }

    /**
     * Parses the program structure, starting with the main `LET` and ending with `END`.
     *
     * @return the ParseTree node representing the program.
     */
    private ParseTree Program() {
        //System.out.print("1 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(match(LexicalUnit.LET));
        leaves.add(match(LexicalUnit.PROGNAME));
        leaves.add(match(LexicalUnit.BE));
        leaves.add(Code());
        leaves.add(match(LexicalUnit.END));
        System.out.print("\n");
        return new ParseTree(new Symbol(LexicalUnit.Program, "Program"), leaves);
    }

    /**
     * Parses the sequence of statements or returns an epsilon node if none are present.
     *
     * @return the ParseTree node for the `Code` grammar rule.
     */
    private ParseTree Code() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case END, ELSE -> {
                //System.out.print("3 ");
                return new ParseTree(new Symbol(LexicalUnit.EPSILON, "$\\epsilon$"));
            }
            case IF, WHILE, FOR, OUTPUT, INPUT, VARNAME -> {
                //System.out.print("2 ");
                leaves.add(Instruction());
                leaves.add(match(LexicalUnit.COLUMN)); 
                leaves.add(Code());
                return new ParseTree(new Symbol(LexicalUnit.Code, "Code"), leaves);
            }
            default -> throw new RuntimeException("\nParsing Error, Unexpected token : " + currentToken.getType() + " at line " + currentToken.getLine());
        }
    }

    /**
     * Parses a single instruction based on its starting token (e.g., IF, WHILE).
     *
     * @return the ParseTree node for the `Instruction` grammar rule.
     */
    private ParseTree Instruction() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case IF -> {
                //System.out.print("5 ");
                leaves.add(If());
            }
            case WHILE -> {
                //System.out.print("6 ");
                leaves.add(While());
            }
            case FOR -> {
                //System.out.print("6 ");
                leaves.add(For());
            }
            case OUTPUT -> {
                //System.out.print("7 ");
                leaves.add(Output());
            }
            case INPUT -> {
                //System.out.print("8 ");
                leaves.add(Input());
            }
            case VARNAME -> {
                //System.out.print("4 ");
                leaves.add(Assign());
            }
            default -> throw new RuntimeException();
        }
        return new ParseTree(new Symbol(LexicalUnit.Instruction, "Instruction"), leaves);
    }

    /**
     * Parses an assignment statement.
     *
     * @return the ParseTree node for the `Assign` grammar rule.
     */
    private ParseTree Assign() {
        //System.out.print("9 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(match(LexicalUnit.VARNAME));
        leaves.add(match(LexicalUnit.ASSIGN));
        leaves.add(ExprArith());
        return new ParseTree(new Symbol(LexicalUnit.Assign, "Assign"), leaves);
    }

    /**
     * Parses an if-then-else conditional statement.
     *
     * @return the ParseTree node for the `If` grammar rule.
     */
    private ParseTree If() {
        //System.out.print("22 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(match(LexicalUnit.IF));
        leaves.add(match(LexicalUnit.LBRACK));
        leaves.add(Cond());
        leaves.add(match(LexicalUnit.RBRACK));
        leaves.add(match(LexicalUnit.THEN));
        leaves.add(Code());
        leaves.add(EndIf());
        return new ParseTree(new Symbol(LexicalUnit.If, "If"), leaves);
    }

    /**
     * Parses the `else` part of an if-then-else statement, if present.
     *
     * @return the ParseTree node for the `EndIf` grammar rule.
     */
    private ParseTree EndIf() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case END -> {
                //System.out.print("24 ");
                leaves.add(match(LexicalUnit.END));
            }
            case ELSE -> {
                //System.out.print("23 ");
                leaves.add(match(LexicalUnit.ELSE));
                leaves.add(Code());
                leaves.add(match(LexicalUnit.END));
            }
            default -> throw new RuntimeException();
        }
        return new ParseTree(new Symbol(LexicalUnit.EndIf, "EndIf"), leaves);
    } 

    /**
     * Parses a while loop.
     *
     * @return the ParseTree node for the `While` grammar rule.
     */
    private ParseTree While() {
        //System.out.print("33 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(match(LexicalUnit.WHILE));
        leaves.add(match(LexicalUnit.LBRACK));
        leaves.add(Cond());
        leaves.add(match(LexicalUnit.RBRACK));
        leaves.add(match(LexicalUnit.REPEAT));
        leaves.add(Code());
        leaves.add(match(LexicalUnit.END));
        return new ParseTree(new Symbol(LexicalUnit.While, "While"), leaves);
    }

    private ParseTree For() {
        //System.out.print("33 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(match(LexicalUnit.FOR));
        leaves.add(match(LexicalUnit.LBRACK));
        leaves.add(Assign());
        leaves.add(match(LexicalUnit.TO));
        leaves.add(ExprArith());
        leaves.add(match(LexicalUnit.RBRACK));
        leaves.add(match(LexicalUnit.REPEAT));
        leaves.add(Code());
        leaves.add(match(LexicalUnit.END));
        return new ParseTree(new Symbol(LexicalUnit.For, "For"), leaves);
    }

    /**
     * Parses an output statement.
     *
     * @return the ParseTree node for the `Output` grammar rule.
     */
    private ParseTree Output() {
        //System.out.print("34 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(match(LexicalUnit.OUTPUT));
        leaves.add(match(LexicalUnit.LPAREN));
        leaves.add(match(LexicalUnit.VARNAME));
        leaves.add(match(LexicalUnit.RPAREN));
        return new ParseTree(new Symbol(LexicalUnit.Output, "Output"), leaves);
    }

    /**
     * Parses an input statement.
     *
     * @return the ParseTree node for the `Input` grammar rule.
     */
    private ParseTree Input() {
        //System.out.print("35 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(match(LexicalUnit.INPUT));
        leaves.add(match(LexicalUnit.LPAREN));
        leaves.add(match(LexicalUnit.VARNAME));
        leaves.add(match(LexicalUnit.RPAREN));
        return new ParseTree(new Symbol(LexicalUnit.Input, "Input"), leaves);
    }

    /**
     * Parses a condition or logical expression.
     *
     * @return the ParseTree node for the `Cond` grammar rule.
     */
    private ParseTree Cond() {
        //System.out.print("25 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(CondAtom());
        leaves.add(CondImpl());
        return new ParseTree(new Symbol(LexicalUnit.Cond, "Cond"), leaves);
    }

    /**
     * Parses the implication part of a logical expression.
     *
     * @return the ParseTree node for the `CondImpl` grammar rule.
     */
    private ParseTree CondImpl() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case IMPLIES -> {
                //System.out.print("26 ");
                leaves.add(match(LexicalUnit.IMPLIES));
                leaves.add(CondAtom());
                leaves.add(CondImpl());
            }
            case RPAREN, RBRACK, PIPE -> {
                //System.out.print("27 ");
                return new ParseTree(new Symbol(LexicalUnit.EPSILON, "$\\epsilon$"));
            }
            default -> throw new RuntimeException("\nParsing Error, Unexpected token : " + currentToken.getType() + " at line " + currentToken.getLine());
        }
        return new ParseTree(new Symbol(LexicalUnit.CondImpl, "CondImpl"), leaves);
    }

    /**
     * Parses a condition or logical operation.
     *
     * @return the ParseTree node for the `CondAtom` grammar rule.
     */
    private ParseTree CondAtom() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case VARNAME, NUMBER, MINUS, LPAREN -> {
                //System.out.print("28 ");
                leaves.add(ExprArith());
                leaves.add(Comp());
                leaves.add(ExprArith());
            }
            case PIPE -> {
                //System.out.print("29 ");
                leaves.add(match(LexicalUnit.PIPE));
                leaves.add(Cond());
                leaves.add(match(LexicalUnit.PIPE));
            }
            default -> throw new RuntimeException("\nParsing Error, Unexpected token : " + currentToken.getType() + " at line " + currentToken.getLine());
        }
        return new ParseTree(new Symbol(LexicalUnit.CondAtom, "CondAtom"), leaves);
    }



    /**
     * Parses an mathematic comparator.
     *
     * @return the ParseTree node for the `Comp` grammar rule.
     */
    private ParseTree Comp() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case EQUAL -> {
                //System.out.print("30 ");
                leaves.add(match(LexicalUnit.EQUAL));
            }
            case SMALEQ -> {
                //System.out.print("31 ");
                leaves.add(match(LexicalUnit.SMALEQ));
            }
            case SMALLER -> {
                //System.out.print("32 ");
                leaves.add(match(LexicalUnit.SMALLER));
            }
            default -> throw new RuntimeException("\nParsing Error, Unexpected token : " + currentToken.getType() + " at line " + currentToken.getLine());
        }
        return new ParseTree(new Symbol(LexicalUnit.Comp, "Comparator"), leaves);
    }

    /**
     * Parses an arithmetic expression.
     *
     * @return the ParseTree node for the `ExprArith` grammar rule.
     */
    private ParseTree ExprArith() {
        //System.out.print("10 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(Prod());
        leaves.add(ExprArith2());
        return new ParseTree(new Symbol(LexicalUnit.ExprArith, "ExprArith"), leaves);
    }

    /**
     * Parses an addition, a substraction or nothing.
     *
     * @return the ParseTree node for the `ExprArith2` grammar rule.
     */
    private ParseTree ExprArith2() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case PLUS -> {
                //System.out.print("15 ");
                leaves.add(match(LexicalUnit.PLUS));
                leaves.add(Prod());
                leaves.add(ExprArith2());
            }
            case MINUS -> {
                //System.out.print("16 ");
                leaves.add(match(LexicalUnit.MINUS));
                leaves.add(Prod());
                leaves.add(ExprArith2());
            }
            case COLUMN, RPAREN, EQUAL, SMALEQ, SMALLER, RBRACK, PIPE, IMPLIES, TO -> {
                //System.out.print("17 ");
                return new ParseTree(new Symbol(LexicalUnit.EPSILON, "$\\epsilon$"));
            }
            default -> throw new RuntimeException("\nParsing Error, Unexpected token : " + currentToken.getType() + " at line " + currentToken.getLine());
        }
        return new ParseTree(new Symbol(LexicalUnit.ExprArith2, "ExprArithPrime"), leaves);
    }

    /**
     * Parses a product term in an arithmetic expression.
     *
     * @return the ParseTree node for the `Prod` grammar rule.
     */
    private ParseTree Prod() {
        //System.out.print("11 ");
        List<ParseTree> leaves = new ArrayList<>();
        leaves.add(Atom());
        leaves.add(Prod2());
        return new ParseTree(new Symbol(LexicalUnit.Prod, "Product"), leaves);
    }

    /**
     * Parses a multiplication, a division or nothing.
     *
     * @return the ParseTree node for the `Prod2` grammar rule.
     */
    private ParseTree Prod2() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case TIMES -> {
                //System.out.print("12 ");
                leaves.add(match(LexicalUnit.TIMES));
                leaves.add(Atom());
                leaves.add(Prod2());
            }
            case DIVIDE -> {
                //System.out.print("13 ");
                leaves.add(match(LexicalUnit.DIVIDE));
                leaves.add(Atom());
                leaves.add(Prod2());
            }
            case COLUMN, PLUS, MINUS, RPAREN, EQUAL, SMALEQ, SMALLER, RBRACK, PIPE, IMPLIES, TO -> {
                //System.out.print("14 ");
                return new ParseTree(new Symbol(LexicalUnit.EPSILON, "$\\epsilon$"));
            }
            default -> throw new RuntimeException("\nParsing Error, Unexpected token : " + currentToken.getType() + " at line " + currentToken.getLine());
        }
        return new ParseTree(new Symbol(LexicalUnit.Prod2, "ProductPrime"), leaves);
    }

    /**
     * Parses an atomic operand in an arithmetic expression.
     *
     * @return the ParseTree node for the `Atom` grammar rule.
     */
    private ParseTree Atom() {
        List<ParseTree> leaves = new ArrayList<>();
        switch (currentToken.getType()) {
            case MINUS -> {
                //System.out.print("18 ");
                leaves.add(match(LexicalUnit.MINUS));
                leaves.add(Atom());
            }
            case NUMBER -> {
                //System.out.print("20 ");
                leaves.add(match(LexicalUnit.NUMBER));
            }
            case VARNAME -> {
                //System.out.print("19 ");
                leaves.add(match(LexicalUnit.VARNAME));
            }
            case LPAREN -> {
                //System.out.print("21 ");
                leaves.add(match(LexicalUnit.LPAREN));
                leaves.add(ExprArith());
                leaves.add(match(LexicalUnit.RPAREN));
            }
            default -> throw new RuntimeException("\nParsing Error, Unexpected token : " + currentToken.getType() + " at line " + currentToken.getLine());
        }
        return new ParseTree(new Symbol(LexicalUnit.Atom, "Atom"), leaves);

    }

    /**
     * Allows for interfacing the nextToken function of the LexicalAnalyser
     */
    private void nextToken() {
        try {
            currentToken = lexer.nextSymbol();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't get the next token from lexical analyser", e);
        }

    }

    /**
     * Matches the current token against an expected token type and advances to the next token.
     *
     * @param expectedToken the type of token expected.
     * @return a ParseTree leaf node containing the matched token.
     * @throws RuntimeException if the current token does not match the expected type.
     */
    private ParseTree match(LexicalUnit expectedToken) {
        ParseTree leaf;
        if (!currentToken.getType().equals(expectedToken)) {
            throw new RuntimeException("\nParsing Error, Expected : " + expectedToken + ", Actual : " + currentToken.getType());
        } else {
            
            leaf = new ParseTree(currentToken);
            nextToken();
        }
        return leaf;
    }
}
