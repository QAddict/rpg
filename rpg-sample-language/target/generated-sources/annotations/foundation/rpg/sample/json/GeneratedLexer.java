package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.Input;
import foundation.rpg.parser.Position;
import foundation.rpg.parser.End;
import java.io.IOException;
import foundation.rpg.parser.TokenBuilder;

public class GeneratedLexer implements Lexer<State> {
	private final foundation.rpg.sample.json.JsonFactory factory;

	public GeneratedLexer(foundation.rpg.sample.json.JsonFactory factory) {
		this.factory = factory;
	}

	public foundation.rpg.sample.json.JsonFactory getFactory() {
		return factory;
	}

	public Element<State> next(Input input) throws IOException {
		int state = 0;
		int symbol = input.lookahead();
		TokenBuilder builder = new TokenBuilder(input);
		if(symbol < 0) return visitor -> visitor.visitEnd(new End(builder.build()));
		for(; true; symbol = builder.next()) {
			switch(state) {
				case 0:
					switch(symbol) {
						case '[': state = 1; break;
						case ']': state = 2; break;
						case '{': state = 3; break;
						case '}': state = 4; break;
						case ',': state = 5; break;
						case ':': state = 6; break;
						case '\'': state = 7; break;
						case '"': state = 8; break;
						default:
							if(Lexer.matchesGroup("i", symbol)) { state = 9; break; }
							if(Lexer.matchesGroup("d", symbol)) { state = 10; break; }
							if(Lexer.matchesGroup("s", symbol)) { state = 11; break; }
							throw new IllegalStateException("");
					}
					break;
				case 1:
					return visitor -> visitor.visitLBr(new foundation.rpg.common.symbols.LBr(builder.build()));
				case 2:
					return visitor -> visitor.visitRBr(new foundation.rpg.common.symbols.RBr(builder.build()));
				case 3:
					return visitor -> visitor.visitLCurl(new foundation.rpg.common.symbols.LCurl(builder.build()));
				case 4:
					return visitor -> visitor.visitRCurl(new foundation.rpg.common.symbols.RCurl(builder.build()));
				case 5:
					return visitor -> visitor.visitComma(new foundation.rpg.common.symbols.Comma(builder.build()));
				case 6:
					return visitor -> visitor.visitColon(new foundation.rpg.common.symbols.Colon(builder.build()));
				case 7:
					switch(symbol) {
						case '\'': state = 12; break;
						case '\\': state = 13; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 14; break;
					}
					break;
				case 8:
					switch(symbol) {
						case '"': state = 15; break;
						case '\\': state = 16; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 17; break;
					}
					break;
				case 9:
					if(Lexer.matchesGroup("w", symbol)) { state = 18; break; }
					return visitor -> visitor.visitString(getFactory().matchIdString(builder.build()));
				case 10:
					switch(symbol) {
						case '.': state = 19; break;
						case 'e': state = 19; break;
						case 'E': state = 19; break;
						default:
							if(Lexer.matchesGroup("d", symbol)) { state = 20; break; }
							return visitor -> visitor.visitInteger(getFactory().matchInt(builder.build()));
					}
					break;
				case 11:
					if(Lexer.matchesGroup("s", symbol)) { state = 21; break; }
					return visitor -> visitor.visitWhiteSpace(new foundation.rpg.common.symbols.WhiteSpace(builder.build()));
				case 12:
					return visitor -> visitor.visitString(getFactory().matchQuotedString(builder.build()));
				case 13:
					switch(symbol) {
						case '\'': state = 22; break;
						case '\\': state = 22; break;
						case 'r': state = 22; break;
						case 'n': state = 22; break;
						case 't': state = 22; break;
						default:
							throw new IllegalStateException("");
					}
					break;
				case 14:
					switch(symbol) {
						case '\\': state = 13; break;
						case '\'': state = 12; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 14; break;
					}
					break;
				case 15:
					return visitor -> visitor.visitString(getFactory().matchQuotedString(builder.build()));
				case 16:
					switch(symbol) {
						case '"': state = 23; break;
						case '\\': state = 23; break;
						case 'r': state = 23; break;
						case 'n': state = 23; break;
						case 't': state = 23; break;
						default:
							throw new IllegalStateException("");
					}
					break;
				case 17:
					switch(symbol) {
						case '\\': state = 16; break;
						case '"': state = 15; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 17; break;
					}
					break;
				case 18:
					if(Lexer.matchesGroup("w", symbol)) { state = 18; break; }
					return visitor -> visitor.visitString(getFactory().matchIdString(builder.build()));
				case 19:
					if(Lexer.matchesGroup("d", symbol)) { state = 24; break; }
					throw new IllegalStateException("");
				case 20:
					switch(symbol) {
						case '.': state = 19; break;
						case 'e': state = 19; break;
						case 'E': state = 19; break;
						default:
							if(Lexer.matchesGroup("d", symbol)) { state = 20; break; }
							return visitor -> visitor.visitInteger(getFactory().matchInt(builder.build()));
					}
					break;
				case 21:
					if(Lexer.matchesGroup("s", symbol)) { state = 21; break; }
					return visitor -> visitor.visitWhiteSpace(new foundation.rpg.common.symbols.WhiteSpace(builder.build()));
				case 22:
					switch(symbol) {
						case '\\': state = 13; break;
						case '\'': state = 12; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 14; break;
					}
					break;
				case 23:
					switch(symbol) {
						case '\\': state = 16; break;
						case '"': state = 15; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 17; break;
					}
					break;
				case 24:
					if(Lexer.matchesGroup("d", symbol)) { state = 25; break; }
					return visitor -> visitor.visitDouble(getFactory().matchDouble(builder.build()));
				case 25:
					if(Lexer.matchesGroup("d", symbol)) { state = 25; break; }
					return visitor -> visitor.visitDouble(getFactory().matchDouble(builder.build()));
			}
		}
	}
}
