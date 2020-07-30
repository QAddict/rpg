/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Ondrej Fischer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package foundation.rpg.generator.parser.context;

import foundation.rpg.generator.parser.TypeUtils;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Map;

public class Context {

    private final ExecutableElement startRule;
    private final TypeElement factoryClass;
    private final String packageName;
    private final String parserName;
    private final String lexerName;
    private final Map<Rule, ExecutableElement> ruleToMethod;
    private final Map<Symbol, Entry> symbolToType;
    private final Grammar grammar;

    public Context(ExecutableElement startRule,
                   TypeElement factoryClass,
                   String packageName,
                   String parserName,
                   String lexerName,
                   Map<Rule, ExecutableElement> ruleToMethod,
                   Map<Symbol, Entry> symbolToType,
                   Grammar grammar) {

        this.startRule = startRule;
        this.factoryClass = factoryClass;
        this.packageName = packageName;
        this.parserName = parserName;
        this.lexerName = lexerName;
        this.ruleToMethod = ruleToMethod;
        this.symbolToType = symbolToType;
        this.grammar = grammar;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public ExecutableElement methodOf(Rule rule) {
        return ruleToMethod.get(rule);
    }

    public String typeOf(Symbol symbol) {
        return TypeUtils.typeName(typeMirrorOf(symbol));
    }

    public TypeMirror typeMirrorOf(Symbol symbol) {
        return symbolToType.get(symbol).getTypeMirror();
    }

    public boolean isFactoryStatic() {
        return false;
    }

    public ExecutableElement getStartRule() {
        return startRule;
    }

    public TypeElement getFactoryClass() {
        return factoryClass;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getParserName() {
        return parserName;
    }

    public String getLexerName() {
        return lexerName;
    }

}
