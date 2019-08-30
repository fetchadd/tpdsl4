package tpdsl4.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AST {
    public String name;
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

    private String _toDot(HashMap<String, Integer> nodeRecord) {
        StringBuilder buf = new StringBuilder();

        String label = this.safeName();
        String name = label;

        if (nodeRecord.containsKey(label)) {
            nodeRecord.put(label, nodeRecord.get(label) + 1);
            name = name + nodeRecord.get(label).toString();
        } else {
            nodeRecord.put(label, 0);
        }

        buf.append(name);
        buf.append("[");
        buf.append("label=");
        buf.append(label);
        buf.append("]");
        buf.append(";");
        buf.append("\n");

        if (this.children != null) {
            for (AST child: this.children) {
                buf.append(child._toDot(nodeRecord));
                buf.append(name);
                buf.append("->");
                buf.append(child.safeName());
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
        buf.append("  edge [arrowsize=.5]\n");
        buf.append("  node [shape=plaintext, fontname=\"ArialNarrow\",\n");
        buf.append("        fontsize=12, fixedsize=true, height=.45];\n");
        buf.append("  ");

        HashMap<String, Integer> nodeRecords = new HashMap<>();
        buf.append(root._toDot(nodeRecords));
        buf.append("}\n");
        return buf.toString();
    }
}
