package tpdsl4.tools;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AST {
    public String name;
    public ParseTree parseNode;
    public List<AST> children;
    String[] keywords = {
            ";", "=", "->", "+", ",", "[", "]", "*"
    };

    public void addChild(AST ast) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }

        this.children.add(ast);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();

        if (this.children != null) {
            buffer.append(" (");

            buffer.append(this.name);

            for (AST child: this.children) {
                buffer.append(child.toString());
                buffer.append(" ");
            }


            buffer.append(")");
        } else {
            buffer.append(" ");
            buffer.append(this.name);
        }

        return buffer.toString();
    }

    public String safeName() {
        for (String kw: keywords) {
            if (kw.equals(this.name)) {
                return '"' + this.name + '"';
            }
        }


        return this.name;
    }

    public String getId() {
        int charPosition;

        if (this.parseNode instanceof TerminalNode) {
            charPosition = ((TerminalNode) this.parseNode).getSymbol().getCharPositionInLine();
        } else {
            charPosition = ((ParserRuleContext) this.parseNode).getStart().getCharPositionInLine();
        }

        return "\"" + this.name  + "@" + charPosition + "\"";
    }

    private String _toDot(HashMap<String, Integer> nodeRecord) {
        StringBuilder buf = new StringBuilder();

        String label = this.safeName();
        String id = this.getId();

        buf.append(id);
        buf.append("[");
        buf.append("label=");
        buf.append(label);
        buf.append("]");
        buf.append(";");
        buf.append("\n");

        if (this.children != null) {
            for (AST child: this.children) {
                buf.append(child._toDot(nodeRecord));
                buf.append(id);
                buf.append("->");
                buf.append(child.getId());
                buf.append(";");
                buf.append("\n");
            }
        }

        return buf.toString();
    }

    public static String toDot(AST root) {
        StringBuilder buf = new StringBuilder();
        buf.append("digraph G {\n");
        buf.append("  ranksep=.25;\n");
        buf.append("  nodesep=.25;\n");
        buf.append("  edge [arrowsize=.25]\n");
        buf.append("  node [shape=plaintext,\n");
        buf.append("        fontsize=12, height=.45];\n");
        buf.append("  ");

        HashMap<String, Integer> nodeRecords = new HashMap<>();
        buf.append(root._toDot(nodeRecords));
        buf.append("}\n");
        return buf.toString();
    }
}
