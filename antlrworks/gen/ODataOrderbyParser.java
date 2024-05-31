// Generated from C:/work/intelliJ_ws/sandbox/antlrworks/src/main/antlr4/jam/sandbox/grammar\ODataOrderby.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ODataOrderbyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ID=1, COMMA=2, ASC=3, DESC=4, NEWLINE=5, WS=6;
	public static final int
		RULE_orderbyItem = 0, RULE_orderbyExpression = 1;
	public static final String[] ruleNames = {
		"orderbyItem", "orderbyExpression"
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

	@Override
	public String getGrammarFileName() { return "ODataOrderby.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ODataOrderbyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class OrderbyItemContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ODataOrderbyParser.ID, 0); }
		public TerminalNode ASC() { return getToken(ODataOrderbyParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(ODataOrderbyParser.DESC, 0); }
		public OrderbyItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderbyItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ODataOrderbyListener ) ((ODataOrderbyListener)listener).enterOrderbyItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ODataOrderbyListener ) ((ODataOrderbyListener)listener).exitOrderbyItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ODataOrderbyVisitor ) return ((ODataOrderbyVisitor<? extends T>)visitor).visitOrderbyItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderbyItemContext orderbyItem() throws RecognitionException {
		OrderbyItemContext _localctx = new OrderbyItemContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_orderbyItem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(4);
			match(ID);
			setState(6);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC) {
				{
				setState(5);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderbyExpressionContext extends ParserRuleContext {
		public List<OrderbyItemContext> orderbyItem() {
			return getRuleContexts(OrderbyItemContext.class);
		}
		public OrderbyItemContext orderbyItem(int i) {
			return getRuleContext(OrderbyItemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ODataOrderbyParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ODataOrderbyParser.COMMA, i);
		}
		public OrderbyExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderbyExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ODataOrderbyListener ) ((ODataOrderbyListener)listener).enterOrderbyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ODataOrderbyListener ) ((ODataOrderbyListener)listener).exitOrderbyExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ODataOrderbyVisitor ) return ((ODataOrderbyVisitor<? extends T>)visitor).visitOrderbyExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderbyExpressionContext orderbyExpression() throws RecognitionException {
		OrderbyExpressionContext _localctx = new OrderbyExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_orderbyExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			orderbyItem();
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(9);
				match(COMMA);
				setState(10);
				orderbyItem();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\b\23\4\2\t\2\4\3"+
		"\t\3\3\2\3\2\5\2\t\n\2\3\3\3\3\3\3\7\3\16\n\3\f\3\16\3\21\13\3\3\3\2\2"+
		"\4\2\4\2\3\3\2\5\6\22\2\6\3\2\2\2\4\n\3\2\2\2\6\b\7\3\2\2\7\t\t\2\2\2"+
		"\b\7\3\2\2\2\b\t\3\2\2\2\t\3\3\2\2\2\n\17\5\2\2\2\13\f\7\4\2\2\f\16\5"+
		"\2\2\2\r\13\3\2\2\2\16\21\3\2\2\2\17\r\3\2\2\2\17\20\3\2\2\2\20\5\3\2"+
		"\2\2\21\17\3\2\2\2\4\b\17";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}