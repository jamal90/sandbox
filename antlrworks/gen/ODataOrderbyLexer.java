// Generated from C:/work/intelliJ_ws/sandbox/antlrworks/src/main/antlr4/jam/sandbox/grammar\ODataOrderby.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ODataOrderbyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ID=1, COMMA=2, ASC=3, DESC=4, NEWLINE=5, WS=6;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"ID", "COMMA", "ASC", "DESC", "NEWLINE", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ID", "COMMA", "ASC", "DESC", "NEWLINE", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ODataOrderbyLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ODataOrderby.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\b\67\b\1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\6\2\22\n\2\r\2\16\2\23"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4\36\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\5\5(\n\5\3\6\5\6+\n\6\3\6\3\6\3\6\3\6\3\7\6\7\62\n\7\r\7\16\7\63"+
		"\3\7\3\7\2\2\b\3\3\5\4\7\5\t\6\13\7\r\b\3\2\5\5\2C\\aac|\6\2\62;C\\aa"+
		"c|\5\2\13\f\17\17\"\";\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\3\17\3\2\2\2\5\25\3\2\2\2\7\35\3\2\2\2\t\'\3"+
		"\2\2\2\13*\3\2\2\2\r\61\3\2\2\2\17\21\t\2\2\2\20\22\t\3\2\2\21\20\3\2"+
		"\2\2\22\23\3\2\2\2\23\21\3\2\2\2\23\24\3\2\2\2\24\4\3\2\2\2\25\26\7.\2"+
		"\2\26\6\3\2\2\2\27\30\7c\2\2\30\31\7u\2\2\31\36\7e\2\2\32\33\7C\2\2\33"+
		"\34\7U\2\2\34\36\7E\2\2\35\27\3\2\2\2\35\32\3\2\2\2\36\b\3\2\2\2\37 \7"+
		"f\2\2 !\7g\2\2!\"\7u\2\2\"(\7e\2\2#$\7F\2\2$%\7G\2\2%&\7U\2\2&(\7E\2\2"+
		"\'\37\3\2\2\2\'#\3\2\2\2(\n\3\2\2\2)+\7\17\2\2*)\3\2\2\2*+\3\2\2\2+,\3"+
		"\2\2\2,-\7\f\2\2-.\3\2\2\2./\b\6\2\2/\f\3\2\2\2\60\62\t\4\2\2\61\60\3"+
		"\2\2\2\62\63\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\65\3\2\2\2\65\66\b"+
		"\7\2\2\66\16\3\2\2\2\b\2\23\35\'*\63\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}