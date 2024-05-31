// Generated from C:/work/intelliJ_ws/sandbox/antlrworks/src/main/antlr4/jam/sandbox/grammar\ODataOrderby.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ODataOrderbyParser}.
 */
public interface ODataOrderbyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ODataOrderbyParser#orderbyItem}.
	 * @param ctx the parse tree
	 */
	void enterOrderbyItem(ODataOrderbyParser.OrderbyItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ODataOrderbyParser#orderbyItem}.
	 * @param ctx the parse tree
	 */
	void exitOrderbyItem(ODataOrderbyParser.OrderbyItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ODataOrderbyParser#orderbyExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrderbyExpression(ODataOrderbyParser.OrderbyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ODataOrderbyParser#orderbyExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrderbyExpression(ODataOrderbyParser.OrderbyExpressionContext ctx);
}