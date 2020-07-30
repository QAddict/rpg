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
						case 'e': state = 2; break;
						case '(': state = 3; break;
						case 'i': state = 4; break;
						case ')': state = 5; break;
						case '*': state = 6; break;
						case '+': state = 7; break;
						case ',': state = 8; break;
						case '/': state = 9; break;
						case 't': state = 10; break;
						case ';': state = 11; break;
						case '=': state = 12; break;
						case '>': state = 13; break;
						default:
							if(Lexer.matchesGroup('s', symbol)) { state = 14; break; }
							if(Lexer.matchesGroup('d', symbol)) { state = 15; break; }
							if(Lexer.matchesGroup('i', symbol)) { state = 16; break; }
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 1:
					switch(symbol) {
						case '"': state = 17; break;
						case '\\': state = 18; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 19; break;
					}
					break;
				case 2:
					if(symbol == 'l') { state = 20; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 3:
					return new ElementLPar(new foundation.rpg.common.symbols.LPar(builder.build()));
				case 4:
					if(symbol == 'f') { state = 22; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 5:
					return new ElementRPar(new foundation.rpg.common.symbols.RPar(builder.build()));
				case 6:
					return new ElementTimes(new foundation.rpg.common.symbols.Times(builder.build()));
				case 7:
					return new ElementPlus(new foundation.rpg.common.symbols.Plus(builder.build()));
				case 8:
					return new ElementComma(new foundation.rpg.common.symbols.Comma(builder.build()));
				case 9:
					if(symbol == '*') { state = 23; break; }
					input.error("Unexpected character: '" + (char) symbol + "'");
				case 10:
					if(symbol == 'h') { state = 24; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 11:
					return new ElementSemicolon(new foundation.rpg.common.symbols.Semicolon(builder.build()));
				case 12:
					return new ElementEqual(new foundation.rpg.common.symbols.Equal(builder.build()));
				case 13:
					return new ElementGt(new foundation.rpg.common.symbols.Gt(builder.build()));
				case 14:
					if(Lexer.matchesGroup('s', symbol)) { state = 14; break; }
					return new ElementWhiteSpace(new foundation.rpg.common.symbols.WhiteSpace(builder.build()));
				case 15:
					if(Lexer.matchesGroup('d', symbol)) { state = 15; break; }
					return new ElementLong(new java.lang.Long(builder.build().getContent()));
				case 16:
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 17:
					return new ElementString(new java.lang.String(builder.build().getContent()));
				case 18:
					switch(symbol) {
						case '"': state = 25; break;
						case 'r': state = 25; break;
						case 't': state = 25; break;
						case '\\': state = 25; break;
						case 'n': state = 25; break;
						default:
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 19:
					switch(symbol) {
						case '"': state = 17; break;
						case '\\': state = 18; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 19; break;
					}
					break;
				case 20:
					if(symbol == 's') { state = 26; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 21:
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 22:
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
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
					if(symbol == 'e') { state = 30; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 25:
					switch(symbol) {
						case '"': state = 17; break;
						case '\\': state = 18; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 19; break;
					}
					break;
				case 26:
					if(symbol == 'e') { state = 31; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
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
					if(symbol == 'n') { state = 35; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 31:
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
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
				case 35:
					if(Lexer.matchesGroup('w', symbol)) { state = 21; break; }
					return new ElementThen(new foundation.rpg.common.symbols.Then(builder.build()));
			}
		}
	}
}
