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

package foundation.rpg.parser;


/**
 * Description of token identified by Lexer.
 *
 * It contains full information about it's location and content:
 *
 *  - file name
 *  - start line, column, position
 *  - end line, column, position
 *  - string content matched exactly in the file without any processing (e.g. matching string literal ".." still
 *    contains the quotes)
 *
 */
public final class Token {

    /**
     * File name (or provided name for non-file streams), where the token was found.
     */
    private final String fileName;

    /**
     * Line in the input, on which the token starts.
     */
    private final int startLine;

    /**
     * Column on the line, where the token content starts.
     */
    private final int startCol;

    /**
     * Position (character-wise) of start of the token in the file (including number of characters of all previous lines).
     */
    private final int startPos;

    /**
     * Line in the input, on which the token ends.
     */
    private final int endLine;

    /**
     * Column on the line, where the token content ends.
     */
    private final int endCol;

    /**
     * Position (character-wise) of end of the token in the file (including number of characters of all previous lines).
     */
    private final int endPos;

    /**
     * String content as-is identified in the source file.
     */
    private final String content;

    /**
     * Constructor of the token description.
     *
     * @param fileName File name (or provided name for non-file streams), where the token was found.
     * @param startLine Line in the input, on which the token starts.
     * @param startCol Column on the line, where the token content starts.
     * @param startPos Position (character-wise) of start of the token in the file (including number of characters of all previous lines).
     * @param endLine Line in the input, on which the token ends.
     * @param endChar Column on the line, where the token content ends.
     * @param endPos Position (character-wise) of end of the token in the file (including number of characters of all previous lines).
     * @param content String content as-is identified in the source file.
     */
    public Token(String fileName, int startLine, int startCol, int startPos, int endLine, int endChar, int endPos, String content) {
        this.fileName = fileName;
        this.startLine = startLine;
        this.startCol = startCol;
        this.startPos = startPos;
        this.endLine = endLine;
        this.endCol = endChar;
        this.endPos = endPos;
        this.content = content;
    }

    /**
     * @return File name (or provided name for non-file streams), where the token was found.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return Line in the input, on which the token starts.
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * @return Column on the line, where the token content starts.
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * @return Position (character-wise) of start of the token in the file (including number of characters of all previous lines).
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * @return Line in the input, on which the token ends.
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * @return Column on the line, where the token content ends.
     */
    public int getEndCol() {
        return endCol;
    }

    /**
     * @return Position (character-wise) of end of the token in the file (including number of characters of all previous lines).
     */
    public int getEndPos() {
        return endPos;
    }

    /**
     * @return String content as-is identified in the source file.
     */
    public String getContent() {
        return content;
    }

    /**
     * @return Length of the identified token.
     */
    public int length() {
        return endPos - startPos;
    }

    /**
     * @return String content as-is identified in the source file.
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
