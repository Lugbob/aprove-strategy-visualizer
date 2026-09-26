// Generated from c:/Users/lucar/Desktop/AProVe_Strategy_Visualizer_final/parser/src/main/antlr4/de/luca/grammar/StrategyParser.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class StrategyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		REPEAT=1, MAYBE=2, FIRST=3, DELAY=4, IF=5, COMBINE=6, TIMER=7, ANYK=8, 
		ANY=9, SOLVE=10, PROVE=11, DISPROVE=12, ITERATE=13, EQ=14, DOT=15, DECLARE=16, 
		COLON=17, SEMICOLON=18, COMMA=19, LBRA=20, RBRA=21, LSQBRA=22, RSQBRA=23, 
		STAR=24, NUMBER=25, DESCRIPTION=26, COMMENT=27, LCNAME=28, UCNAME=29, 
		WS=30, STRING=31, BACKSLASH=32;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_qualifiedName = 2, RULE_namePart = 3, 
		RULE_equation = 4, RULE_strategyTerm = 5, RULE_strategyTermList = 6, RULE_sequence = 7, 
		RULE_timer = 8, RULE_ifExpr = 9, RULE_combine = 10, RULE_solveProve = 11, 
		RULE_strategyAtom = 12, RULE_repeat = 13, RULE_iterate = 14, RULE_upperBound = 15, 
		RULE_processor = 16, RULE_parameterBlock = 17, RULE_parameterContent = 18, 
		RULE_choice = 19, RULE_maybe = 20, RULE_delay = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "qualifiedName", "namePart", "equation", "strategyTerm", 
			"strategyTermList", "sequence", "timer", "ifExpr", "combine", "solveProve", 
			"strategyAtom", "repeat", "iterate", "upperBound", "processor", "parameterBlock", 
			"parameterContent", "choice", "maybe", "delay"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'Maybe'", "'First'", null, "'If'", null, null, "'AnyK'", 
			"'Any'", "'Solve'", "'Prove'", "'DISPROVE'", null, "'='", "'.'", "'declare'", 
			"':'", "';'", "','", "'('", "')'", "'['", "']'", "'*'", null, null, null, 
			null, null, null, null, "'\\'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "REPEAT", "MAYBE", "FIRST", "DELAY", "IF", "COMBINE", "TIMER", 
			"ANYK", "ANY", "SOLVE", "PROVE", "DISPROVE", "ITERATE", "EQ", "DOT", 
			"DECLARE", "COLON", "SEMICOLON", "COMMA", "LBRA", "RBRA", "LSQBRA", "RSQBRA", 
			"STAR", "NUMBER", "DESCRIPTION", "COMMENT", "LCNAME", "UCNAME", "WS", 
			"STRING", "BACKSLASH"
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
	public String getGrammarFileName() { return "StrategyParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public StrategyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(StrategyParser.EOF, 0); }
		public List<EquationContext> equation() {
			return getRuleContexts(EquationContext.class);
		}
		public EquationContext equation(int i) {
			return getRuleContext(EquationContext.class,i);
		}
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
			setState(46); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(46);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(44);
					equation();
					}
					break;
				case 2:
					{
					setState(45);
					declaration();
					}
					break;
				}
				}
				setState(48); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 335609856L) != 0) );
			setState(50);
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
		public TerminalNode DECLARE() { return getToken(StrategyParser.DECLARE, 0); }
		public NamePartContext namePart() {
			return getRuleContext(NamePartContext.class,0);
		}
		public TerminalNode EQ() { return getToken(StrategyParser.EQ, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<TerminalNode> DESCRIPTION() { return getTokens(StrategyParser.DESCRIPTION); }
		public TerminalNode DESCRIPTION(int i) {
			return getToken(StrategyParser.DESCRIPTION, i);
		}
		public TerminalNode LCNAME() { return getToken(StrategyParser.LCNAME, 0); }
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
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DESCRIPTION) {
				{
				{
				setState(52);
				match(DESCRIPTION);
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(58);
			match(DECLARE);
			setState(59);
			namePart();
			setState(60);
			match(EQ);
			setState(61);
			qualifiedName();
			setState(64);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(62);
				match(LCNAME);
				setState(63);
				parameterBlock();
				}
				break;
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
		public List<TerminalNode> DOT() { return getTokens(StrategyParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(StrategyParser.DOT, i);
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
			setState(66);
			namePart();
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(67);
				match(DOT);
				setState(68);
				namePart();
				}
				}
				setState(73);
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
		public TerminalNode LCNAME() { return getToken(StrategyParser.LCNAME, 0); }
		public TerminalNode UCNAME() { return getToken(StrategyParser.UCNAME, 0); }
		public TerminalNode REPEAT() { return getToken(StrategyParser.REPEAT, 0); }
		public TerminalNode MAYBE() { return getToken(StrategyParser.MAYBE, 0); }
		public TerminalNode FIRST() { return getToken(StrategyParser.FIRST, 0); }
		public TerminalNode DELAY() { return getToken(StrategyParser.DELAY, 0); }
		public TerminalNode IF() { return getToken(StrategyParser.IF, 0); }
		public TerminalNode COMBINE() { return getToken(StrategyParser.COMBINE, 0); }
		public TerminalNode TIMER() { return getToken(StrategyParser.TIMER, 0); }
		public TerminalNode ANY() { return getToken(StrategyParser.ANY, 0); }
		public TerminalNode ANYK() { return getToken(StrategyParser.ANYK, 0); }
		public TerminalNode SOLVE() { return getToken(StrategyParser.SOLVE, 0); }
		public TerminalNode PROVE() { return getToken(StrategyParser.PROVE, 0); }
		public TerminalNode ITERATE() { return getToken(StrategyParser.ITERATE, 0); }
		public NamePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namePart; }
	}

	public final NamePartContext namePart() throws RecognitionException {
		NamePartContext _localctx = new NamePartContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_namePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 805318654L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
	public static class EquationContext extends ParserRuleContext {
		public TerminalNode LCNAME() { return getToken(StrategyParser.LCNAME, 0); }
		public TerminalNode EQ() { return getToken(StrategyParser.EQ, 0); }
		public StrategyTermContext strategyTerm() {
			return getRuleContext(StrategyTermContext.class,0);
		}
		public List<TerminalNode> DESCRIPTION() { return getTokens(StrategyParser.DESCRIPTION); }
		public TerminalNode DESCRIPTION(int i) {
			return getToken(StrategyParser.DESCRIPTION, i);
		}
		public EquationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equation; }
	}

	public final EquationContext equation() throws RecognitionException {
		EquationContext _localctx = new EquationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_equation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DESCRIPTION) {
				{
				{
				setState(76);
				match(DESCRIPTION);
				}
				}
				setState(81);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(82);
			match(LCNAME);
			setState(83);
			match(EQ);
			setState(84);
			strategyTerm();
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
	public static class StrategyTermContext extends ParserRuleContext {
		public SequenceContext sequence() {
			return getRuleContext(SequenceContext.class,0);
		}
		public StrategyTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strategyTerm; }
	}

	public final StrategyTermContext strategyTerm() throws RecognitionException {
		StrategyTermContext _localctx = new StrategyTermContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_strategyTerm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			sequence();
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
	public static class StrategyTermListContext extends ParserRuleContext {
		public List<StrategyTermContext> strategyTerm() {
			return getRuleContexts(StrategyTermContext.class);
		}
		public StrategyTermContext strategyTerm(int i) {
			return getRuleContext(StrategyTermContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(StrategyParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StrategyParser.COMMA, i);
		}
		public StrategyTermListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strategyTermList; }
	}

	public final StrategyTermListContext strategyTermList() throws RecognitionException {
		StrategyTermListContext _localctx = new StrategyTermListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_strategyTermList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			strategyTerm();
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(89);
				match(COMMA);
				setState(90);
				strategyTerm();
				}
				}
				setState(95);
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
	public static class SequenceContext extends ParserRuleContext {
		public List<StrategyAtomContext> strategyAtom() {
			return getRuleContexts(StrategyAtomContext.class);
		}
		public StrategyAtomContext strategyAtom(int i) {
			return getRuleContext(StrategyAtomContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(StrategyParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(StrategyParser.COLON, i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(StrategyParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(StrategyParser.SEMICOLON, i);
		}
		public SequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequence; }
	}

	public final SequenceContext sequence() throws RecognitionException {
		SequenceContext _localctx = new SequenceContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_sequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			strategyAtom();
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLON || _la==SEMICOLON) {
				{
				{
				setState(97);
				_la = _input.LA(1);
				if ( !(_la==COLON || _la==SEMICOLON) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(98);
				strategyAtom();
				}
				}
				setState(103);
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
	public static class TimerContext extends ParserRuleContext {
		public TerminalNode TIMER() { return getToken(StrategyParser.TIMER, 0); }
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public TerminalNode NUMBER() { return getToken(StrategyParser.NUMBER, 0); }
		public TerminalNode COMMA() { return getToken(StrategyParser.COMMA, 0); }
		public StrategyTermContext strategyTerm() {
			return getRuleContext(StrategyTermContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public TimerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timer; }
	}

	public final TimerContext timer() throws RecognitionException {
		TimerContext _localctx = new TimerContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_timer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(TIMER);
			setState(105);
			match(LBRA);
			setState(106);
			match(NUMBER);
			setState(107);
			match(COMMA);
			setState(108);
			strategyTerm();
			setState(109);
			match(RBRA);
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
	public static class IfExprContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(StrategyParser.IF, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public List<StrategyTermContext> strategyTerm() {
			return getRuleContexts(StrategyTermContext.class);
		}
		public StrategyTermContext strategyTerm(int i) {
			return getRuleContext(StrategyTermContext.class,i);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public TerminalNode COMMA() { return getToken(StrategyParser.COMMA, 0); }
		public IfExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifExpr; }
	}

	public final IfExprContext ifExpr() throws RecognitionException {
		IfExprContext _localctx = new IfExprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ifExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(IF);
			setState(112);
			parameterBlock();
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRA) {
				{
				setState(113);
				match(LBRA);
				setState(114);
				strategyTerm();
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(115);
					match(COMMA);
					setState(116);
					strategyTerm();
					}
				}

				setState(119);
				match(RBRA);
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
	public static class CombineContext extends ParserRuleContext {
		public TerminalNode COMBINE() { return getToken(StrategyParser.COMBINE, 0); }
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public StrategyTermListContext strategyTermList() {
			return getRuleContext(StrategyTermListContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public CombineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_combine; }
	}

	public final CombineContext combine() throws RecognitionException {
		CombineContext _localctx = new CombineContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_combine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(COMBINE);
			setState(124);
			match(LBRA);
			setState(125);
			strategyTermList();
			setState(126);
			match(RBRA);
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
	public static class SolveProveContext extends ParserRuleContext {
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public StrategyTermContext strategyTerm() {
			return getRuleContext(StrategyTermContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public TerminalNode SOLVE() { return getToken(StrategyParser.SOLVE, 0); }
		public TerminalNode PROVE() { return getToken(StrategyParser.PROVE, 0); }
		public TerminalNode DISPROVE() { return getToken(StrategyParser.DISPROVE, 0); }
		public SolveProveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_solveProve; }
	}

	public final SolveProveContext solveProve() throws RecognitionException {
		SolveProveContext _localctx = new SolveProveContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_solveProve);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7168L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(129);
			match(LBRA);
			setState(130);
			strategyTerm();
			setState(131);
			match(RBRA);
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
	public static class StrategyAtomContext extends ParserRuleContext {
		public RepeatContext repeat() {
			return getRuleContext(RepeatContext.class,0);
		}
		public IterateContext iterate() {
			return getRuleContext(IterateContext.class,0);
		}
		public ChoiceContext choice() {
			return getRuleContext(ChoiceContext.class,0);
		}
		public MaybeContext maybe() {
			return getRuleContext(MaybeContext.class,0);
		}
		public ProcessorContext processor() {
			return getRuleContext(ProcessorContext.class,0);
		}
		public TimerContext timer() {
			return getRuleContext(TimerContext.class,0);
		}
		public IfExprContext ifExpr() {
			return getRuleContext(IfExprContext.class,0);
		}
		public CombineContext combine() {
			return getRuleContext(CombineContext.class,0);
		}
		public SolveProveContext solveProve() {
			return getRuleContext(SolveProveContext.class,0);
		}
		public DelayContext delay() {
			return getRuleContext(DelayContext.class,0);
		}
		public TerminalNode LCNAME() { return getToken(StrategyParser.LCNAME, 0); }
		public StrategyAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strategyAtom; }
	}

	public final StrategyAtomContext strategyAtom() throws RecognitionException {
		StrategyAtomContext _localctx = new StrategyAtomContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_strategyAtom);
		try {
			setState(144);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case REPEAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				repeat();
				}
				break;
			case ITERATE:
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				iterate();
				}
				break;
			case FIRST:
			case ANYK:
			case ANY:
				enterOuterAlt(_localctx, 3);
				{
				setState(135);
				choice();
				}
				break;
			case MAYBE:
				enterOuterAlt(_localctx, 4);
				{
				setState(136);
				maybe();
				}
				break;
			case UCNAME:
				enterOuterAlt(_localctx, 5);
				{
				setState(137);
				processor();
				}
				break;
			case TIMER:
				enterOuterAlt(_localctx, 6);
				{
				setState(138);
				timer();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 7);
				{
				setState(139);
				ifExpr();
				}
				break;
			case COMBINE:
				enterOuterAlt(_localctx, 8);
				{
				setState(140);
				combine();
				}
				break;
			case SOLVE:
			case PROVE:
			case DISPROVE:
				enterOuterAlt(_localctx, 9);
				{
				setState(141);
				solveProve();
				}
				break;
			case DELAY:
				enterOuterAlt(_localctx, 10);
				{
				setState(142);
				delay();
				}
				break;
			case LCNAME:
				enterOuterAlt(_localctx, 11);
				{
				setState(143);
				match(LCNAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class RepeatContext extends ParserRuleContext {
		public TerminalNode REPEAT() { return getToken(StrategyParser.REPEAT, 0); }
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public TerminalNode NUMBER() { return getToken(StrategyParser.NUMBER, 0); }
		public List<TerminalNode> COMMA() { return getTokens(StrategyParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StrategyParser.COMMA, i);
		}
		public UpperBoundContext upperBound() {
			return getRuleContext(UpperBoundContext.class,0);
		}
		public StrategyTermContext strategyTerm() {
			return getRuleContext(StrategyTermContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public RepeatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repeat; }
	}

	public final RepeatContext repeat() throws RecognitionException {
		RepeatContext _localctx = new RepeatContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_repeat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(REPEAT);
			setState(147);
			match(LBRA);
			setState(148);
			match(NUMBER);
			setState(149);
			match(COMMA);
			setState(150);
			upperBound();
			setState(151);
			match(COMMA);
			setState(152);
			strategyTerm();
			setState(153);
			match(RBRA);
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
	public static class IterateContext extends ParserRuleContext {
		public TerminalNode ITERATE() { return getToken(StrategyParser.ITERATE, 0); }
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public ProcessorContext processor() {
			return getRuleContext(ProcessorContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public IterateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterate; }
	}

	public final IterateContext iterate() throws RecognitionException {
		IterateContext _localctx = new IterateContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_iterate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(ITERATE);
			setState(156);
			match(LBRA);
			setState(157);
			processor();
			setState(158);
			match(RBRA);
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
	public static class UpperBoundContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(StrategyParser.NUMBER, 0); }
		public TerminalNode STAR() { return getToken(StrategyParser.STAR, 0); }
		public UpperBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_upperBound; }
	}

	public final UpperBoundContext upperBound() throws RecognitionException {
		UpperBoundContext _localctx = new UpperBoundContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_upperBound);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			_la = _input.LA(1);
			if ( !(_la==STAR || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
	public static class ProcessorContext extends ParserRuleContext {
		public TerminalNode UCNAME() { return getToken(StrategyParser.UCNAME, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public ProcessorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_processor; }
	}

	public final ProcessorContext processor() throws RecognitionException {
		ProcessorContext _localctx = new ProcessorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_processor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(UCNAME);
			setState(164);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LSQBRA) {
				{
				setState(163);
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
	public static class ParameterBlockContext extends ParserRuleContext {
		public TerminalNode LSQBRA() { return getToken(StrategyParser.LSQBRA, 0); }
		public TerminalNode RSQBRA() { return getToken(StrategyParser.RSQBRA, 0); }
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
		enterRule(_localctx, 34, RULE_parameterBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(LSQBRA);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8581545982L) != 0)) {
				{
				setState(167);
				parameterContent();
				}
			}

			setState(170);
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
		public List<TerminalNode> LSQBRA() { return getTokens(StrategyParser.LSQBRA); }
		public TerminalNode LSQBRA(int i) {
			return getToken(StrategyParser.LSQBRA, i);
		}
		public List<TerminalNode> RSQBRA() { return getTokens(StrategyParser.RSQBRA); }
		public TerminalNode RSQBRA(int i) {
			return getToken(StrategyParser.RSQBRA, i);
		}
		public ParameterContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterContent; }
	}

	public final ParameterContentContext parameterContent() throws RecognitionException {
		ParameterContentContext _localctx = new ParameterContentContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_parameterContent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(174);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LSQBRA:
					{
					setState(172);
					parameterBlock();
					}
					break;
				case REPEAT:
				case MAYBE:
				case FIRST:
				case DELAY:
				case IF:
				case COMBINE:
				case TIMER:
				case ANYK:
				case ANY:
				case SOLVE:
				case PROVE:
				case DISPROVE:
				case ITERATE:
				case EQ:
				case DOT:
				case DECLARE:
				case COLON:
				case SEMICOLON:
				case COMMA:
				case LBRA:
				case RBRA:
				case STAR:
				case NUMBER:
				case DESCRIPTION:
				case COMMENT:
				case LCNAME:
				case UCNAME:
				case WS:
				case STRING:
				case BACKSLASH:
					{
					setState(173);
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
				setState(176); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 8581545982L) != 0) );
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
	public static class ChoiceContext extends ParserRuleContext {
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public StrategyTermListContext strategyTermList() {
			return getRuleContext(StrategyTermListContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public TerminalNode ANY() { return getToken(StrategyParser.ANY, 0); }
		public TerminalNode FIRST() { return getToken(StrategyParser.FIRST, 0); }
		public TerminalNode ANYK() { return getToken(StrategyParser.ANYK, 0); }
		public TerminalNode NUMBER() { return getToken(StrategyParser.NUMBER, 0); }
		public TerminalNode COMMA() { return getToken(StrategyParser.COMMA, 0); }
		public ChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choice; }
	}

	public final ChoiceContext choice() throws RecognitionException {
		ChoiceContext _localctx = new ChoiceContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_choice);
		int _la;
		try {
			setState(190);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FIRST:
			case ANY:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				_la = _input.LA(1);
				if ( !(_la==FIRST || _la==ANY) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(179);
				match(LBRA);
				setState(180);
				strategyTermList();
				setState(181);
				match(RBRA);
				}
				break;
			case ANYK:
				enterOuterAlt(_localctx, 2);
				{
				setState(183);
				match(ANYK);
				setState(184);
				match(LBRA);
				setState(185);
				match(NUMBER);
				setState(186);
				match(COMMA);
				setState(187);
				strategyTermList();
				setState(188);
				match(RBRA);
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class MaybeContext extends ParserRuleContext {
		public TerminalNode MAYBE() { return getToken(StrategyParser.MAYBE, 0); }
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public StrategyTermContext strategyTerm() {
			return getRuleContext(StrategyTermContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public MaybeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_maybe; }
	}

	public final MaybeContext maybe() throws RecognitionException {
		MaybeContext _localctx = new MaybeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_maybe);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(MAYBE);
			setState(193);
			match(LBRA);
			setState(194);
			strategyTerm();
			setState(195);
			match(RBRA);
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
	public static class DelayContext extends ParserRuleContext {
		public TerminalNode DELAY() { return getToken(StrategyParser.DELAY, 0); }
		public TerminalNode LBRA() { return getToken(StrategyParser.LBRA, 0); }
		public TerminalNode NUMBER() { return getToken(StrategyParser.NUMBER, 0); }
		public TerminalNode COMMA() { return getToken(StrategyParser.COMMA, 0); }
		public StrategyTermListContext strategyTermList() {
			return getRuleContext(StrategyTermListContext.class,0);
		}
		public TerminalNode RBRA() { return getToken(StrategyParser.RBRA, 0); }
		public DelayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delay; }
	}

	public final DelayContext delay() throws RecognitionException {
		DelayContext _localctx = new DelayContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_delay);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(DELAY);
			setState(198);
			match(LBRA);
			setState(199);
			match(NUMBER);
			setState(200);
			match(COMMA);
			setState(201);
			strategyTermList();
			setState(202);
			match(RBRA);
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
		"\u0004\u0001 \u00cd\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0001\u0000\u0001\u0000\u0004\u0000/\b\u0000\u000b\u0000\f\u00000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0005\u00016\b\u0001\n\u0001\f\u00019\t"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0003\u0001A\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0005"+
		"\u0002F\b\u0002\n\u0002\f\u0002I\t\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0005\u0004N\b\u0004\n\u0004\f\u0004Q\t\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006\\\b\u0006\n\u0006\f\u0006_\t\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0005\u0007d\b\u0007\n\u0007\f\u0007g\t"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\tv\b\t\u0001\t\u0001\t"+
		"\u0003\tz\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u0091"+
		"\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0003\u0010\u00a5\b\u0010\u0001\u0011"+
		"\u0001\u0011\u0003\u0011\u00a9\b\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0004\u0012\u00af\b\u0012\u000b\u0012\f\u0012\u00b0\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u00bf\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0000\u0000\u0016\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*\u0000"+
		"\u0006\u0003\u0000\u0001\u000b\r\r\u001c\u001d\u0001\u0000\u0011\u0012"+
		"\u0001\u0000\n\f\u0001\u0000\u0018\u0019\u0001\u0000\u0016\u0017\u0002"+
		"\u0000\u0003\u0003\t\t\u00cf\u0000.\u0001\u0000\u0000\u0000\u00027\u0001"+
		"\u0000\u0000\u0000\u0004B\u0001\u0000\u0000\u0000\u0006J\u0001\u0000\u0000"+
		"\u0000\bO\u0001\u0000\u0000\u0000\nV\u0001\u0000\u0000\u0000\fX\u0001"+
		"\u0000\u0000\u0000\u000e`\u0001\u0000\u0000\u0000\u0010h\u0001\u0000\u0000"+
		"\u0000\u0012o\u0001\u0000\u0000\u0000\u0014{\u0001\u0000\u0000\u0000\u0016"+
		"\u0080\u0001\u0000\u0000\u0000\u0018\u0090\u0001\u0000\u0000\u0000\u001a"+
		"\u0092\u0001\u0000\u0000\u0000\u001c\u009b\u0001\u0000\u0000\u0000\u001e"+
		"\u00a0\u0001\u0000\u0000\u0000 \u00a2\u0001\u0000\u0000\u0000\"\u00a6"+
		"\u0001\u0000\u0000\u0000$\u00ae\u0001\u0000\u0000\u0000&\u00be\u0001\u0000"+
		"\u0000\u0000(\u00c0\u0001\u0000\u0000\u0000*\u00c5\u0001\u0000\u0000\u0000"+
		",/\u0003\b\u0004\u0000-/\u0003\u0002\u0001\u0000.,\u0001\u0000\u0000\u0000"+
		".-\u0001\u0000\u0000\u0000/0\u0001\u0000\u0000\u00000.\u0001\u0000\u0000"+
		"\u000001\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u000023\u0005\u0000"+
		"\u0000\u00013\u0001\u0001\u0000\u0000\u000046\u0005\u001a\u0000\u0000"+
		"54\u0001\u0000\u0000\u000069\u0001\u0000\u0000\u000075\u0001\u0000\u0000"+
		"\u000078\u0001\u0000\u0000\u00008:\u0001\u0000\u0000\u000097\u0001\u0000"+
		"\u0000\u0000:;\u0005\u0010\u0000\u0000;<\u0003\u0006\u0003\u0000<=\u0005"+
		"\u000e\u0000\u0000=@\u0003\u0004\u0002\u0000>?\u0005\u001c\u0000\u0000"+
		"?A\u0003\"\u0011\u0000@>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000"+
		"A\u0003\u0001\u0000\u0000\u0000BG\u0003\u0006\u0003\u0000CD\u0005\u000f"+
		"\u0000\u0000DF\u0003\u0006\u0003\u0000EC\u0001\u0000\u0000\u0000FI\u0001"+
		"\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000"+
		"H\u0005\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000JK\u0007\u0000"+
		"\u0000\u0000K\u0007\u0001\u0000\u0000\u0000LN\u0005\u001a\u0000\u0000"+
		"ML\u0001\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000"+
		"\u0000OP\u0001\u0000\u0000\u0000PR\u0001\u0000\u0000\u0000QO\u0001\u0000"+
		"\u0000\u0000RS\u0005\u001c\u0000\u0000ST\u0005\u000e\u0000\u0000TU\u0003"+
		"\n\u0005\u0000U\t\u0001\u0000\u0000\u0000VW\u0003\u000e\u0007\u0000W\u000b"+
		"\u0001\u0000\u0000\u0000X]\u0003\n\u0005\u0000YZ\u0005\u0013\u0000\u0000"+
		"Z\\\u0003\n\u0005\u0000[Y\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000"+
		"\u0000][\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000^\r\u0001\u0000"+
		"\u0000\u0000_]\u0001\u0000\u0000\u0000`e\u0003\u0018\f\u0000ab\u0007\u0001"+
		"\u0000\u0000bd\u0003\u0018\f\u0000ca\u0001\u0000\u0000\u0000dg\u0001\u0000"+
		"\u0000\u0000ec\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000f\u000f"+
		"\u0001\u0000\u0000\u0000ge\u0001\u0000\u0000\u0000hi\u0005\u0007\u0000"+
		"\u0000ij\u0005\u0014\u0000\u0000jk\u0005\u0019\u0000\u0000kl\u0005\u0013"+
		"\u0000\u0000lm\u0003\n\u0005\u0000mn\u0005\u0015\u0000\u0000n\u0011\u0001"+
		"\u0000\u0000\u0000op\u0005\u0005\u0000\u0000py\u0003\"\u0011\u0000qr\u0005"+
		"\u0014\u0000\u0000ru\u0003\n\u0005\u0000st\u0005\u0013\u0000\u0000tv\u0003"+
		"\n\u0005\u0000us\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vw\u0001"+
		"\u0000\u0000\u0000wx\u0005\u0015\u0000\u0000xz\u0001\u0000\u0000\u0000"+
		"yq\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z\u0013\u0001\u0000"+
		"\u0000\u0000{|\u0005\u0006\u0000\u0000|}\u0005\u0014\u0000\u0000}~\u0003"+
		"\f\u0006\u0000~\u007f\u0005\u0015\u0000\u0000\u007f\u0015\u0001\u0000"+
		"\u0000\u0000\u0080\u0081\u0007\u0002\u0000\u0000\u0081\u0082\u0005\u0014"+
		"\u0000\u0000\u0082\u0083\u0003\n\u0005\u0000\u0083\u0084\u0005\u0015\u0000"+
		"\u0000\u0084\u0017\u0001\u0000\u0000\u0000\u0085\u0091\u0003\u001a\r\u0000"+
		"\u0086\u0091\u0003\u001c\u000e\u0000\u0087\u0091\u0003&\u0013\u0000\u0088"+
		"\u0091\u0003(\u0014\u0000\u0089\u0091\u0003 \u0010\u0000\u008a\u0091\u0003"+
		"\u0010\b\u0000\u008b\u0091\u0003\u0012\t\u0000\u008c\u0091\u0003\u0014"+
		"\n\u0000\u008d\u0091\u0003\u0016\u000b\u0000\u008e\u0091\u0003*\u0015"+
		"\u0000\u008f\u0091\u0005\u001c\u0000\u0000\u0090\u0085\u0001\u0000\u0000"+
		"\u0000\u0090\u0086\u0001\u0000\u0000\u0000\u0090\u0087\u0001\u0000\u0000"+
		"\u0000\u0090\u0088\u0001\u0000\u0000\u0000\u0090\u0089\u0001\u0000\u0000"+
		"\u0000\u0090\u008a\u0001\u0000\u0000\u0000\u0090\u008b\u0001\u0000\u0000"+
		"\u0000\u0090\u008c\u0001\u0000\u0000\u0000\u0090\u008d\u0001\u0000\u0000"+
		"\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0090\u008f\u0001\u0000\u0000"+
		"\u0000\u0091\u0019\u0001\u0000\u0000\u0000\u0092\u0093\u0005\u0001\u0000"+
		"\u0000\u0093\u0094\u0005\u0014\u0000\u0000\u0094\u0095\u0005\u0019\u0000"+
		"\u0000\u0095\u0096\u0005\u0013\u0000\u0000\u0096\u0097\u0003\u001e\u000f"+
		"\u0000\u0097\u0098\u0005\u0013\u0000\u0000\u0098\u0099\u0003\n\u0005\u0000"+
		"\u0099\u009a\u0005\u0015\u0000\u0000\u009a\u001b\u0001\u0000\u0000\u0000"+
		"\u009b\u009c\u0005\r\u0000\u0000\u009c\u009d\u0005\u0014\u0000\u0000\u009d"+
		"\u009e\u0003 \u0010\u0000\u009e\u009f\u0005\u0015\u0000\u0000\u009f\u001d"+
		"\u0001\u0000\u0000\u0000\u00a0\u00a1\u0007\u0003\u0000\u0000\u00a1\u001f"+
		"\u0001\u0000\u0000\u0000\u00a2\u00a4\u0005\u001d\u0000\u0000\u00a3\u00a5"+
		"\u0003\"\u0011\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001"+
		"\u0000\u0000\u0000\u00a5!\u0001\u0000\u0000\u0000\u00a6\u00a8\u0005\u0016"+
		"\u0000\u0000\u00a7\u00a9\u0003$\u0012\u0000\u00a8\u00a7\u0001\u0000\u0000"+
		"\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000"+
		"\u0000\u00aa\u00ab\u0005\u0017\u0000\u0000\u00ab#\u0001\u0000\u0000\u0000"+
		"\u00ac\u00af\u0003\"\u0011\u0000\u00ad\u00af\b\u0004\u0000\u0000\u00ae"+
		"\u00ac\u0001\u0000\u0000\u0000\u00ae\u00ad\u0001\u0000\u0000\u0000\u00af"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b0"+
		"\u00b1\u0001\u0000\u0000\u0000\u00b1%\u0001\u0000\u0000\u0000\u00b2\u00b3"+
		"\u0007\u0005\u0000\u0000\u00b3\u00b4\u0005\u0014\u0000\u0000\u00b4\u00b5"+
		"\u0003\f\u0006\u0000\u00b5\u00b6\u0005\u0015\u0000\u0000\u00b6\u00bf\u0001"+
		"\u0000\u0000\u0000\u00b7\u00b8\u0005\b\u0000\u0000\u00b8\u00b9\u0005\u0014"+
		"\u0000\u0000\u00b9\u00ba\u0005\u0019\u0000\u0000\u00ba\u00bb\u0005\u0013"+
		"\u0000\u0000\u00bb\u00bc\u0003\f\u0006\u0000\u00bc\u00bd\u0005\u0015\u0000"+
		"\u0000\u00bd\u00bf\u0001\u0000\u0000\u0000\u00be\u00b2\u0001\u0000\u0000"+
		"\u0000\u00be\u00b7\u0001\u0000\u0000\u0000\u00bf\'\u0001\u0000\u0000\u0000"+
		"\u00c0\u00c1\u0005\u0002\u0000\u0000\u00c1\u00c2\u0005\u0014\u0000\u0000"+
		"\u00c2\u00c3\u0003\n\u0005\u0000\u00c3\u00c4\u0005\u0015\u0000\u0000\u00c4"+
		")\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\u0004\u0000\u0000\u00c6\u00c7"+
		"\u0005\u0014\u0000\u0000\u00c7\u00c8\u0005\u0019\u0000\u0000\u00c8\u00c9"+
		"\u0005\u0013\u0000\u0000\u00c9\u00ca\u0003\f\u0006\u0000\u00ca\u00cb\u0005"+
		"\u0015\u0000\u0000\u00cb+\u0001\u0000\u0000\u0000\u0010.07@GO]euy\u0090"+
		"\u00a4\u00a8\u00ae\u00b0\u00be";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}