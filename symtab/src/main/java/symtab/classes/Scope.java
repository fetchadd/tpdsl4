package symtab.classes;

public interface Scope {
    public String getScopeName();
    public Scope getParentScope();
    public Scope getEnclosingScope();
}
