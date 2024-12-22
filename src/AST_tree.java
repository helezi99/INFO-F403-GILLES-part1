import java.util.ArrayList;
import java.util.List;

public class AST_tree {

    /**
     * @param parseTree the node of the tree that we want to cast to AST
     * @param context the context of that node, i.e. what it needs from the parent
     * @return the AST
     */
    public ParseTree castParseTreeToAST(ParseTree parseTree, ParseTree context) {

        Symbol currentSymbol = parseTree.getLabel();

        List<ParseTree> children = new ArrayList<>();

        switch(currentSymbol.getType()) {
            case Program -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(3), null));
                parseTree.setChildren(children);
            }
            case Code -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(0), null));
                if (parseTree.getChildren().get(2).getLabel().getType() != LexicalUnit.EPSILON) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                }
                parseTree.setChildren(children);
            }
            case Instruction -> {
                parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
            }
            case Assign -> {
                children.add(parseTree.getChildren().get(0)); // VARNAME
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null)); // <ExprArith>
                parseTree = parseTree.getChildren().get(1); // "="
                parseTree.setChildren(children);
            }
            case ExprArith, Prod -> {
                if (parseTree.getChildren().get(1).getLabel().getType() == LexicalUnit.EPSILON) {
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
                } else {
                    context = castParseTreeToAST(parseTree.getChildren().get(0), null);
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(1), context);
                }
            }
            case ExprArith2, Prod2 -> {
                if (parseTree.getChildren().get(2).getLabel().getType() == LexicalUnit.EPSILON) {
                    children.add(context);
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null));
                    parseTree = new ParseTree(parseTree.getChildren().get(0).getLabel(), children);
                } else {
                    children.add(context);
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null));
                    context = new ParseTree(parseTree.getChildren().get(0).getLabel(), children);
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(2), context);
                }
            }
            case Atom -> {
                if (parseTree.getChildren().size() == 1) {
                    parseTree = parseTree.getChildren().get(0);
                } else if (parseTree.getChildren().size() == 2) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null));
                    parseTree.setLabel(parseTree.getChildren().get(0).getLabel());
                    parseTree.setChildren(children);
                } else if (parseTree.getChildren().size() == 3) {
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(1), null);
                }
            }
            case If -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                children.add(castParseTreeToAST(parseTree.getChildren().get(5), null));
                if (parseTree.getChildren().get(6).getChildren().get(0).getLabel().getType() != LexicalUnit.END) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(6), null));
                }
                parseTree.setChildren(children);
            }
            case EndIf -> {
                parseTree = castParseTreeToAST(parseTree.getChildren().get(1), null);
            }
            case Cond -> {
                //ParseTree condAtomResult = castParseTreeToAST(parseTree.getChildren().get(0), null);
                //ParseTree condImplNode = parseTree.getChildren().get(1);
                if (parseTree.getChildren().get(1).getLabel().getType() == LexicalUnit.EPSILON) {

                    parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
                } else {
                    ParseTree condImplResult = castParseTreeToAST(parseTree.getChildren().get(1), null); 
                    condImplResult.getChildren().add(0, castParseTreeToAST(parseTree.getChildren().get(0), null));
                    parseTree = condImplResult; 
                }
            }
            case CondImpl -> {
                        
                // Grammar: CondImpl -> IMPLIES CondAtom CondImpl | EPSILON
                if (parseTree.getChildren().get(0).getLabel().getType() == LexicalUnit.EPSILON) {
                    // No operator, just return epsilon or something that indicates no more conditions
                    parseTree = parseTree.getChildren().get(0); // this is an EPSILON node
                } else {
                    // IMPLIES CondAtom CondImpl
                    ParseTree impliesToken = parseTree.getChildren().get(0); // IMPLIES node
                    ParseTree condAtomResult = castParseTreeToAST(parseTree.getChildren().get(1), null);
                    ParseTree nextImpl = castParseTreeToAST(parseTree.getChildren().get(2), null);

                    // nextImpl could be epsilon or another IMPLIES chain
                    children.add(condAtomResult);
                    if (nextImpl.getLabel().getType() != LexicalUnit.EPSILON) {
                        // nextImpl is an IMPLIES node with its own children
                        // We attach nextImpl as a child. This naturally forms a chain of conditions.
                        children.add(nextImpl);
                    }

                    impliesToken.setChildren(children);
                    parseTree = impliesToken;
                }
            }


            case CondAtom -> {
                if (parseTree.getChildren().size() == 3 &&
                parseTree.getChildren().get(0).getLabel().getType() == LexicalUnit.PIPE &&
                parseTree.getChildren().get(2).getLabel().getType() == LexicalUnit.PIPE) {
                
                parseTree = castParseTreeToAST(parseTree.getChildren().get(1), null); 
                } else {
                // <ExprArith> <Comp> <ExprArith>
                ParseTree leftExpr = castParseTreeToAST(parseTree.getChildren().get(0), null);
                ParseTree compNode = castParseTreeToAST(parseTree.getChildren().get(1), null);
                ParseTree rightExpr = castParseTreeToAST(parseTree.getChildren().get(2), null);

                // compNode is something like "SMALLER", "SMALEQ", etc.
                compNode.setChildren(List.of(leftExpr, rightExpr));
                parseTree = compNode;
                }
             }

            case Comp -> {
                parseTree = parseTree.getChildren().get(0);
            }
            case While -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                children.add(castParseTreeToAST(parseTree.getChildren().get(5), null));
                parseTree.setChildren(children);
            }
            case For -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                children.add(castParseTreeToAST(parseTree.getChildren().get(4), null));
                children.add(castParseTreeToAST(parseTree.getChildren().get(7), null));
                parseTree.setChildren(children);
            }
            case Input, Output -> {
                children.add(parseTree.getChildren().get(2));
                parseTree.setChildren(children);
            }
        }

        return parseTree;
    }
}