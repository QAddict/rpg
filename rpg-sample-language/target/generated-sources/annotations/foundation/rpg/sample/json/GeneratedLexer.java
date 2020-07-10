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
		if(symbol < 0) return new ElementEnd(new End(builder.build()));
		for(; true; symbol = builder.next()) {
			switch(state) {
				case 0:
					switch(symbol) {
						case '"': state = 1; break;
						case '\'': state = 2; break;
						case ':': state = 3; break;
						case '[': state = 4; break;
						case '{': state = 5; break;
						case ',': state = 6; break;
						case ']': state = 7; break;
						case '}': state = 8; break;
						default:
							if(Lexer.matchesGroup('s', symbol)) { state = 9; break; }
							if(Lexer.matchesGroup('d', symbol)) { state = 10; break; }
							if(Lexer.matchesGroup('i', symbol)) { state = 11; break; }
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 1:
					switch(symbol) {
						case '"': state = 12; break;
						case '\\': state = 13; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 14; break;
					}
					break;
				case 2:
					switch(symbol) {
						case '\'': state = 15; break;
						case '\\': state = 16; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 17; break;
					}
					break;
				case 3:
					return new ElementColon(new foundation.rpg.common.symbols.Colon(builder.build()));
				case 4:
					return new ElementLBr(new foundation.rpg.common.symbols.LBr(builder.build()));
				case 5:
					return new ElementLCurl(new foundation.rpg.common.symbols.LCurl(builder.build()));
				case 6:
					return new ElementComma(new foundation.rpg.common.symbols.Comma(builder.build()));
				case 7:
					return new ElementRBr(new foundation.rpg.common.symbols.RBr(builder.build()));
				case 8:
					return new ElementRCurl(new foundation.rpg.common.symbols.RCurl(builder.build()));
				case 9:
					if(Lexer.matchesGroup('s', symbol)) { state = 9; break; }
					return new ElementWhiteSpace(new foundation.rpg.common.symbols.WhiteSpace(builder.build()));
				case 10:
					switch(symbol) {
						case 'e': state = 18; break;
						case 'E': state = 18; break;
						case '.': state = 18; break;
						default:
							if(Lexer.matchesGroup('d', symbol)) { state = 10; break; }
							return new ElementToken$$(builder.build());
					}
					break;
				case 11:
					if(Lexer.matchesGroup('w', symbol)) { state = 19; break; }
					return new ElementToken$(builder.build());
				case 12:
					return new ElementToken(builder.build());
				case 13:
					switch(symbol) {
						case '"': state = 20; break;
						case 'r': state = 20; break;
						case 't': state = 20; break;
						case '\\': state = 20; break;
						case 'n': state = 20; break;
						default:
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 14:
					switch(symbol) {
						case '"': state = 12; break;
						case '\\': state = 13; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 14; break;
					}
					break;
				case 15:
					return new ElementToken(builder.build());
				case 16:
					switch(symbol) {
						case 'r': state = 21; break;
						case 't': state = 21; break;
						case '\'': state = 21; break;
						case '\\': state = 21; break;
						case 'n': state = 21; break;
						default:
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 17:
					switch(symbol) {
						case '\'': state = 15; break;
						case '\\': state = 16; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 17; break;
					}
					break;
				case 18:
					if(Lexer.matchesGroup('d', symbol)) { state = 22; break; }
					input.error("Unexpected character: '" + (char) symbol + "'");
				case 19:
					if(Lexer.matchesGroup('w', symbol)) { state = 19; break; }
					return new ElementToken$(builder.build());
				case 20:
					switch(symbol) {
						case '"': state = 12; break;
						case '\\': state = 13; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 14; break;
					}
					break;
				case 21:
					switch(symbol) {
						case '\'': state = 15; break;
						case '\\': state = 16; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 17; break;
					}
					break;
				case 22:
					if(Lexer.matchesGroup('d', symbol)) { state = 22; break; }
					return new ElementToken$$$(builder.build());
			}
		}
	}
}
