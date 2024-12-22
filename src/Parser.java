import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Parser for GILLES.
 * 
 * The parser implements a recursive descent mimicking the run of the pushdown automaton: the call stack replacing the automaton stack.
 * 
 * @author Mrudula Balachander, inspired from earlier versions of the project (exact authors not determined).
 *
 */
public class Parser{
    /**
     * Lexer object for the parsed file.
     */
    private LexicalAnalyzer scanner;
    /**
     * Current symbol at the head of the word to be read. This corresponds to the look-ahead (of length 1).
     */
    private Symbol current;
    /**
     * Option to print only the rule number (false) or the full rule (true).
     */
    private boolean fullRuleDisplay=false;
    /**
     * Width (in characters) of the widest left handside in a production rule.
     */
    private static final int widestNonTerm=13; // <Instruction>
    /**
     * Width (in characters) of the highest rule number.
     */
    private static final int log10ruleCard=2; // 35 rules

    /**
     * Creates a Parser object for the provided file and initialized the look-ahead.
     * 
     * @param source a FileReader object for the parsed file.
     * @throws IOException in case the lexing fails (syntax error).
     */
    public Parser(FileReader source) throws IOException{
        this.scanner = new LexicalAnalyzer(source);
        this.current = scanner.nextToken();
    }
    
    /* Display of the rules */
    /**
     * Returns a string of several spaces.
     * 
     * @param n the number of spaces.
     * @return a String containing n spaces.
     */
    private static String multispace(int n) {
        String res="";
        for (int i=0;i<n;i++) {
            res+=" ";
        };
        return res;
    }
    
    /**
     * Outputs the rule used in the LL descent.
     * 
     * @param rNum the rule number.
     * @param ruleLhs the left hand-side of the rule as a String.
     * @param ruleRhs the right hand-side of the rule as a String.
     * @param full a boolean specifying whether to write only the rule number (false) or the full rule (true).
     */
    private static void ruleOutput(int rNum, String ruleLhs,String ruleRhs, boolean full) {
        if (full) {
            System.out.println("   ["+rNum+"]"+
                multispace(1+log10ruleCard-String.valueOf(rNum).length())+ // Align left hand-sides regardless of number of digits in rule number
                ruleLhs+multispace(2+widestNonTerm-ruleLhs.length())+ // Align right hand-sides regardless of length of the left hand-side
                "→  "+ruleRhs);
        } else {
            System.out.print(rNum+" ");
        }
    }
    
    /**
     * Outputs the rule used in the LL descent, using the fullRuleDisplay value to set the option of full display or not.
     * 
     * @param rNum the rule number.
     * @param ruleLhs the left hand-side of the rule as a String.
     * @param ruleRhs the right hand-side of the rule as a String.
     */
    private void ruleOutput(int rNum, String ruleLhs,String ruleRhs) {
        ruleOutput(rNum,ruleLhs,ruleRhs,this.fullRuleDisplay);
    }
    
    /**
     * Sets the display option to "Full rules".
     */
    public void displayFullRules() {
        this.fullRuleDisplay=true;
    }
    
    /**
     * Sets the display option to "Rule numbers only".
     */
    public void displayRuleNumbers() {
        this.fullRuleDisplay=false;
    }

    /* Matching of terminals */
    /**
     * Advances in the input stream, consuming one token.
     * 
     * @throws IOException in case the lexing fails (syntax error).
     */
    private void consume() throws IOException{
        current = scanner.nextToken();
    }

    /**
     * Matches a (terminal) token from the head of the word.
     * 
     * @param token then LexicalUnit (terminal) to be matched.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the matching fails (syntax error): the next tolen is not the one to be matched.
     * @return a ParseTree made of a single leaf (the matched terminal).
     */
    private ParseTree match(LexicalUnit token) throws IOException, ParseException{
        if(!current.getType().equals(token)){
            // There is a parsing error
            throw new ParseException(current, Arrays.asList(token));
        }
        else {
            Symbol cur = current;
            consume();
            return new ParseTree(cur);
        }
    }
    
    /* Applying grammar rules */
    /**
     * Parses the file.
     * 
     * @return a ParseTree containing the parsed file structured by the grammar rules.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the parsing fails (syntax error).
     */
    public ParseTree parse() throws IOException, ParseException{
        // Program is the initial symbol of the grammar
        ParseTree pt = program();
        if (!this.fullRuleDisplay) {System.out.println();} // New line at the end of list of rules
        return pt;
    }
    
    /**
     * Treats a &lt;Program&gt; at the top of the stack.
     * 
     * Tries to apply rule [1]&nbsp;&lt;Program&gt;&nbsp;&rarr;&nbsp;<code>begin</code> &lt;Code&gt; <code>end</code>
     * 
     * @return a ParseTree with a &lt;Program&gt; non-terminal at the root.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the parsing fails (syntax error).
     */
    private ParseTree program() throws IOException, ParseException{
        // [1] <Program>  ->  begin <Code> end
        ruleOutput(1,"<Program>","LET [ProgName] BE <Code> END");
        return new ParseTree(NonTerminal.Program, Arrays.asList(
            match(LexicalUnit.LET),
            match(LexicalUnit.PROGNAME),
            match(LexicalUnit.BE),
            code(),
            match(LexicalUnit.END)
        ));
    }
    
    /**
     * Treats a &lt;Code&gt; at the top of the stack.
     * 
     * Tries to apply one of the rules <ul>
     *   <li>[2]&nbsp;&lt;Code&gt;&nbsp;&rarr;&nbsp;&lt;Instruction&gt;&nbsp;<code>:</code>&nbsp;&lt;Code&gt;</li>
     *   <li>[3]&nbsp;&lt;Code&gt;&nbsp;&rarr;&nbsp;&epsilon;</li>
     * </ul>
     * 
     * @return a ParseTree with a &lt;Code&gt; non-terminal at the root.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the parsing fails (syntax error).
     */
    private ParseTree code() throws IOException, ParseException{
        switch(current.getType()) {
            // [2] <Code>  ->  <Instruction>:<Code>
            case IF:
            case WHILE:
            case OUTPUT:
            case INPUT:
            case VARNAME:
                ruleOutput(2,"<Code>","<Instruction>:<Code>");
                return new ParseTree(NonTerminal.Code, Arrays.asList(
                    instruction(),
                    match(LexicalUnit.COLUMN),
                    code()
                ));
            // [3] <Code>  ->  EPSILON 
            case END:
            case ELSE:
                ruleOutput(3,"<Code>","ɛ");
                return new ParseTree(NonTerminal.Code, Arrays.asList(
                    new ParseTree(LexicalUnit.EPSILON)
                ));
            default:
                throw new ParseException(current,NonTerminal.Code,Arrays.asList(
                    LexicalUnit.IF,
                    LexicalUnit.ELSE,
                    LexicalUnit.WHILE,
                    LexicalUnit.OUTPUT,
                    LexicalUnit.INPUT,
                    LexicalUnit.VARNAME,
                    LexicalUnit.END
                ));
        }
    }

    /**
     * Treats a &lt;Instruction&gt; at the top of the stack.
     * 
     * Tries to apply one of the rules <ul>
     *   <li>[7]&nbsp;&lt;Instruction&gt;&nbsp;&rarr;&nbsp;&lt;Assign&gt;</li>
     *   <li>[8]&nbsp;&lt;Instruction&gt;&nbsp;&rarr;&nbsp;&lt;If&gt;</li>
     *   <li>[9]&nbsp;&lt;Instruction&gt;&nbsp;&rarr;&nbsp;&lt;While&gt;</li>
     *   <li>[10]&nbsp;&lt;Instruction&gt;&nbsp;&rarr;&nbsp;&lt;Print&gt;</li>
     *   <li>[11]&nbsp;&lt;Instruction&gt;&nbsp;&rarr;&nbsp;&lt;Read&gt;</li>
     *   <li>[12]&nbsp;&lt;Instruction&gt;&nbsp;&rarr;&nbsp;<code>begin</code> &lt;InstList&gt; <code>end</code></li>
     * </ul>
     * 
     * @return a ParseTree with a &lt;Instruction&gt; non-terminal at the root.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the parsing fails (syntax error).
     */
    private ParseTree instruction() throws IOException, ParseException{
        switch(current.getType()) {
            // [4] <Instruction>  ->  <Assign>
            case VARNAME:
                ruleOutput(4,"<Instruction>","<Assign>");
                return new ParseTree(NonTerminal.Instruction, Arrays.asList(
                    assignExpr()
                ));
            // [5] <Instruction>  ->  <If>
            case IF:
                ruleOutput(5,"<Instruction>","<If>");
                return new ParseTree(NonTerminal.Instruction, Arrays.asList(
                    ifExpr()
                ));
            // [6] <Instruction>  ->  <While>
            case WHILE:
                ruleOutput(6,"<Instruction>","<While>");
                return new ParseTree(NonTerminal.Instruction, Arrays.asList(
                    whileExpr()
                ));
            // [7] <Instruction>  ->  <Output>
            case OUTPUT:
                ruleOutput(7,"<Instruction>","<Output>");
                return new ParseTree(NonTerminal.Instruction, Arrays.asList(
                    outputExpr()
                ));
            // [8] <Instruction>  ->  <Input>
            case INPUT:
                ruleOutput(8,"<Instruction>","<Input>");
                return new ParseTree(NonTerminal.Instruction, Arrays.asList(
                    inputExpr()
                ));
            default:
                throw new ParseException(current,NonTerminal.Instruction,Arrays.asList(
                    LexicalUnit.VARNAME,
                    LexicalUnit.IF,
                    LexicalUnit.WHILE,
                    LexicalUnit.OUTPUT,
                    LexicalUnit.INPUT
                ));
        }
    }
    
    /**
     * Treats a &lt;Assign&gt; at the top of the stack.
     * 
     * Tries to apply rule [13]&nbsp;&lt;Assign&gt;&nbsp;&rarr;&nbsp;[Varname]<code>:=</code>&lt;ExprArith&gt;
     * 
     * @return a ParseTree with a &lt;Assign&gt; non-terminal at the root.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the parsing fails (syntax error).
     */
    private ParseTree assignExpr() throws IOException, ParseException{
        // [9] <Assign>  ->  [Varname] = <ExprArith>
        ruleOutput(9,"<Assign>","[Varname] = <ExprArith>");
        return new ParseTree(NonTerminal.Assign, Arrays.asList(
            match(LexicalUnit.VARNAME),
            match(LexicalUnit.ASSIGN),
            exprArith()
        ));
    }
    
    /**
     * Treats a &lt;ExprArith&gt; at the top of the stack.
     * 
     * Tries to apply rule [10]&nbsp;&lt;ExprArith&gt;&nbsp;&rarr;&nbsp;&lt;Prod&gt;&lt;ExprArith'&gt;
     * 
     * @return a ParseTree with a &lt;ExprArith&gt; non-terminal at the root.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the parsing fails (syntax error).
     */
    private ParseTree exprArith() throws IOException, ParseException{
        switch (current.getType()) {
            case MINUS:
            case LPAREN:
            case VARNAME:
            case NUMBER:
                // [10] <ExprArith>  ->  <Prod> <ExprArith'>
                ruleOutput(10,"<ExprArith>","<Prod> <ExprArith'>");
                return new ParseTree(NonTerminal.ExprArith, Arrays.asList(
                    prod(),
                    exprArithPrime()
                ));
            default:
                throw new ParseException(current,NonTerminal.ExprArith,Arrays.asList(
                    LexicalUnit.MINUS,
                    LexicalUnit.LPAREN,
                    LexicalUnit.VARNAME,
                    LexicalUnit.NUMBER
                ));
        }
    }

    /**
     * Treats a &lt;ExprArith'&gt; at the top of the stack.
     * 
     * Tries to apply one of the rules <ul>
     *   <li>[11]&nbsp;&lt;ExprArith'&gt;&nbsp;&rarr;&nbsp;<code>+</code>&lt;Prod&gt;&lt;ExprArith'&gt;</li>
     *   <li>[12]&nbsp;&lt;ExprArith'&gt;&nbsp;&rarr;&nbsp;<code>-</code>&lt;Prod&gt;&lt;ExprArith'&gt;</li>
     *   <li>[13]&nbsp;&lt;ExprArith'&gt;&nbsp;&rarr;&nbsp;&epsilon;</li>
     * </ul>
     * 
     * @return a ParseTree with a &lt;ExprArith'&gt; non-terminal at the root.
     * @throws IOException in case the lexing fails (syntax error).
     * @throws ParseException in case the parsing fails (syntax error).
     */
    private ParseTree exprArithPrime() throws IOException, ParseException{
        switch (current.getType()) {
            // [11] <ExprArith'>  ->  + <Prod> <ExprArith'>
            case PLUS:
                ruleOutput(11,"<ExprArith'>","+ <Prod> <ExprArith'>");
                return new ParseTree(NonTerminal.ExprArithPrime, Arrays.asList(
                    match(LexicalUnit.PLUS),
                    prod(),
                    exprArithPrime()
                ));
            // [12] <ExprArith'>  ->  - <Prod> <ExprArith'>
            case MINUS:
                ruleOutput(12,"<ExprArith'>","- <Prod> <ExprArith'>");
                return new ParseTree(NonTerminal.ExprArithPrime, Arrays.asList(
                    match(LexicalUnit.MINUS),
                    prod(),
                    exprArithPrime()
                ));
            // [13] <ExprArith'>  ->  EPSILON
            case COLUMN:
            case RPAREN:
            case RBRACK:
            case EQUAL:
            case SMALEQ:
            case SMALLER:
            case IMPLIES:
            case PIPE:
                ruleOutput(13,"<ExprArith'>","ɛ");
                return new ParseTree(NonTerminal.ExprArithPrime, Arrays.asList(
                    new ParseTree(LexicalUnit.EPSILON)
                ));
            default:
                throw new ParseException(current,NonTerminal.ExprArithPrime,Arrays.asList(
                    LexicalUnit.PLUS,
                    LexicalUnit.MINUS,
                    LexicalUnit.COLUMN,
                    LexicalUnit.RPAREN,
                    LexicalUnit.RBRACK,
                    LexicalUnit.EQUAL,
                    LexicalUnit.SMALEQ,
                    LexicalUnit.SMALLER,
                    LexicalUnit.IMPLIES,
                    LexicalUnit.PIPE
                ));
        }
    }
}
