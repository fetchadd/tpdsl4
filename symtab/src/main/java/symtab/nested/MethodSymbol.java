package symtab.nested;

import java.util.LinkedHashMap;
import java.util.Map;

public class MethodSymbol extends Symbol implements Scope {
    Map<String, Symbol> orderedArgs = new LinkedHashMap<>();
    Scope enclosingScope;

    public MethodSymbol(String name, Type retType, Scope enclosingScope) {
        super(name, retType);
        this.enclosingScope = enclosingScope;
    }

    public Symbol resolve(String name) {
        Symbol s = orderedArgs.get(name);

        if (s != null) {
            return s;
        }

        if (this.enclosingScope != null) {
            return this.enclosingScope.resolve(name);
        }

        return null;
    }

    public void define(Symbol symbol) {
        this.orderedArgs.put(symbol.name, symbol);
        symbol.scope = this;
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public String getScopeName() {
        return name;
    }

    public String toString() {
        return "method"+super.toString()+":"+orderedArgs.values();
    }
}
