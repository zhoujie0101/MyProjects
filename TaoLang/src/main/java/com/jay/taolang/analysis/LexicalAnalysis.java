package com.jay.taolang.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

import static com.jay.taolang.analysis.Token.Type;

/**
 * Created by jay on 16/2/24.
 */
public class LexicalAnalysis {
    private enum State {
        Normal, Identifier, Sign, Annotation, String, RegEX, Space
    }

    private static final char[] Space = new char[]{' ', '\t'};
    private static final char[] IdentifierRearSign = new char[]{'?', '!'};

    public LexicalAnalysis(Reader reader) {
    }

    Token reader() throws IOException, LexicalAnalysisException {
         return new Token(Type.EndSymbol, null);
    }

    private State state;
    private LinkedList<Token> tokenBuffer = new LinkedList<>();
    private StringBuilder readBuffer;

    private void refreshBuffer(char c) {
        readBuffer = new StringBuilder();
        readBuffer.append(c);
    }

    private void createToken(Type type) {
        Token token = new Token(type, readBuffer.toString());
        tokenBuffer.addFirst(token);
        readBuffer = null;
    }

    private boolean readChar(char c) throws LexicalAnalysisException {
        // 在读完当前 char 之后，是否移动游标读取下一个字符。如果为 false，则该函数的下一次调用的参数与前一次调用的参数会一模一样
        boolean moveCursor = true;
        Type createType = null;
        if(state == State.Normal) {
            if(inIdentifierSetNotRear(c)) {
                state = State.Identifier;
            } else if(SignParser.inCharSet(c)) {
                state = State.Sign;
            } else if(c == '#') {
                state = State.Annotation;
            } else if(c == '\"' || c == '\'') {
                state = State.String;
            } else if(include(Space, c)) {
                state = State.Space;
            }
        } else if(state == State.Identifier) {
            if(inIdentifierSetNotRear(c)) {
                readBuffer.append(c);
            } else if(include(IdentifierRearSign, c)) {
                createType = Type.Identifier;
                readBuffer.append(c);
                state = State.Normal;
            } else {
                createType = Type.Identifier;
                state = State.Normal;
                moveCursor = false;
            }
        } else if(state == State.Annotation) {
            if(c != '\n' && c != '\0') {
                readBuffer.append(c);
            } else {
                createType = Type.Annotation;
                state = State.Normal;
                moveCursor = false;
            }
        } else if(state == State.String) {

        } else if(state == State.RegEX) {

        } else if(state == State.Sign) {

        } else if(state == State.Space) {

        }
        if(createType != null) {
            createToken(createType);
        }

        return moveCursor;
    }

    private boolean inIdentifierSetNotRear(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= 'A' && c <= 'Z') || (c == '_');
    }

    private boolean include(char[] range, char c) {
        boolean include = false;
        for(int i = 0; i < range.length; i++) {
            if(range[i] == c) {
                include = true;
                break;
            }
        }

        return include;
    }

}