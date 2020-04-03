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

package foundation.fluent.jast.processor;

import foundation.fluent.jast.parser.Name;

import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LexerGenerator {

    class Node {
        private TypeMirror type;
        private final Map<Character, Node> followers = new HashMap<>();

        public void add(String s, TypeMirror t) {
            if(s.isEmpty()) {
                type = t;
            } else {
                followers.computeIfAbsent(s.charAt(0), k -> new Node()).add(s.substring(1), t);
            }
        }
    }

    public Node createTree(List<TypeMirror> items) {
        Node node = new Node();
        items.forEach(t -> node.add(t.getAnnotation(Name.class).value(), t));
        return node;
    }

    public void write(PrintWriter w, List<TypeMirror> items) {
        writeSwitch(w, createTree(items));
    }

    private void writeSwitch(PrintWriter w, Node node) {
        node.followers.forEach((c, n) -> {
            w.print("case '" + c + "': ");
            if(n.followers.isEmpty()) {
                w.println("return new Token" + n.type + "(new " + n.type + "(mark));");
            } else {
                if(n.followers.size() == 1) {
                    w.print("match(input, \"");
                    writeChar(w, n);
                } else {
                    w.println("switch(input.move().lookahead()) {");
                    writeSwitch(w, n);
                    w.println("}");
                }
            }
        });
    }

    private void writeChar(PrintWriter w, Node node) {
        node.followers.forEach((c, n) -> {
            w.print(c);
            writeMatch(w, n);
        });
    }
    private void writeMatch(PrintWriter w, Node node) {
        if(node.followers.size() == 1) {
            writeChar(w, node);
        } else {
            w.println("\");");
            if(node.followers.isEmpty()) {
                w.println("return new Token" + node.type + "(new " + node.type + "(mark));");
            } else {
                w.println("switch(input.move().lookahead()) {");
                writeSwitch(w, node);
                w.println("}");
            }
        }
    }
}
