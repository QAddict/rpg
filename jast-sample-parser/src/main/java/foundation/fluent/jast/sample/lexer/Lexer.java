package foundation.fluent.jast.sample.lexer;

import foundation.fluent.jast.sample.tokens.Token;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import static foundation.fluent.jast.sample.lexer.Lexer.State.*;
import static java.lang.Character.*;

public class Lexer implements Iterator<Token> {

    private final Reader reader;
    int lookahead;
    int line = 0;
    int pos = 0;

    public Lexer(Reader reader) {
        this.reader = reader;
        move();
    }

    private void move() {
        try {
            if(lookahead == '\n') {
                line++;
                pos = 0;
            } else {
                pos++;
            }
            lookahead = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    enum State {START, LPAR, RPAR, PLUS, IDENT, END}

    @Override
    public Token next() {
        State state = State.START;
        StringBuilder builder = new StringBuilder();
        int line = this.line;
        int pos = this.pos;
        do {
            switch(state) {
                case START:
                    switch (lookahead) {
                        case -1: state = END; break;
                        case '(': state = LPAR; break;
                        case ')': state = RPAR; break;
                        case '+': state = PLUS; break;
                        default:
                            if(isJavaIdentifierStart(lookahead)) {
                                state = IDENT;
                                break;
                            }
                            if(!isWhitespace(lookahead)) {
                                throw new IllegalStateException("Unexpected character: " + (char) lookahead);
                            }
                    }
                    break;
                case END: return Token.END;
                case LPAR: return Token.LPAR;
                case RPAR: return Token.RPAR;
                case PLUS: return Token.PLUS;
                case IDENT:
                    if(!isJavaIdentifierPart(lookahead))
                        return Token.ident(builder.toString(), line, pos);
            }
            builder.append((char) lookahead);
            move();
        } while (true);
    }
}
