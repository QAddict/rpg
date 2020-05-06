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

package foundation.rpg.sample.json;

import foundation.rpg.parser.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonParserTest {

    private final JsonParser parser = new JsonParser();

    @Test
    public void testJson() throws IOException, ParseErrorException {
        Assert.assertTrue(parser.parse("[]") instanceof List);
        Assert.assertTrue(parser.parse("[4]") instanceof List);
        Assert.assertTrue(parser.parse("[5,\"a\"]") instanceof List);
        Assert.assertTrue(parser.parse("{}") instanceof Map);
        Assert.assertEquals(parser.parse("1") , 1);
        Assert.assertEquals(parser.parse("\"1\"") , "1");
        Assert.assertEquals(1, ((Map) parser.parse("{a:1,b:3}")).get("a"));
    }

    @Test(expectedExceptions = ParseErrorException.class, expectedExceptionsMessageRegExp = "Parse error: Duplicate key: a\n" +
            "\tat json: line: 1, character: 9")
    public void testError() throws IOException, ParseErrorException {
        Assert.assertEquals(1, ((Map) parser.parse("{a:1,a:3}")).get("a"));
    }

}
