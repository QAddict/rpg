package foundation.fluent.jast.sample.lexer;

import foundation.fluent.jast.sample.tokens.Token;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import static java.lang.Character.*;

public class Lexer implements Iterator<Token> {

    private final Reader reader;
    int lookahead;

    public Lexer(Reader reader) {
        this.reader = reader;
        move();
    }

    private void move() {
        try {
            lookahead = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Token next() {
        int state = 0;
        StringBuilder builder = new StringBuilder();
        do {
            switch(state) {
                case 0:
                    switch (lookahead) {
                        case -1: state = 1; break;
                        case '(': state = 2; break;
                        case ')': state = 3; break;
                        case '+': state = 4; break;
                        default:
                            if(isJavaIdentifierStart(lookahead)) {
                                state = 5;
                                break;
                            }
                            if(!isWhitespace(lookahead)) {
                                throw new IllegalStateException("Unexpected character: " + (char) lookahead);
                            }
                    }
                    break;
                case 1: return Token.END;
                case 2: return Token.LPAR;
                case 3: return Token.RPAR;
                case 4: return Token.PLUS;
                case 5:
                    if(!isJavaIdentifierPart(lookahead))
                        return Token.ident(builder.toString());
            }
            builder.append((char) lookahead);
            move();
        } while (true);
    }
}
