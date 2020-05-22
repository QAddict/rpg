package foundation.rpg.sample.language.ast;

import foundation.rpg.common.symbols.*;
import foundation.rpg.parser.Element;
import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.Input;
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
		if(symbol < 0) return visitor -> visitor.visitEnd(new End(builder.build()));
		for(; true; symbol = builder.next()) {
			switch(state) {
				case 0:
					switch(symbol) {
						case '.': state = 1; break;
						case '>': state = 2; break;
						case '+': state = 3; break;
						case '*': state = 4; break;
						case '(': state = 5; break;
						case ')': state = 6; break;
						case ',': state = 7; break;
						default:
							if(Lexer.matchesGroup("w", symbol)) { state = 8; break; }
							throw new IllegalStateException("");
					}
					break;
				case 1:
					return visitor -> visitor.visitDot(new Dot(builder.build()));
				case 2:
					return visitor -> visitor.visitGt(new Gt(builder.build()));
				case 3:
					return visitor -> visitor.visitPlus(new Plus(builder.build()));
				case 4:
					return visitor -> visitor.visitTimes(new Times(builder.build()));
				case 5:
					return visitor -> visitor.visitLPar(new LPar(builder.build()));
				case 6:
					return visitor -> visitor.visitRPar(new RPar(builder.build()));
				case 7:
					return visitor -> visitor.visitComma(new Comma(builder.build()));
				case 8:
					if(Lexer.matchesGroup("a", symbol)) { state = 9; break; }
					return visitor -> visitor.visitIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
				case 9:
					if(Lexer.matchesGroup("a", symbol)) { state = 9; break; }
					return visitor -> visitor.visitIdentifier(new foundation.rpg.sample.language.ast.Identifier(builder.build().getContent()));
			}
		}
	}
}
