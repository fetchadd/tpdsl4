package symtab.nested;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import symtab.nested.Cymbol.CymbolLexer;
import symtab.nested.Cymbol.CymbolParser;
import symtab.nested.Cymbol.DefRef;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Test {
    public static void main(String[] args) throws IOException {
        String testFile = Test.class.getResource("/t.cymbol").getPath();

        InputStream is = new FileInputStream(testFile);
        CharStream stream = CharStreams.fromStream(is);
        CymbolLexer lexer = new CymbolLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CymbolParser parser = new CymbolParser(tokens);

        ParseTree root = parser.file();
        DefRef defRef = new DefRef();
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(defRef, root);
    }
}
