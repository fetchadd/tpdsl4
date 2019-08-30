// Generated from VecMath.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link VecMathParser}.
 */
public interface VecMathListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link VecMathParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(VecMathParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link VecMathParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(VecMathParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link VecMathParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(VecMathParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link VecMathParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(VecMathParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link VecMathParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(VecMathParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link VecMathParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(VecMathParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link VecMathParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(VecMathParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link VecMathParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(VecMathParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link VecMathParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(VecMathParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link VecMathParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(VecMathParser.PrimaryContext ctx);
}