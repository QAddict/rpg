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
						case 't': state = 2; break;
						case 'e': state = 3; break;
						case '(': state = 4; break;
						case 'i': state = 5; break;
						case ')': state = 6; break;
						case '*': state = 7; break;
						case ';': state = 8; break;
						case '+': state = 9; break;
						case ',': state = 10; break;
						case '>': state = 11; break;
						case '/': state = 12; break;
						default:
							if(Lexer.matchesGroup('s', symbol)) { state = 13; break; }
							if(Lexer.matchesGroup('d', symbol)) { state = 14; break; }
							if(Lexer.matchesGroup('i', symbol)) { state = 15; break; }
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 1:
					switch(symbol) {
						case '"': state = 16; break;
						case '\\': state = 17; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 18; break;
					}
					break;
				case 2:
					if(symbol == 'h') { state = 19; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 3:
					if(symbol == 'l') { state = 21; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 4:
					return new ElementLPar(new foundation.rpg.common.symbols.LPar(builder.build()));
				case 5:
					if(symbol == 'f') { state = 22; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 6:
					return new ElementRPar(new foundation.rpg.common.symbols.RPar(builder.build()));
				case 7:
					return new ElementTimes(new foundation.rpg.common.symbols.Times(builder.build()));
				case 8:
					return new ElementSemicolon(new foundation.rpg.common.symbols.Semicolon(builder.build()));
				case 9:
					return new ElementPlus(new foundation.rpg.common.symbols.Plus(builder.build()));
				case 10:
					return new ElementComma(new foundation.rpg.common.symbols.Comma(builder.build()));
				case 11:
					return new ElementGt(new foundation.rpg.common.symbols.Gt(builder.build()));
				case 12:
					if(symbol == '*') { state = 23; break; }
					input.error("Unexpected character: '" + (char) symbol + "'");
				case 13:
					if(Lexer.matchesGroup('s', symbol)) { state = 13; break; }
					return new ElementWhiteSpace(new foundation.rpg.common.symbols.WhiteSpace(builder.build()));
				case 14:
					if(Lexer.matchesGroup('d', symbol)) { state = 14; break; }
					return new ElementLong(new java.lang.Long(builder.build().getContent()));
				case 15:
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 16:
					return new ElementString(new java.lang.String(builder.build().getContent()));
				case 17:
					switch(symbol) {
						case '"': state = 24; break;
						case 'r': state = 24; break;
						case 't': state = 24; break;
						case '\\': state = 24; break;
						case 'n': state = 24; break;
						default:
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 18:
					switch(symbol) {
						case '"': state = 16; break;
						case '\\': state = 17; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 18; break;
					}
					break;
				case 19:
					if(symbol == 'e') { state = 25; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 20:
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 21:
					if(symbol == 's') { state = 26; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 22:
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIf(new foundation.rpg.common.symbols.If(builder.build()));
				case 23:
					switch(symbol) {
						case '*': state = 27; break;
						case '/': state = 28; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 29; break;
					}
					break;
				case 24:
					switch(symbol) {
						case '"': state = 16; break;
						case '\\': state = 17; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 18; break;
					}
					break;
				case 25:
					if(symbol == 'n') { state = 30; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 26:
					if(symbol == 'e') { state = 31; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 27:
					switch(symbol) {
						case '*': state = 32; break;
						case '/': state = 33; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 34; break;
					}
					break;
				case 28:
					switch(symbol) {
						case '*': state = 27; break;
						case '/': state = 28; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 29; break;
					}
					break;
				case 29:
					switch(symbol) {
						case '*': state = 27; break;
						case '/': state = 28; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 29; break;
					}
					break;
				case 30:
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementThen(new foundation.rpg.common.symbols.Then(builder.build()));
				case 31:
					if(Lexer.matchesGroup('w', symbol)) { state = 20; break; }
					return new ElementElse(new foundation.rpg.common.symbols.Else(builder.build()));
				case 32:
					switch(symbol) {
						case '*': state = 27; break;
						case '/': state = 28; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 29; break;
					}
					break;
				case 33:
					return new ElementComment(new foundation.rpg.common.symbols.Comment(builder.build()));
				case 34:
					switch(symbol) {
						case '*': state = 27; break;
						case '/': state = 28; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 29; break;
					}
					break;
			}
		}
	}
}
