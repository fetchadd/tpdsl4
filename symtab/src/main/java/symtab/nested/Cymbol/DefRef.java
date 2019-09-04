package symtab.nested.Cymbol;

import org.antlr.v4.runtime.Token;
import symtab.nested.*;

public class DefRef extends CymbolBaseListener {
    private GlobalScope globals;
    private Scope currentScope;

    @Override
    public void enterFile(CymbolParser.FileContext ctx) {
        globals = new GlobalScope();
        currentScope = globals;
    }

    @Override
    public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        String name = ctx.ID().getText();
        Symbol symbol = currentScope.resolve(ctx.type().getText());

        if (! (symbol instanceof BuiltInTypeSymbol)) {
            error(ctx.type().start, "invalid method return type: " + ctx.type().getText());
            return;
        }

        Type type = (Type) symbol;
        MethodSymbol methodSymbol = new MethodSymbol(name, type, currentScope);
        currentScope.define(methodSymbol);
        currentScope = methodSymbol;
    }

    @Override
    public void exitFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }


    @Override
    public void enterBlock(CymbolParser.BlockContext ctx) {
        LocalScope scope = new LocalScope(currentScope);
        currentScope = scope;
    }

    @Override
    public void exitBlock(CymbolParser.BlockContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }


    @Override
    public void exitVarDecl(CymbolParser.VarDeclContext ctx) {
        String name = ctx.ID().getSymbol().getText();
        Symbol type = currentScope.resolve(ctx.type().getText());

        if (!(type instanceof BuiltInTypeSymbol)) {
            error(ctx.type().getStart(), "no such type: " + ctx.type().getText());
            return;
        }

        defineVar(name, (BuiltInTypeSymbol)type);
    }

    @Override
    public void exitVar(CymbolParser.VarContext ctx) {
        String name = ctx.ID().getText();
        Symbol symbol = currentScope.resolve(name);

        if (symbol == null) {
            error(ctx.getStart(), "no such variable: " + name);
        }
    }

    @Override
    public void enterFormalParameter(CymbolParser.FormalParameterContext ctx) {
        String name = ctx.ID().getText();
        Symbol type = currentScope.resolve(ctx.type().getText());

        if (!(type instanceof BuiltInTypeSymbol)) {
            error(ctx.type().getStart(), "no such type: " + ctx.type().getText());
            return;
        }

        defineVar(name, (BuiltInTypeSymbol) type);
    }

    @Override
    public void exitCall(CymbolParser.CallContext ctx) {
        String name = ctx.ID().getText();
        Symbol func = currentScope.resolve(name);

        if (func == null) {
            error(ctx.ID().getSymbol(), "no such function: "+ name);
            return;
        }

        if (func instanceof VariableSymbol) {
            error(ctx.ID().getSymbol(), name + " is not a function");
        }
    }


    VariableSymbol defineVar(String name, BuiltInTypeSymbol type) {
        VariableSymbol variableSymbol = new VariableSymbol(name, type);
        currentScope.define(variableSymbol);

        return variableSymbol;
    }

    void error(Token t, String msg) {
        System.err.printf("line %d:%d %s\n",
                t.getLine(),
                t.getCharPositionInLine(),
                msg
        );
    }
}
