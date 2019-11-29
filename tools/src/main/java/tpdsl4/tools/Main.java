package tpdsl4.tools;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFile = null;

        if (args.length > 0) {
            inputFile = args[0];
        }

        InputStream is = System.in;

        if (inputFile != null) {
            is = new FileInputStream(inputFile);
        }

        CharStream charStream = CharStreams.fromStream(is);
        LispLexer lexer = new LispLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LispParser parser = new LispParser(tokens);

        ParseTree root = parser.list();
        AST astRoot = adaptTree(root);
        System.out.println(AST.toDot(astRoot));
    }

    public static AST adaptTree(ParseTree root) {
        if (root instanceof LispParser.ListContext) {
            AST astRoot = new AST();

            astRoot.name = root.getChild(1).getText();
            astRoot.parseNode = root.getChild(1);

            for (int i = 2, count = root.getChildCount(); i < count - 1; i ++ ) {
                AST astChild = adaptTree(root.getChild(i));
                astRoot.addChild(astChild);
            }

            return astRoot;
        }


        AST astLeaf = new AST();
        astLeaf.name = root.getText();
        astLeaf.parseNode = root;

        return astLeaf;
    }
}
