package jam.sandbox;

import jam.sandbox.grammar.ODataOrderbyLexer;
import jam.sandbox.grammar.ODataOrderbyParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I076097 on 12/3/2016
 */
public class OrderbyTest {

    public static void main(final String[] args) {
        parseOrderBy("abc, aa desc, bx asc");
        // parseOrderBy("abc desc aa, aa asc, aa");
    }

    private static void parseOrderBy(final String orderby) {
        final List<Pair<String, String>> orderByItems = new ArrayList<>();
        final ODataOrderbyLexer oDataOrderbyLexer = new ODataOrderbyLexer(new ANTLRInputStream(orderby));
        final ODataOrderbyParser oDataOrderbyParser = new ODataOrderbyParser(new CommonTokenStream(oDataOrderbyLexer));

        final BaseErrorListener errorListener = new BaseErrorListener() {
            @Override
            public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine, final String msg, final RecognitionException e) {
                super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
                System.out.println("Error " + msg);
            }
        };

        oDataOrderbyParser.addErrorListener(errorListener);

        final ODataOrderbyParser.OrderbyExpressionContext orderbyExpressionContext = oDataOrderbyParser.orderbyExpression();
        for (final ODataOrderbyParser.OrderbyItemContext itemContext : orderbyExpressionContext.orderbyItem()) {
            final String orderByClause;
            if (itemContext.getChildCount() > 1) { // asc / desc is given
                orderByClause = itemContext.getChild(1).getText().toLowerCase();
            } else {
                orderByClause = "asc";
            }
            orderByItems.add(new Pair<String, String>(itemContext.ID().getText(), orderByClause));
        }


        System.out.println(orderByItems);
    }

}
