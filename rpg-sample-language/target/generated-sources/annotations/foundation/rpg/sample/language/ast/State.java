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

package foundation.rpg.sample.language.ast;

/*

N = [Start, Program, List1ListOfStatement, Statement, P1Expression, P2Expression$, Expression$$, List3ListOfExpression, List2ListOfExpression$]
T = [End, Dot, Plus, Times, Identifier, LPar, RPar, Comma]
S = Start
R = {
	Start -> Program,End
	Program -> List1ListOfStatement
	List1ListOfStatement -> 
	List1ListOfStatement -> List1ListOfStatement,Statement
	Statement -> P1Expression,Dot
	P1Expression -> P2Expression$
	P1Expression -> P1Expression,Plus,P2Expression$
	P2Expression$ -> Expression$$
	P2Expression$ -> P2Expression$,Times,Expression$$
	Expression$$ -> Identifier
	Expression$$ -> LPar,Expression$$,RPar
	Expression$$ -> Identifier,LPar,List3ListOfExpression,RPar
	List3ListOfExpression -> 
	List3ListOfExpression -> List2ListOfExpression$
	List2ListOfExpression$ -> Expression$$
	List2ListOfExpression$ -> List2ListOfExpression$,Comma,Expression$$
}

1: {
	Start -> • Program End []
	Program -> • List1ListOfStatement [End]
	List1ListOfStatement -> • [End, Identifier, LPar]
	List1ListOfStatement -> • List1ListOfStatement Statement [End, Identifier, LPar]
}
Program1: {
	Start -> Program • End []
}
List1ListOfStatement1: {
	Program -> List1ListOfStatement • [End]
	List1ListOfStatement -> List1ListOfStatement • Statement [End, Identifier, LPar]
	Statement -> • P1Expression Dot [End, Identifier, LPar]
	P1Expression -> • P2Expression$ [Dot, Plus]
	P1Expression -> • P1Expression Plus P2Expression$ [Dot, Plus]
	P2Expression$ -> • Expression$$ [Dot, Times, Plus]
	P2Expression$ -> • P2Expression$ Times Expression$$ [Dot, Times, Plus]
	Expression$$ -> • Identifier [Dot, Times, Plus]
	Expression$$ -> • LPar Expression$$ RPar [Dot, Times, Plus]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [Dot, Times, Plus]
}
End1: {
	Start -> Program End • []
}
Statement1: {
	List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
}
P1Expression1: {
	Statement -> P1Expression • Dot [End, Identifier, LPar]
	P1Expression -> P1Expression • Plus P2Expression$ [Dot, Plus]
}
P2Expression$1: {
	P1Expression -> P2Expression$ • [Dot, Plus]
	P2Expression$ -> P2Expression$ • Times Expression$$ [Dot, Times, Plus]
}
Expression$$1: {
	P2Expression$ -> Expression$$ • [Dot, Times, Plus]
}
Identifier1: {
	Expression$$ -> Identifier • [Dot, Times, Plus]
	Expression$$ -> Identifier • LPar List3ListOfExpression RPar [Dot, Times, Plus]
}
LPar1: {
	Expression$$ -> LPar • Expression$$ RPar [Dot, Times, Plus]
	Expression$$ -> • Identifier [RPar]
	Expression$$ -> • LPar Expression$$ RPar [RPar]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [RPar]
}
Dot1: {
	Statement -> P1Expression Dot • [End, Identifier, LPar]
}
Plus1: {
	P1Expression -> P1Expression Plus • P2Expression$ [Dot, Plus]
	P2Expression$ -> • Expression$$ [Dot, Plus, Times]
	P2Expression$ -> • P2Expression$ Times Expression$$ [Dot, Plus, Times]
	Expression$$ -> • Identifier [Dot, Plus, Times]
	Expression$$ -> • LPar Expression$$ RPar [Dot, Plus, Times]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [Dot, Plus, Times]
}
Times1: {
	P2Expression$ -> P2Expression$ Times • Expression$$ [Dot, Times, Plus]
	Expression$$ -> • Identifier [Dot, Times, Plus]
	Expression$$ -> • LPar Expression$$ RPar [Dot, Times, Plus]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [Dot, Times, Plus]
}
LPar2: {
	Expression$$ -> Identifier LPar • List3ListOfExpression RPar [Dot, Times, Plus]
	List3ListOfExpression -> • [RPar]
	List3ListOfExpression -> • List2ListOfExpression$ [RPar]
	List2ListOfExpression$ -> • Expression$$ [RPar, Comma]
	List2ListOfExpression$ -> • List2ListOfExpression$ Comma Expression$$ [RPar, Comma]
	Expression$$ -> • Identifier [RPar, Comma]
	Expression$$ -> • LPar Expression$$ RPar [RPar, Comma]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Comma]
}
Expression$$2: {
	Expression$$ -> LPar Expression$$ • RPar [Dot, Times, Plus]
}
Identifier2: {
	Expression$$ -> Identifier • [RPar]
	Expression$$ -> Identifier • LPar List3ListOfExpression RPar [RPar]
}
LPar3: {
	Expression$$ -> LPar • Expression$$ RPar [RPar]
	Expression$$ -> • Identifier [RPar]
	Expression$$ -> • LPar Expression$$ RPar [RPar]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [RPar]
}
P2Expression$2: {
	P1Expression -> P1Expression Plus P2Expression$ • [Dot, Plus]
	P2Expression$ -> P2Expression$ • Times Expression$$ [Dot, Plus, Times]
}
Expression$$4: {
	P2Expression$ -> P2Expression$ Times Expression$$ • [Dot, Times, Plus]
}
List3ListOfExpression1: {
	Expression$$ -> Identifier LPar List3ListOfExpression • RPar [Dot, Times, Plus]
}
List2ListOfExpression$1: {
	List3ListOfExpression -> List2ListOfExpression$ • [RPar]
	List2ListOfExpression$ -> List2ListOfExpression$ • Comma Expression$$ [RPar, Comma]
}
Expression$$5: {
	List2ListOfExpression$ -> Expression$$ • [RPar, Comma]
}
Identifier5: {
	Expression$$ -> Identifier • [RPar, Comma]
	Expression$$ -> Identifier • LPar List3ListOfExpression RPar [RPar, Comma]
}
LPar6: {
	Expression$$ -> LPar • Expression$$ RPar [RPar, Comma]
	Expression$$ -> • Identifier [RPar]
	Expression$$ -> • LPar Expression$$ RPar [RPar]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [RPar]
}
RPar1: {
	Expression$$ -> LPar Expression$$ RPar • [Dot, Times, Plus]
}
LPar7: {
	Expression$$ -> Identifier LPar • List3ListOfExpression RPar [RPar]
	List3ListOfExpression -> • [RPar]
	List3ListOfExpression -> • List2ListOfExpression$ [RPar]
	List2ListOfExpression$ -> • Expression$$ [RPar, Comma]
	List2ListOfExpression$ -> • List2ListOfExpression$ Comma Expression$$ [RPar, Comma]
	Expression$$ -> • Identifier [RPar, Comma]
	Expression$$ -> • LPar Expression$$ RPar [RPar, Comma]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Comma]
}
Expression$$6: {
	Expression$$ -> LPar Expression$$ • RPar [RPar]
}
RPar2: {
	Expression$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
}
Comma1: {
	List2ListOfExpression$ -> List2ListOfExpression$ Comma • Expression$$ [RPar, Comma]
	Expression$$ -> • Identifier [RPar, Comma]
	Expression$$ -> • LPar Expression$$ RPar [RPar, Comma]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Comma]
}
LPar9: {
	Expression$$ -> Identifier LPar • List3ListOfExpression RPar [RPar, Comma]
	List3ListOfExpression -> • [RPar]
	List3ListOfExpression -> • List2ListOfExpression$ [RPar]
	List2ListOfExpression$ -> • Expression$$ [RPar, Comma]
	List2ListOfExpression$ -> • List2ListOfExpression$ Comma Expression$$ [RPar, Comma]
	Expression$$ -> • Identifier [RPar, Comma]
	Expression$$ -> • LPar Expression$$ RPar [RPar, Comma]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Comma]
}
Expression$$7: {
	Expression$$ -> LPar Expression$$ • RPar [RPar, Comma]
}
List3ListOfExpression2: {
	Expression$$ -> Identifier LPar List3ListOfExpression • RPar [RPar]
}
RPar3: {
	Expression$$ -> LPar Expression$$ RPar • [RPar]
}
Expression$$9: {
	List2ListOfExpression$ -> List2ListOfExpression$ Comma Expression$$ • [RPar, Comma]
}
List3ListOfExpression3: {
	Expression$$ -> Identifier LPar List3ListOfExpression • RPar [RPar, Comma]
}
RPar4: {
	Expression$$ -> LPar Expression$$ RPar • [RPar, Comma]
}
RPar5: {
	Expression$$ -> Identifier LPar List3ListOfExpression RPar • [RPar]
}
RPar6: {
	Expression$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Comma]
}

1: End -> REDUCE: List1ListOfStatement -> • [End, Identifier, LPar]
1: Identifier -> REDUCE: List1ListOfStatement -> • [End, Identifier, LPar]
1: LPar -> REDUCE: List1ListOfStatement -> • [End, Identifier, LPar]
1: Program -> GOTO: Program1
1: List1ListOfStatement -> GOTO: List1ListOfStatement1
Program1: End -> GOTO: End1
List1ListOfStatement1: End -> REDUCE: Program -> List1ListOfStatement • [End]
List1ListOfStatement1: Statement -> GOTO: Statement1
List1ListOfStatement1: P1Expression -> GOTO: P1Expression1
List1ListOfStatement1: P2Expression$ -> GOTO: P2Expression$1
List1ListOfStatement1: Expression$$ -> GOTO: Expression$$1
List1ListOfStatement1: Identifier -> GOTO: Identifier1
List1ListOfStatement1: LPar -> GOTO: LPar1
End1:  -> ACCEPT: Start -> Program End • []
Statement1: End -> REDUCE: List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
Statement1: Identifier -> REDUCE: List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
Statement1: LPar -> REDUCE: List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
P1Expression1: Dot -> GOTO: Dot1
P1Expression1: Plus -> GOTO: Plus1
P2Expression$1: Dot -> REDUCE: P1Expression -> P2Expression$ • [Dot, Plus]
P2Expression$1: Plus -> REDUCE: P1Expression -> P2Expression$ • [Dot, Plus]
P2Expression$1: Times -> GOTO: Times1
Expression$$1: Dot -> REDUCE: P2Expression$ -> Expression$$ • [Dot, Times, Plus]
Expression$$1: Times -> REDUCE: P2Expression$ -> Expression$$ • [Dot, Times, Plus]
Expression$$1: Plus -> REDUCE: P2Expression$ -> Expression$$ • [Dot, Times, Plus]
Identifier1: Dot -> REDUCE: Expression$$ -> Identifier • [Dot, Times, Plus]
Identifier1: Times -> REDUCE: Expression$$ -> Identifier • [Dot, Times, Plus]
Identifier1: Plus -> REDUCE: Expression$$ -> Identifier • [Dot, Times, Plus]
Identifier1: LPar -> GOTO: LPar2
LPar1: Expression$$ -> GOTO: Expression$$2
LPar1: Identifier -> GOTO: Identifier2
LPar1: LPar -> GOTO: LPar3
Dot1: End -> REDUCE: Statement -> P1Expression Dot • [End, Identifier, LPar]
Dot1: Identifier -> REDUCE: Statement -> P1Expression Dot • [End, Identifier, LPar]
Dot1: LPar -> REDUCE: Statement -> P1Expression Dot • [End, Identifier, LPar]
Plus1: P2Expression$ -> GOTO: P2Expression$2
Plus1: Expression$$ -> GOTO: Expression$$1
Plus1: Identifier -> GOTO: Identifier1
Plus1: LPar -> GOTO: LPar1
Times1: Expression$$ -> GOTO: Expression$$4
Times1: Identifier -> GOTO: Identifier1
Times1: LPar -> GOTO: LPar1
LPar2: RPar -> REDUCE: List3ListOfExpression -> • [RPar]
LPar2: List3ListOfExpression -> GOTO: List3ListOfExpression1
LPar2: List2ListOfExpression$ -> GOTO: List2ListOfExpression$1
LPar2: Expression$$ -> GOTO: Expression$$5
LPar2: Identifier -> GOTO: Identifier5
LPar2: LPar -> GOTO: LPar6
Expression$$2: RPar -> GOTO: RPar1
Identifier2: RPar -> REDUCE: Expression$$ -> Identifier • [RPar]
Identifier2: LPar -> GOTO: LPar7
LPar3: Expression$$ -> GOTO: Expression$$6
LPar3: Identifier -> GOTO: Identifier2
LPar3: LPar -> GOTO: LPar3
P2Expression$2: Dot -> REDUCE: P1Expression -> P1Expression Plus P2Expression$ • [Dot, Plus]
P2Expression$2: Plus -> REDUCE: P1Expression -> P1Expression Plus P2Expression$ • [Dot, Plus]
P2Expression$2: Times -> GOTO: Times1
Expression$$4: Dot -> REDUCE: P2Expression$ -> P2Expression$ Times Expression$$ • [Dot, Times, Plus]
Expression$$4: Times -> REDUCE: P2Expression$ -> P2Expression$ Times Expression$$ • [Dot, Times, Plus]
Expression$$4: Plus -> REDUCE: P2Expression$ -> P2Expression$ Times Expression$$ • [Dot, Times, Plus]
List3ListOfExpression1: RPar -> GOTO: RPar2
List2ListOfExpression$1: RPar -> REDUCE: List3ListOfExpression -> List2ListOfExpression$ • [RPar]
List2ListOfExpression$1: Comma -> GOTO: Comma1
Expression$$5: RPar -> REDUCE: List2ListOfExpression$ -> Expression$$ • [RPar, Comma]
Expression$$5: Comma -> REDUCE: List2ListOfExpression$ -> Expression$$ • [RPar, Comma]
Identifier5: RPar -> REDUCE: Expression$$ -> Identifier • [RPar, Comma]
Identifier5: Comma -> REDUCE: Expression$$ -> Identifier • [RPar, Comma]
Identifier5: LPar -> GOTO: LPar9
LPar6: Expression$$ -> GOTO: Expression$$7
LPar6: Identifier -> GOTO: Identifier2
LPar6: LPar -> GOTO: LPar3
RPar1: Dot -> REDUCE: Expression$$ -> LPar Expression$$ RPar • [Dot, Times, Plus]
RPar1: Times -> REDUCE: Expression$$ -> LPar Expression$$ RPar • [Dot, Times, Plus]
RPar1: Plus -> REDUCE: Expression$$ -> LPar Expression$$ RPar • [Dot, Times, Plus]
LPar7: RPar -> REDUCE: List3ListOfExpression -> • [RPar]
LPar7: List3ListOfExpression -> GOTO: List3ListOfExpression2
LPar7: List2ListOfExpression$ -> GOTO: List2ListOfExpression$1
LPar7: Expression$$ -> GOTO: Expression$$5
LPar7: Identifier -> GOTO: Identifier5
LPar7: LPar -> GOTO: LPar6
Expression$$6: RPar -> GOTO: RPar3
RPar2: Dot -> REDUCE: Expression$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
RPar2: Times -> REDUCE: Expression$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
RPar2: Plus -> REDUCE: Expression$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
Comma1: Expression$$ -> GOTO: Expression$$9
Comma1: Identifier -> GOTO: Identifier5
Comma1: LPar -> GOTO: LPar6
LPar9: RPar -> REDUCE: List3ListOfExpression -> • [RPar]
LPar9: List3ListOfExpression -> GOTO: List3ListOfExpression3
LPar9: List2ListOfExpression$ -> GOTO: List2ListOfExpression$1
LPar9: Expression$$ -> GOTO: Expression$$5
LPar9: Identifier -> GOTO: Identifier5
LPar9: LPar -> GOTO: LPar6
Expression$$7: RPar -> GOTO: RPar4
List3ListOfExpression2: RPar -> GOTO: RPar5
RPar3: RPar -> REDUCE: Expression$$ -> LPar Expression$$ RPar • [RPar]
Expression$$9: RPar -> REDUCE: List2ListOfExpression$ -> List2ListOfExpression$ Comma Expression$$ • [RPar, Comma]
Expression$$9: Comma -> REDUCE: List2ListOfExpression$ -> List2ListOfExpression$ Comma Expression$$ • [RPar, Comma]
List3ListOfExpression3: RPar -> GOTO: RPar6
RPar4: RPar -> REDUCE: Expression$$ -> LPar Expression$$ RPar • [RPar, Comma]
RPar4: Comma -> REDUCE: Expression$$ -> LPar Expression$$ RPar • [RPar, Comma]
RPar5: RPar -> REDUCE: Expression$$ -> Identifier LPar List3ListOfExpression RPar • [RPar]
RPar6: RPar -> REDUCE: Expression$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Comma]
RPar6: Comma -> REDUCE: Expression$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Comma]

*/

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<foundation.rpg.sample.language.ast.Program> {

// Ignored:
    public State visitWhiteSpace(foundation.rpg.common.WhiteSpace symbol) {
        return this;
    }

    public State visitComment(foundation.rpg.common.Comment symbol) {
        return this;
    }


// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitDot(foundation.rpg.common.Dot symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitPlus(foundation.rpg.common.Plus symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitTimes(foundation.rpg.common.Times symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLPar(foundation.rpg.common.LPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRPar(foundation.rpg.common.RPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitComma(foundation.rpg.common.Comma symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitProgram(foundation.rpg.sample.language.ast.Program symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList1ListOfStatement(java.util.List<foundation.rpg.sample.language.ast.Statement> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitStatement(foundation.rpg.sample.language.ast.Statement symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitP1Expression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitP2Expression$(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitExpression$$(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList3ListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList2ListOfExpression$(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
