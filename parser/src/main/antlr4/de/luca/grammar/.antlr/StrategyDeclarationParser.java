// Generated from c:/Users/lucar/Desktop/AProVe_Strategy_Visualizer_final/parser/src/main/antlr4/de/luca/grammar/StrategyDeclarationParser.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class StrategyDeclarationParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DECLARE=1, DEFAULTS=2, EQ=3, DOT=4, COMMA=5, LSQBRA=6, RSQBRA=7, DESCRIPTION=8, 
		COMMENT=9, STRING=10, NUMBER=11, WS=12, IDENT=13;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_qualifiedName = 2, RULE_namePart = 3, 
		RULE_parameterBlock = 4, RULE_parameterContent = 5;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "qualifiedName", "namePart", "parameterBlock", 
			"parameterContent"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'declare'", "'defaults'", "'='", "'.'", "','", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "DECLARE", "DEFAULTS", "EQ", "DOT", "COMMA", "LSQBRA", "RSQBRA", 
			"DESCRIPTION", "COMMENT", "STRING", "NUMBER", "WS", "IDENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "StrategyDeclarationParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public StrategyDeclarationParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(StrategyDeclarationParser.EOF, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(12);
				declaration();
				}
				}
				setState(15); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DECLARE || _la==DESCRIPTION );
			setState(17);
			match(EOF);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode DECLARE() { return getToken(StrategyDeclarationParser.DECLARE, 0); }
		public NamePartContext namePart() {
			return getRuleContext(NamePartContext.class,0);
		}
		public TerminalNode EQ() { return getToken(StrategyDeclarationParser.EQ, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<TerminalNode> DESCRIPTION() { return getTokens(StrategyDeclarationParser.DESCRIPTION); }
		public TerminalNode DESCRIPTION(int i) {
			return getToken(StrategyDeclarationParser.DESCRIPTION, i);
		}
		public TerminalNode DEFAULTS() { return getToken(StrategyDeclarationParser.DEFAULTS, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DESCRIPTION) {
				{
				{
				setState(19);
				match(DESCRIPTION);
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25);
			match(DECLARE);
			setState(26);
			namePart();
			setState(27);
			match(EQ);
			setState(28);
			qualifiedName();
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULTS) {
				{
				setState(29);
				match(DEFAULTS);
				setState(30);
				parameterBlock();
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

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameContext extends ParserRuleContext {
		public List<NamePartContext> namePart() {
			return getRuleContexts(NamePartContext.class);
		}
		public NamePartContext namePart(int i) {
			return getRuleContext(NamePartContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(StrategyDeclarationParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(StrategyDeclarationParser.DOT, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			namePart();
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(34);
				match(DOT);
				setState(35);
				namePart();
				}
				}
				setState(40);
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

	@SuppressWarnings("CheckReturnValue")
	public static class NamePartContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(StrategyDeclarationParser.IDENT, 0); }
		public NamePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namePart; }
	}

	public final NamePartContext namePart() throws RecognitionException {
		NamePartContext _localctx = new NamePartContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_namePart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(IDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterBlockContext extends ParserRuleContext {
		public TerminalNode LSQBRA() { return getToken(StrategyDeclarationParser.LSQBRA, 0); }
		public TerminalNode RSQBRA() { return getToken(StrategyDeclarationParser.RSQBRA, 0); }
		public ParameterContentContext parameterContent() {
			return getRuleContext(ParameterContentContext.class,0);
		}
		public ParameterBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterBlock; }
	}

	public final ParameterBlockContext parameterBlock() throws RecognitionException {
		ParameterBlockContext _localctx = new ParameterBlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_parameterBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(LSQBRA);
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 16254L) != 0)) {
				{
				setState(44);
				parameterContent();
				}
			}

			setState(47);
			match(RSQBRA);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterContentContext extends ParserRuleContext {
		public List<ParameterBlockContext> parameterBlock() {
			return getRuleContexts(ParameterBlockContext.class);
		}
		public ParameterBlockContext parameterBlock(int i) {
			return getRuleContext(ParameterBlockContext.class,i);
		}
		public List<TerminalNode> LSQBRA() { return getTokens(StrategyDeclarationParser.LSQBRA); }
		public TerminalNode LSQBRA(int i) {
			return getToken(StrategyDeclarationParser.LSQBRA, i);
		}
		public List<TerminalNode> RSQBRA() { return getTokens(StrategyDeclarationParser.RSQBRA); }
		public TerminalNode RSQBRA(int i) {
			return getToken(StrategyDeclarationParser.RSQBRA, i);
		}
		public ParameterContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterContent; }
	}

	public final ParameterContentContext parameterContent() throws RecognitionException {
		ParameterContentContext _localctx = new ParameterContentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_parameterContent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(51);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LSQBRA:
					{
					setState(49);
					parameterBlock();
					}
					break;
				case DECLARE:
				case DEFAULTS:
				case EQ:
				case DOT:
				case COMMA:
				case DESCRIPTION:
				case COMMENT:
				case STRING:
				case NUMBER:
				case WS:
				case IDENT:
					{
					setState(50);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==LSQBRA || _la==RSQBRA) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(53); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 16254L) != 0) );
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
		"\u0004\u0001\r8\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0001\u0000\u0004\u0000\u000e\b\u0000\u000b\u0000\f"+
		"\u0000\u000f\u0001\u0000\u0001\u0000\u0001\u0001\u0005\u0001\u0015\b\u0001"+
		"\n\u0001\f\u0001\u0018\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0003\u0001 \b\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0005\u0002%\b\u0002\n\u0002\f\u0002(\t\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0003\u0004.\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0004\u00054\b\u0005\u000b"+
		"\u0005\f\u00055\u0001\u0005\u0000\u0000\u0006\u0000\u0002\u0004\u0006"+
		"\b\n\u0000\u0001\u0001\u0000\u0006\u00078\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0002\u0016\u0001\u0000\u0000\u0000\u0004!\u0001\u0000\u0000\u0000\u0006"+
		")\u0001\u0000\u0000\u0000\b+\u0001\u0000\u0000\u0000\n3\u0001\u0000\u0000"+
		"\u0000\f\u000e\u0003\u0002\u0001\u0000\r\f\u0001\u0000\u0000\u0000\u000e"+
		"\u000f\u0001\u0000\u0000\u0000\u000f\r\u0001\u0000\u0000\u0000\u000f\u0010"+
		"\u0001\u0000\u0000\u0000\u0010\u0011\u0001\u0000\u0000\u0000\u0011\u0012"+
		"\u0005\u0000\u0000\u0001\u0012\u0001\u0001\u0000\u0000\u0000\u0013\u0015"+
		"\u0005\b\u0000\u0000\u0014\u0013\u0001\u0000\u0000\u0000\u0015\u0018\u0001"+
		"\u0000\u0000\u0000\u0016\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001"+
		"\u0000\u0000\u0000\u0017\u0019\u0001\u0000\u0000\u0000\u0018\u0016\u0001"+
		"\u0000\u0000\u0000\u0019\u001a\u0005\u0001\u0000\u0000\u001a\u001b\u0003"+
		"\u0006\u0003\u0000\u001b\u001c\u0005\u0003\u0000\u0000\u001c\u001f\u0003"+
		"\u0004\u0002\u0000\u001d\u001e\u0005\u0002\u0000\u0000\u001e \u0003\b"+
		"\u0004\u0000\u001f\u001d\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000"+
		"\u0000 \u0003\u0001\u0000\u0000\u0000!&\u0003\u0006\u0003\u0000\"#\u0005"+
		"\u0004\u0000\u0000#%\u0003\u0006\u0003\u0000$\"\u0001\u0000\u0000\u0000"+
		"%(\u0001\u0000\u0000\u0000&$\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000"+
		"\u0000\'\u0005\u0001\u0000\u0000\u0000(&\u0001\u0000\u0000\u0000)*\u0005"+
		"\r\u0000\u0000*\u0007\u0001\u0000\u0000\u0000+-\u0005\u0006\u0000\u0000"+
		",.\u0003\n\u0005\u0000-,\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000"+
		"./\u0001\u0000\u0000\u0000/0\u0005\u0007\u0000\u00000\t\u0001\u0000\u0000"+
		"\u000014\u0003\b\u0004\u000024\b\u0000\u0000\u000031\u0001\u0000\u0000"+
		"\u000032\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u000053\u0001\u0000"+
		"\u0000\u000056\u0001\u0000\u0000\u00006\u000b\u0001\u0000\u0000\u0000"+
		"\u0007\u000f\u0016\u001f&-35";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}