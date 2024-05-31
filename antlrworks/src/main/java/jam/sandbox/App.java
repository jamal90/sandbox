package jam.sandbox;

import jam.sandbox.grammar.DrinkLexer;
import jam.sandbox.grammar.DrinkParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Hello world!
 */
public class App {
    public static void main(final String[] args) {

        final String inputString = "a cup of tea";

        // lex it and get the token stream
        final DrinkLexer drinkLexer = new DrinkLexer(new ANTLRInputStream(inputString));

        // give the token stream to the parser
        final DrinkParser drinkParser = new DrinkParser(new CommonTokenStream(drinkLexer));

        // specify the entry point for the parser rules
        final DrinkParser.DrinkSentenceContext drinkSentenceContext = drinkParser.drinkSentence();

        final DrinkParser.DrinkContext drink = drinkSentenceContext.drink();
        System.out.println(drink.getText());
    }
}
