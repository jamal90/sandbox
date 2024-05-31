// Generated from C:/work/intelliJ_ws/sandbox/antlrworks/src/main/antlr4/jam/sandbox/grammar\ODataOrderby.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ODataOrderbyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ODataOrderbyVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ODataOrderbyParser#orderbyItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderbyItem(ODataOrderbyParser.OrderbyItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link ODataOrderbyParser#orderbyExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderbyExpression(ODataOrderbyParser.OrderbyExpressionContext ctx);
}