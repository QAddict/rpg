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

package foundation.rpg.gnfa;

import foundation.rpg.dfa.DFA;
import foundation.rpg.dfa.GNFATransformer;
import foundation.rpg.dfa.StateSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static foundation.rpg.dfa.StateSet.isError;

public class ThompsonTest {
    private final Thompson thompson = new Thompson();

    @Test
    public void testRepeatInversion() {
        GNFA repetition = thompson.repetition(thompson.inversions(Stream.of('a', 'b')));
        DFA transform = new GNFATransformer((g, c) -> true).transform(repetition);
        StateSet defaultState = transform.getStart().getDefaultState();
        StateSet nextDefaultState = defaultState.getDefaultState();
        System.out.println(nextDefaultState);
        Assert.assertFalse(isError(defaultState));
        Assert.assertFalse(isError(nextDefaultState));
    }

}
