import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
//        System.out.println(parser.prog().toStringTree(parser));

        List<VecMathParser.StatContext> trees = parser.prog().stat();

        ParseTreePattern pattern = parser.compileParseTreePattern(
                "<ID> = <expr>",
                VecMathParser.RULE_stat
        );
        ParseTreePattern pattern1 = parser.compileParseTreePattern(
                "<primary> + <primary>",
                VecMathParser.RULE_expr
        );

        for (VecMathParser.StatContext tree: trees) {
            matchTree(tree, pattern, parser);
            matchTree(tree.expr(), pattern1, parser);
        }


    }

    static void matchTree(ParseTree tree, ParseTreePattern pattern, Parser parser) {
        ParseTreeMatch match = pattern.match(tree);

        if (match.succeeded()) {
            for (String key : match.getLabels().keySet()) {
                Object values = match.getLabels().get(key);

                if (values instanceof List) {
                    for (ParseTree n: (List<ParseTree>)values) {
                        if (n instanceof ParserRuleContext) {
                            System.out.printf("%s=%s\n", key, n.toStringTree(parser));
                        } else {
                            System.out.printf("%s=%s\n", key, n);
                        }
                    }
                }
            }
//            System.out.println(match.get("ID").getText());
//            System.out.println(match.get("expr").getText());
        }
    }
}
