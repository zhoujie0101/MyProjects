package com.jay.taolang.analysis;

import java.util.HashSet;

/**
 * Created by jay on 16/2/24.
 * 用于表示词法分析器 Tokenizer 的产物。同时，也作为下一阶段的语法分析器 Parser 的原料。
 */
public class Token {
    public enum Type {
        Keyword, Number, Identifier, Sign, Annotation, String, RegEx, Space, NewLine, EndSymbol
    }

    private static final HashSet<String> keywordsSet = new HashSet<>();
    static {
        keywordsSet.add("if");
        keywordsSet.add("when");
        keywordsSet.add("elsif");
        keywordsSet.add("else");
        keywordsSet.add("while");
        keywordsSet.add("begin");
        keywordsSet.add("until");
        keywordsSet.add("for");
        keywordsSet.add("do");
        keywordsSet.add("try");
        keywordsSet.add("catch");
        keywordsSet.add("finally");
        keywordsSet.add("end");
        keywordsSet.add("def");
        keywordsSet.add("var");
        keywordsSet.add("this");
        keywordsSet.add("null");
        keywordsSet.add("throw");
        keywordsSet.add("break");
        keywordsSet.add("continue");
        keywordsSet.add("return");
        keywordsSet.add("operator");
    }

    final Type type;
    final String value;

    public Token(Type type, String value) {
        if(type == Type.Identifier) {
            char firstChar = value.charAt(0);
            if(firstChar > '0' && firstChar < '9') {
                type = Type.Number;
            } else if(keywordsSet.contains(value)) {
                type = Type.Keyword;
            }
        } else if(type == Type.Annotation) {
            //  #这是注释
            value = value.substring(1);
        } else if(type == Type.String) {
            //  "这是字符串"
            value = value.substring(1, value.length() - 1);
        } else if(type == Type.RegEx) {
            //  `^\s+\d+$`
            value = value.substring(1, value.length() - 1);
        } else if(type == Type.EndSymbol) {
            //  EndSymbol 的语素必须为空，不管 Tokenizer 传入什么参数都必须如此。
            value = null;
        }
        this.type = type;
        this.value = value;
    }
}
