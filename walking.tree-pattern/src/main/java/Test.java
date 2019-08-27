import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {
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
        VecMathLexer lexer = new VecMathLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        VecMathParser parser = new VecMathParser(tokens);

        List<VecMathParser.StatContext> trees = parser.prog().stat();


        ParseTreePattern pattern = parser.compileParseTreePattern("<ID> = <expr>", VecMathParser.RULE_stat);

        for (VecMathParser.StatContext tree: trees) {
            ParseTreeMatch match = pattern.match(tree);

            if (match.succeeded()) {
                System.out.println(match.get("ID").getText());
                System.out.println(match.get("expr").getText());
            }
        }
    }
}
