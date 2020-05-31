package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.Input;
import foundation.rpg.parser.Position;
import foundation.rpg.parser.End;
import java.io.IOException;
import foundation.rpg.parser.TokenBuilder;

public class GeneratedLexer implements Lexer<State> {
	private final foundation.rpg.sample.language.ast.AstFactory factory;

	public GeneratedLexer(foundation.rpg.sample.language.ast.AstFactory factory) {
		this.factory = factory;
	}

	public foundation.rpg.sample.language.ast.AstFactory getFactory() {
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
						case '(': state = 2; break;
						case ')': state = 3; break;
						case '*': state = 4; break;
						case ';': state = 5; break;
						case '+': state = 6; break;
						case ',': state = 7; break;
						case '>': state = 8; break;
						case '/': state = 9; break;
						default:
							if(Lexer.matchesGroup('s', symbol)) { state = 10; break; }
							if(Lexer.matchesGroup('d', symbol)) { state = 11; break; }
							if(Lexer.matchesGroup('i', symbol)) { state = 12; break; }
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 1:
					switch(symbol) {
						case '"': state = 13; break;
						case '\\': state = 14; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 15; break;
					}
					break;
				case 2:
					return new ElementLPar(new foundation.rpg.common.symbols.LPar(builder.build()));
				case 3:
					return new ElementRPar(new foundation.rpg.common.symbols.RPar(builder.build()));
				case 4:
					return new ElementTimes(new foundation.rpg.common.symbols.Times(builder.build()));
				case 5:
					return new ElementSemicolon(new foundation.rpg.common.symbols.Semicolon(builder.build()));
				case 6:
					return new ElementPlus(new foundation.rpg.common.symbols.Plus(builder.build()));
				case 7:
					return new ElementComma(new foundation.rpg.common.symbols.Comma(builder.build()));
				case 8:
					return new ElementGt(new foundation.rpg.common.symbols.Gt(builder.build()));
				case 9:
					if(symbol == '*') { state = 16; break; }
					input.error("Unexpected character: '" + (char) symbol + "'");
				case 10:
					if(Lexer.matchesGroup('s', symbol)) { state = 10; break; }
					return new ElementWhiteSpace(new foundation.rpg.common.symbols.WhiteSpace(builder.build()));
				case 11:
					if(Lexer.matchesGroup('d', symbol)) { state = 11; break; }
					return new ElementLong(new java.lang.Long(builder.build().getContent()));
				case 12:
					if(Lexer.matchesGroup('w', symbol)) { state = 17; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 13:
					return new ElementString(new java.lang.String(builder.build().getContent()));
				case 14:
					switch(symbol) {
						case '"': state = 18; break;
						case 'r': state = 18; break;
						case 't': state = 18; break;
						case '\\': state = 18; break;
						case 'n': state = 18; break;
						default:
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 15:
					switch(symbol) {
						case '"': state = 13; break;
						case '\\': state = 14; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 15; break;
					}
					break;
				case 16:
					switch(symbol) {
						case '*': state = 19; break;
						case '/': state = 20; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 21; break;
					}
					break;
				case 17:
					if(Lexer.matchesGroup('w', symbol)) { state = 17; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 18:
					switch(symbol) {
						case '"': state = 13; break;
						case '\\': state = 14; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 15; break;
					}
					break;
				case 19:
					switch(symbol) {
						case '*': state = 22; break;
						case '/': state = 23; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 24; break;
					}
					break;
				case 20:
					switch(symbol) {
						case '*': state = 19; break;
						case '/': state = 20; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 21; break;
					}
					break;
				case 21:
					switch(symbol) {
						case '*': state = 19; break;
						case '/': state = 20; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 21; break;
					}
					break;
				case 22:
					switch(symbol) {
						case '*': state = 19; break;
						case '/': state = 20; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 21; break;
					}
					break;
				case 23:
					return new ElementComment(new foundation.rpg.common.symbols.Comment(builder.build()));
				case 24:
					switch(symbol) {
						case '*': state = 19; break;
						case '/': state = 20; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 21; break;
					}
					break;
			}
		}
	}
}
