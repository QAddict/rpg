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
							throw new IllegalStateException("");
					}
					break;
				case 1:
					return visitor -> visitor.visitLBr(new foundation.rpg.common.LBr(builder.build()));
				case 2:
					return visitor -> visitor.visitRBr(new foundation.rpg.common.RBr(builder.build()));
				case 3:
					return visitor -> visitor.visitLCurl(new foundation.rpg.common.LCurl(builder.build()));
				case 4:
					return visitor -> visitor.visitRCurl(new foundation.rpg.common.RCurl(builder.build()));
				case 5:
					return visitor -> visitor.visitComma(new foundation.rpg.common.Comma(builder.build()));
				case 6:
					return visitor -> visitor.visitColon(new foundation.rpg.common.Colon(builder.build()));
				case 7:
					switch(symbol) {
						case '\'': state = 11; break;
						case '\\': state = 12; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 13; break;
					}
					break;
				case 8:
					switch(symbol) {
						case '"': state = 14; break;
						case '\\': state = 15; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 16; break;
					}
					break;
				case 9:
					if(Lexer.matchesGroup("w", symbol)) { state = 17; break; }
					return visitor -> visitor.visitString(getFactory().matchIdString(builder.build()));
				case 10:
					switch(symbol) {
						case '.': state = 18; break;
						case 'e': state = 18; break;
						case 'E': state = 18; break;
						default:
							if(Lexer.matchesGroup("d", symbol)) { state = 19; break; }
							return visitor -> visitor.visitInteger(getFactory().matchInt(builder.build()));
					}
					break;
				case 11:
					return visitor -> visitor.visitString(getFactory().matchQuotedString(builder.build()));
				case 12:
					switch(symbol) {
						case '\'': state = 20; break;
						case '\\': state = 20; break;
						case 'r': state = 20; break;
						case 'n': state = 20; break;
						case 't': state = 20; break;
						default:
							throw new IllegalStateException("");
					}
					break;
				case 13:
					switch(symbol) {
						case '\\': state = 12; break;
						case '\'': state = 11; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 13; break;
					}
					break;
				case 14:
					return visitor -> visitor.visitString(getFactory().matchQuotedString(builder.build()));
				case 15:
					switch(symbol) {
						case '"': state = 21; break;
						case '\\': state = 21; break;
						case 'r': state = 21; break;
						case 'n': state = 21; break;
						case 't': state = 21; break;
						default:
							throw new IllegalStateException("");
					}
					break;
				case 16:
					switch(symbol) {
						case '\\': state = 15; break;
						case '"': state = 14; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 16; break;
					}
					break;
				case 17:
					if(Lexer.matchesGroup("w", symbol)) { state = 17; break; }
					return visitor -> visitor.visitString(getFactory().matchIdString(builder.build()));
				case 18:
					if(Lexer.matchesGroup("d", symbol)) { state = 22; break; }
					throw new IllegalStateException("");
				case 19:
					switch(symbol) {
						case '.': state = 18; break;
						case 'e': state = 18; break;
						case 'E': state = 18; break;
						default:
							if(Lexer.matchesGroup("d", symbol)) { state = 19; break; }
							return visitor -> visitor.visitInteger(getFactory().matchInt(builder.build()));
					}
					break;
				case 20:
					switch(symbol) {
						case '\\': state = 12; break;
						case '\'': state = 11; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 13; break;
					}
					break;
				case 21:
					switch(symbol) {
						case '\\': state = 15; break;
						case '"': state = 14; break;
						default:
							if(symbol < 0) throw new IllegalStateException("");
							state = 16; break;
					}
					break;
				case 22:
					if(Lexer.matchesGroup("d", symbol)) { state = 23; break; }
					return visitor -> visitor.visitDouble(getFactory().matchDouble(builder.build()));
				case 23:
					if(Lexer.matchesGroup("d", symbol)) { state = 23; break; }
					return visitor -> visitor.visitDouble(getFactory().matchDouble(builder.build()));
			}
		}
	}
}
