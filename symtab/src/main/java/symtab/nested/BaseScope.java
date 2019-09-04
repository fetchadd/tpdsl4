package symtab.nested;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseScope  implements Scope {
    Scope enclosingScope;
    Map<String, Symbol> symbols = new LinkedHashMap<>();

    public BaseScope(Scope enclosingScope) {
        this.enclosingScope = enclosingScope;
    }

    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);

        if (s != null) {
            return s;
        }

        if (enclosingScope != null) {
            return enclosingScope.resolve(name);
        }

        return null;
    }

    public void define(Symbol sym) {
        symbols.put(sym.name, sym);
        sym.scope = this; // track the scope in each symbol
    }

    public Scope getEnclosingScope() { return enclosingScope; }

    public String toString() { return symbols.keySet().toString(); }
}
