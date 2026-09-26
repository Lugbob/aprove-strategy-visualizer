// Generated from c:/Users/lucar/Desktop/AProVe_Strategy_Visualizer_final/parser/src/main/antlr4/de/luca/grammar/StrategyDeclarationLexer.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class StrategyDeclarationLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DECLARE=1, DEFAULTS=2, EQ=3, DOT=4, COMMA=5, LSQBRA=6, RSQBRA=7, DESCRIPTION=8, 
		COMMENT=9, STRING=10, NUMBER=11, WS=12, IDENT=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"DECLARE", "DEFAULTS", "EQ", "DOT", "COMMA", "LSQBRA", "RSQBRA", "DESCRIPTION", 
			"COMMENT", "STRING", "NUMBER", "WS", "IDENT"
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


	public StrategyDeclarationLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "StrategyDeclarationLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\r}\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0005\u0007F\b\u0007\n\u0007\f\u0007I\t"+
		"\u0007\u0001\b\u0001\b\u0005\bM\b\b\n\b\f\bP\t\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0005\tX\b\t\n\t\f\t[\t\t\u0001\t\u0001\t\u0001"+
		"\n\u0003\n`\b\n\u0001\n\u0004\nc\b\n\u000b\n\f\nd\u0001\u000b\u0004\u000b"+
		"h\b\u000b\u000b\u000b\f\u000bi\u0001\u000b\u0001\u000b\u0001\f\u0001\f"+
		"\u0005\fp\b\f\n\f\f\fs\t\f\u0001\f\u0001\f\u0005\fw\b\f\n\f\f\fz\t\f\u0003"+
		"\f|\b\f\u0000\u0000\r\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u0001\u0000\u0007\u0002\u0000\n\n\r\r\u0002\u0000\"\"\\\\\u0001"+
		"\u000009\u0003\u0000\t\n\r\r  \u0001\u0000AZ\u0004\u000009AZ__az\u0001"+
		"\u0000az\u0086\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0001\u001b\u0001\u0000\u0000\u0000"+
		"\u0003#\u0001\u0000\u0000\u0000\u0005,\u0001\u0000\u0000\u0000\u0007."+
		"\u0001\u0000\u0000\u0000\t0\u0001\u0000\u0000\u0000\u000b2\u0001\u0000"+
		"\u0000\u0000\r4\u0001\u0000\u0000\u0000\u000f6\u0001\u0000\u0000\u0000"+
		"\u0011J\u0001\u0000\u0000\u0000\u0013S\u0001\u0000\u0000\u0000\u0015_"+
		"\u0001\u0000\u0000\u0000\u0017g\u0001\u0000\u0000\u0000\u0019{\u0001\u0000"+
		"\u0000\u0000\u001b\u001c\u0005d\u0000\u0000\u001c\u001d\u0005e\u0000\u0000"+
		"\u001d\u001e\u0005c\u0000\u0000\u001e\u001f\u0005l\u0000\u0000\u001f "+
		"\u0005a\u0000\u0000 !\u0005r\u0000\u0000!\"\u0005e\u0000\u0000\"\u0002"+
		"\u0001\u0000\u0000\u0000#$\u0005d\u0000\u0000$%\u0005e\u0000\u0000%&\u0005"+
		"f\u0000\u0000&\'\u0005a\u0000\u0000\'(\u0005u\u0000\u0000()\u0005l\u0000"+
		"\u0000)*\u0005t\u0000\u0000*+\u0005s\u0000\u0000+\u0004\u0001\u0000\u0000"+
		"\u0000,-\u0005=\u0000\u0000-\u0006\u0001\u0000\u0000\u0000./\u0005.\u0000"+
		"\u0000/\b\u0001\u0000\u0000\u000001\u0005,\u0000\u00001\n\u0001\u0000"+
		"\u0000\u000023\u0005[\u0000\u00003\f\u0001\u0000\u0000\u000045\u0005]"+
		"\u0000\u00005\u000e\u0001\u0000\u0000\u000067\u0005#\u0000\u000078\u0005"+
		"@\u0000\u000089\u0005d\u0000\u00009:\u0005e\u0000\u0000:;\u0005s\u0000"+
		"\u0000;<\u0005c\u0000\u0000<=\u0005r\u0000\u0000=>\u0005i\u0000\u0000"+
		">?\u0005p\u0000\u0000?@\u0005t\u0000\u0000@A\u0005i\u0000\u0000AB\u0005"+
		"o\u0000\u0000BC\u0005n\u0000\u0000CG\u0001\u0000\u0000\u0000DF\b\u0000"+
		"\u0000\u0000ED\u0001\u0000\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001"+
		"\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000H\u0010\u0001\u0000\u0000"+
		"\u0000IG\u0001\u0000\u0000\u0000JN\u0005#\u0000\u0000KM\b\u0000\u0000"+
		"\u0000LK\u0001\u0000\u0000\u0000MP\u0001\u0000\u0000\u0000NL\u0001\u0000"+
		"\u0000\u0000NO\u0001\u0000\u0000\u0000OQ\u0001\u0000\u0000\u0000PN\u0001"+
		"\u0000\u0000\u0000QR\u0006\b\u0000\u0000R\u0012\u0001\u0000\u0000\u0000"+
		"SY\u0005\"\u0000\u0000TX\b\u0001\u0000\u0000UV\u0005\\\u0000\u0000VX\t"+
		"\u0000\u0000\u0000WT\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000"+
		"X[\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000"+
		"\u0000Z\\\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000\\]\u0005\""+
		"\u0000\u0000]\u0014\u0001\u0000\u0000\u0000^`\u0005-\u0000\u0000_^\u0001"+
		"\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`b\u0001\u0000\u0000\u0000"+
		"ac\u0007\u0002\u0000\u0000ba\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000"+
		"\u0000db\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000e\u0016\u0001"+
		"\u0000\u0000\u0000fh\u0007\u0003\u0000\u0000gf\u0001\u0000\u0000\u0000"+
		"hi\u0001\u0000\u0000\u0000ig\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000"+
		"\u0000jk\u0001\u0000\u0000\u0000kl\u0006\u000b\u0000\u0000l\u0018\u0001"+
		"\u0000\u0000\u0000mq\u0007\u0004\u0000\u0000np\u0007\u0005\u0000\u0000"+
		"on\u0001\u0000\u0000\u0000ps\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000"+
		"\u0000qr\u0001\u0000\u0000\u0000r|\u0001\u0000\u0000\u0000sq\u0001\u0000"+
		"\u0000\u0000tx\u0007\u0006\u0000\u0000uw\u0007\u0005\u0000\u0000vu\u0001"+
		"\u0000\u0000\u0000wz\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000"+
		"xy\u0001\u0000\u0000\u0000y|\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000"+
		"\u0000{m\u0001\u0000\u0000\u0000{t\u0001\u0000\u0000\u0000|\u001a\u0001"+
		"\u0000\u0000\u0000\u000b\u0000GNWY_diqx{\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}