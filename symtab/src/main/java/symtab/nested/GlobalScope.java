package symtab.nested;

import symtab.nested.BaseScope;
import symtab.nested.Scope;

public class GlobalScope extends BaseScope {
    public GlobalScope() {
        super(null);

        this.define(new BuiltInTypeSymbol("int"));
        this.define(new BuiltInTypeSymbol("float"));
        this.define(new BuiltInTypeSymbol("void"));
    }

    public String getScopeName() {
        return "global";
    }
}
