package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.Input;
import foundation.rpg.parser.Position;
import foundation.rpg.parser.End;
import java.io.IOException;
import foundation.rpg.parser.TokenBuilder;

public class ProgramLexer implements Lexer<State> {
	private final foundation.rpg.sample.language.ast.AstFactory factory;

	public ProgramLexer(foundation.rpg.sample.language.ast.AstFactory factory) {
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
						case 'o': state = 9; break;
						case '/': state = 10; break;
						case 't': state = 11; break;
						case ';': state = 12; break;
						case '=': state = 13; break;
						case '>': state = 14; break;
						default:
							if(Lexer.matchesGroup('s', symbol)) { state = 15; break; }
							if(Lexer.matchesGroup('d', symbol)) { state = 16; break; }
							if(Lexer.matchesGroup('i', symbol)) { state = 17; break; }
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 1:
					switch(symbol) {
						case '"': state = 18; break;
						case '\\': state = 19; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 20; break;
					}
					break;
				case 2:
					if(symbol == 'l') { state = 21; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 3:
					return new ElementLPar(new foundation.rpg.common.symbols.LPar(builder.build()));
				case 4:
					if(symbol == 'f') { state = 23; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
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
					if(symbol == 'p') { state = 24; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 10:
					if(symbol == '*') { state = 25; break; }
					input.error("Unexpected character: '" + (char) symbol + "'");
				case 11:
					if(symbol == 'h') { state = 26; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 12:
					return new ElementSemicolon(new foundation.rpg.common.symbols.Semicolon(builder.build()));
				case 13:
					return new ElementEqual(new foundation.rpg.common.symbols.Equal(builder.build()));
				case 14:
					return new ElementGt(new foundation.rpg.common.symbols.Gt(builder.build()));
				case 15:
					if(Lexer.matchesGroup('s', symbol)) { state = 15; break; }
					return new ElementWhiteSpace(new foundation.rpg.common.symbols.WhiteSpace(builder.build()));
				case 16:
					if(Lexer.matchesGroup('d', symbol)) { state = 16; break; }
					return new ElementLong(new java.lang.Long(builder.build().getContent()));
				case 17:
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 18:
					return new ElementString(new java.lang.String(builder.build().getContent()));
				case 19:
					switch(symbol) {
						case '"': state = 27; break;
						case 'r': state = 27; break;
						case 't': state = 27; break;
						case '\\': state = 27; break;
						case 'n': state = 27; break;
						default:
							input.error("Unexpected character: '" + (char) symbol + "'");
					}
					break;
				case 20:
					switch(symbol) {
						case '"': state = 18; break;
						case '\\': state = 19; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 20; break;
					}
					break;
				case 21:
					if(symbol == 's') { state = 28; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 22:
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 23:
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIf(new foundation.rpg.common.symbols.If(builder.build()));
				case 24:
					if(symbol == 't') { state = 29; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 25:
					switch(symbol) {
						case '*': state = 30; break;
						case '/': state = 31; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 32; break;
					}
					break;
				case 26:
					if(symbol == 'e') { state = 33; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 27:
					switch(symbol) {
						case '"': state = 18; break;
						case '\\': state = 19; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 20; break;
					}
					break;
				case 28:
					if(symbol == 'e') { state = 34; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 29:
					if(symbol == 'i') { state = 35; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 30:
					switch(symbol) {
						case '*': state = 36; break;
						case '/': state = 37; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 38; break;
					}
					break;
				case 31:
					switch(symbol) {
						case '*': state = 30; break;
						case '/': state = 31; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 32; break;
					}
					break;
				case 32:
					switch(symbol) {
						case '*': state = 30; break;
						case '/': state = 31; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 32; break;
					}
					break;
				case 33:
					if(symbol == 'n') { state = 39; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 34:
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementElse(new foundation.rpg.common.symbols.Else(builder.build()));
				case 35:
					if(symbol == 'o') { state = 40; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 36:
					switch(symbol) {
						case '*': state = 30; break;
						case '/': state = 31; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 32; break;
					}
					break;
				case 37:
					return new ElementComment(new foundation.rpg.common.symbols.Comment(builder.build()));
				case 38:
					switch(symbol) {
						case '*': state = 30; break;
						case '/': state = 31; break;
						default:
							if(symbol < 0) input.error("Unexpected end of input");
							state = 32; break;
					}
					break;
				case 39:
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementThen(new foundation.rpg.common.symbols.Then(builder.build()));
				case 40:
					if(symbol == 'n') { state = 41; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 41:
					if(symbol == 'a') { state = 42; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 42:
					if(symbol == 'l') { state = 43; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 43:
					switch(symbol) {
						case 's': state = 44; break;
						case 'i': state = 45; break;
						default:
							if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
							return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
					}
					break;
				case 44:
					if(symbol == 't') { state = 46; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 45:
					if(symbol == 'n') { state = 47; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 46:
					if(symbol == 'r') { state = 48; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 47:
					if(symbol == 't') { state = 49; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 48:
					if(symbol == 'i') { state = 50; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 49:
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementKeywordOfInteger(new foundation.rpg.sample.language.ast.Keyword<java.lang.Integer>());
				case 50:
					if(symbol == 'n') { state = 51; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 51:
					if(symbol == 'g') { state = 52; break; }
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 52:
					if(Lexer.matchesGroup('w', symbol)) { state = 22; break; }
					return new ElementKeywordOfString(new foundation.rpg.sample.language.ast.Keyword<java.lang.String>());
			}
		}
	}
}
