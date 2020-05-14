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

package foundation.rpg.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class SourceModel {

    private final String dir;
    private final String name;
    private final Map<String, Object> variables = new LinkedHashMap<>();
    private final Map<String, List<SourceModel>> fragments = new LinkedHashMap<>();

    private SourceModel(String dir, String name) {
        this.dir = dir;
        this.name = name;
    }

    public static SourceModel source(String pkg, boolean isStaticFactory, String name) {
        return new SourceModel(isStaticFactory ? "static" : "instance", name).set("package", pkg);
    }

    public SourceModel set(Name name, Object value) {
        return set(name.toString(), value);
    }

    public SourceModel set(String name, Object value) {
        variables.put(name, value);
        return this;
    }

    public Object get(String name) {
        return variables.get(name);
    }

    public SourceModel with(Name name) {
        SourceModel fragment = new SourceModel(dir, name.toString());
        fragments.computeIfAbsent(name.toString(), n -> new ArrayList<>()).add(fragment);
        return fragment;
    }

    private String get(Map<String, String> templates, String template) {
        String result = template;
        for(Map.Entry<String, List<SourceModel>> entry : fragments.entrySet()) {
            String fragmentTemplate = templates.getOrDefault(entry.getKey() + ":", "");
            result = result.replace("// " + entry.getKey() + ":", "// " + entry.getKey() + ":\n" + entry.getValue().stream().map(f -> f.get(templates, fragmentTemplate)).collect(joining()));
        }
        return apply(variables, result);
    }

    private static String apply(Map<String, Object> variables, String on) {
        for(Map.Entry<String, Object> var : variables.entrySet()) {
            on = on.replace("$" + var.getKey() + "$", var.getValue().toString());
        }
        return on;
    }

    @Override
    public String toString() {
        Map<String, String> templates = load(dir, name);
        String className = apply(variables, name);
        return set("class", className).get(templates, templates.getOrDefault("", ""));
    }

    private static final Pattern FRAGMENT = Pattern.compile("// (.*):");

    static Map<String, String> load(String dir, String type) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(SourceModel.class.getResourceAsStream("/templates/" + dir + "/" + type + ".java")))) {
            Map<String, String> map = new LinkedHashMap<>();
            String name = "";
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                map.put(name, map.getOrDefault(name, "") + line + "\n");
                if(FRAGMENT.matcher(line).find()) {
                    name = line.substring(line.indexOf("// ") + 3).trim();
                }
                if(line.trim().equals("")) {
                    name = "";
                }
            }
            return map;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public interface Name {}

    public enum Names implements Name {
        name, type, grammar, item, automata, node, prev, next, result, factoryCall, factory, start, parameters,
        Ignored, Symbols, lrItem, parent, Stack, NoStack, Shift, Reduce, Accept
    }
}
