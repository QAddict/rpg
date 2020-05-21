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

N = [Start, Program, List1ListOfStatement, Statement, Expression, P1Expression$, P2Expression$$, P3Expression$$$, List3ListOfExpression, List2ListOfExpression$]
T = [End, Dot, Plus, Times, Identifier, LPar, RPar, Comma]
S = Start
R = {
	Start -> Program,End
	Program -> List1ListOfStatement
	List1ListOfStatement -> 
	List1ListOfStatement -> List1ListOfStatement,Statement
	Statement -> Expression,Dot
	Expression -> P1Expression$
	P1Expression$ -> P2Expression$$
	P1Expression$ -> P1Expression$,Plus,P2Expression$$
	P2Expression$$ -> P3Expression$$$
	P2Expression$$ -> P2Expression$$,Times,P3Expression$$$
	P3Expression$$$ -> Identifier
	P3Expression$$$ -> LPar,Expression,RPar
	P3Expression$$$ -> Identifier,LPar,List3ListOfExpression,RPar
	List3ListOfExpression -> 
	List3ListOfExpression -> List2ListOfExpression$
	List2ListOfExpression$ -> Expression
	List2ListOfExpression$ -> List2ListOfExpression$,Comma,Expression
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
	Statement -> • Expression Dot [End, Identifier, LPar]
	Expression -> • P1Expression$ [Dot]
	P1Expression$ -> • P2Expression$$ [Dot, Plus]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [Dot, Plus]
	P2Expression$$ -> • P3Expression$$$ [Dot, Times, Plus]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [Dot, Times, Plus]
	P3Expression$$$ -> • Identifier [Dot, Times, Plus]
	P3Expression$$$ -> • LPar Expression RPar [Dot, Times, Plus]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [Dot, Times, Plus]
}
End1: {
	Start -> Program End • []
}
Statement1: {
	List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
}
Expression1: {
	Statement -> Expression • Dot [End, Identifier, LPar]
}
P1Expression$1: {
	Expression -> P1Expression$ • [Dot]
	P1Expression$ -> P1Expression$ • Plus P2Expression$$ [Dot, Plus]
}
P2Expression$$1: {
	P1Expression$ -> P2Expression$$ • [Dot, Plus]
	P2Expression$$ -> P2Expression$$ • Times P3Expression$$$ [Dot, Times, Plus]
}
P3Expression$$$1: {
	P2Expression$$ -> P3Expression$$$ • [Dot, Times, Plus]
}
Identifier1: {
	P3Expression$$$ -> Identifier • [Dot, Times, Plus]
	P3Expression$$$ -> Identifier • LPar List3ListOfExpression RPar [Dot, Times, Plus]
}
LPar1: {
	P3Expression$$$ -> LPar • Expression RPar [Dot, Times, Plus]
	Expression -> • P1Expression$ [RPar]
	P1Expression$ -> • P2Expression$$ [RPar, Plus]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [RPar, Plus]
	P2Expression$$ -> • P3Expression$$$ [RPar, Times, Plus]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus]
}
Dot1: {
	Statement -> Expression Dot • [End, Identifier, LPar]
}
Plus1: {
	P1Expression$ -> P1Expression$ Plus • P2Expression$$ [Dot, Plus]
	P2Expression$$ -> • P3Expression$$$ [Dot, Plus, Times]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [Dot, Plus, Times]
	P3Expression$$$ -> • Identifier [Dot, Plus, Times]
	P3Expression$$$ -> • LPar Expression RPar [Dot, Plus, Times]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [Dot, Plus, Times]
}
Times1: {
	P2Expression$$ -> P2Expression$$ Times • P3Expression$$$ [Dot, Times, Plus]
	P3Expression$$$ -> • Identifier [Dot, Times, Plus]
	P3Expression$$$ -> • LPar Expression RPar [Dot, Times, Plus]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [Dot, Times, Plus]
}
LPar2: {
	P3Expression$$$ -> Identifier LPar • List3ListOfExpression RPar [Dot, Times, Plus]
	List3ListOfExpression -> • [RPar]
	List3ListOfExpression -> • List2ListOfExpression$ [RPar]
	List2ListOfExpression$ -> • Expression [RPar, Comma]
	List2ListOfExpression$ -> • List2ListOfExpression$ Comma Expression [RPar, Comma]
	Expression -> • P1Expression$ [RPar, Comma]
	P1Expression$ -> • P2Expression$$ [RPar, Plus, Comma]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [RPar, Plus, Comma]
	P2Expression$$ -> • P3Expression$$$ [RPar, Times, Plus, Comma]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus, Comma]
}
Expression2: {
	P3Expression$$$ -> LPar Expression • RPar [Dot, Times, Plus]
}
P1Expression$2: {
	Expression -> P1Expression$ • [RPar]
	P1Expression$ -> P1Expression$ • Plus P2Expression$$ [RPar, Plus]
}
P2Expression$$2: {
	P1Expression$ -> P2Expression$$ • [RPar, Plus]
	P2Expression$$ -> P2Expression$$ • Times P3Expression$$$ [RPar, Times, Plus]
}
P3Expression$$$2: {
	P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus]
}
Identifier2: {
	P3Expression$$$ -> Identifier • [RPar, Times, Plus]
	P3Expression$$$ -> Identifier • LPar List3ListOfExpression RPar [RPar, Times, Plus]
}
LPar3: {
	P3Expression$$$ -> LPar • Expression RPar [RPar, Times, Plus]
	Expression -> • P1Expression$ [RPar]
	P1Expression$ -> • P2Expression$$ [RPar, Plus]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [RPar, Plus]
	P2Expression$$ -> • P3Expression$$$ [RPar, Times, Plus]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus]
}
P2Expression$$3: {
	P1Expression$ -> P1Expression$ Plus P2Expression$$ • [Dot, Plus]
	P2Expression$$ -> P2Expression$$ • Times P3Expression$$$ [Dot, Plus, Times]
}
P3Expression$$$4: {
	P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [Dot, Times, Plus]
}
List3ListOfExpression1: {
	P3Expression$$$ -> Identifier LPar List3ListOfExpression • RPar [Dot, Times, Plus]
}
List2ListOfExpression$1: {
	List3ListOfExpression -> List2ListOfExpression$ • [RPar]
	List2ListOfExpression$ -> List2ListOfExpression$ • Comma Expression [RPar, Comma]
}
Expression3: {
	List2ListOfExpression$ -> Expression • [RPar, Comma]
}
P1Expression$3: {
	Expression -> P1Expression$ • [RPar, Comma]
	P1Expression$ -> P1Expression$ • Plus P2Expression$$ [RPar, Plus, Comma]
}
P2Expression$$4: {
	P1Expression$ -> P2Expression$$ • [RPar, Plus, Comma]
	P2Expression$$ -> P2Expression$$ • Times P3Expression$$$ [RPar, Times, Plus, Comma]
}
P3Expression$$$5: {
	P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus, Comma]
}
Identifier5: {
	P3Expression$$$ -> Identifier • [RPar, Times, Plus, Comma]
	P3Expression$$$ -> Identifier • LPar List3ListOfExpression RPar [RPar, Times, Plus, Comma]
}
LPar6: {
	P3Expression$$$ -> LPar • Expression RPar [RPar, Times, Plus, Comma]
	Expression -> • P1Expression$ [RPar]
	P1Expression$ -> • P2Expression$$ [RPar, Plus]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [RPar, Plus]
	P2Expression$$ -> • P3Expression$$$ [RPar, Times, Plus]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus]
}
RPar1: {
	P3Expression$$$ -> LPar Expression RPar • [Dot, Times, Plus]
}
Plus2: {
	P1Expression$ -> P1Expression$ Plus • P2Expression$$ [RPar, Plus]
	P2Expression$$ -> • P3Expression$$$ [RPar, Plus, Times]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Plus, Times]
	P3Expression$$$ -> • Identifier [RPar, Plus, Times]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Plus, Times]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Plus, Times]
}
Times2: {
	P2Expression$$ -> P2Expression$$ Times • P3Expression$$$ [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus]
}
LPar7: {
	P3Expression$$$ -> Identifier LPar • List3ListOfExpression RPar [RPar, Times, Plus]
	List3ListOfExpression -> • [RPar]
	List3ListOfExpression -> • List2ListOfExpression$ [RPar]
	List2ListOfExpression$ -> • Expression [RPar, Comma]
	List2ListOfExpression$ -> • List2ListOfExpression$ Comma Expression [RPar, Comma]
	Expression -> • P1Expression$ [RPar, Comma]
	P1Expression$ -> • P2Expression$$ [RPar, Plus, Comma]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [RPar, Plus, Comma]
	P2Expression$$ -> • P3Expression$$$ [RPar, Times, Plus, Comma]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus, Comma]
}
Expression4: {
	P3Expression$$$ -> LPar Expression • RPar [RPar, Times, Plus]
}
RPar2: {
	P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
}
Comma1: {
	List2ListOfExpression$ -> List2ListOfExpression$ Comma • Expression [RPar, Comma]
	Expression -> • P1Expression$ [RPar, Comma]
	P1Expression$ -> • P2Expression$$ [RPar, Comma, Plus]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [RPar, Comma, Plus]
	P2Expression$$ -> • P3Expression$$$ [RPar, Comma, Times, Plus]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Comma, Times, Plus]
	P3Expression$$$ -> • Identifier [RPar, Comma, Times, Plus]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Comma, Times, Plus]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Comma, Times, Plus]
}
Plus3: {
	P1Expression$ -> P1Expression$ Plus • P2Expression$$ [RPar, Plus, Comma]
	P2Expression$$ -> • P3Expression$$$ [RPar, Plus, Comma, Times]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Plus, Comma, Times]
	P3Expression$$$ -> • Identifier [RPar, Plus, Comma, Times]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Plus, Comma, Times]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Plus, Comma, Times]
}
Times4: {
	P2Expression$$ -> P2Expression$$ Times • P3Expression$$$ [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus, Comma]
}
LPar9: {
	P3Expression$$$ -> Identifier LPar • List3ListOfExpression RPar [RPar, Times, Plus, Comma]
	List3ListOfExpression -> • [RPar]
	List3ListOfExpression -> • List2ListOfExpression$ [RPar]
	List2ListOfExpression$ -> • Expression [RPar, Comma]
	List2ListOfExpression$ -> • List2ListOfExpression$ Comma Expression [RPar, Comma]
	Expression -> • P1Expression$ [RPar, Comma]
	P1Expression$ -> • P2Expression$$ [RPar, Plus, Comma]
	P1Expression$ -> • P1Expression$ Plus P2Expression$$ [RPar, Plus, Comma]
	P2Expression$$ -> • P3Expression$$$ [RPar, Times, Plus, Comma]
	P2Expression$$ -> • P2Expression$$ Times P3Expression$$$ [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • LPar Expression RPar [RPar, Times, Plus, Comma]
	P3Expression$$$ -> • Identifier LPar List3ListOfExpression RPar [RPar, Times, Plus, Comma]
}
Expression5: {
	P3Expression$$$ -> LPar Expression • RPar [RPar, Times, Plus, Comma]
}
P2Expression$$7: {
	P1Expression$ -> P1Expression$ Plus P2Expression$$ • [RPar, Plus]
	P2Expression$$ -> P2Expression$$ • Times P3Expression$$$ [RPar, Plus, Times]
}
P3Expression$$$9: {
	P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus]
}
List3ListOfExpression2: {
	P3Expression$$$ -> Identifier LPar List3ListOfExpression • RPar [RPar, Times, Plus]
}
RPar3: {
	P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus]
}
Expression7: {
	List2ListOfExpression$ -> List2ListOfExpression$ Comma Expression • [RPar, Comma]
}
P2Expression$$10: {
	P1Expression$ -> P1Expression$ Plus P2Expression$$ • [RPar, Plus, Comma]
	P2Expression$$ -> P2Expression$$ • Times P3Expression$$$ [RPar, Plus, Comma, Times]
}
P3Expression$$$13: {
	P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus, Comma]
}
List3ListOfExpression3: {
	P3Expression$$$ -> Identifier LPar List3ListOfExpression • RPar [RPar, Times, Plus, Comma]
}
RPar4: {
	P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus, Comma]
}
RPar5: {
	P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus]
}
RPar6: {
	P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus, Comma]
}

1: End -> REDUCE: List1ListOfStatement -> • [End, Identifier, LPar]
1: Identifier -> REDUCE: List1ListOfStatement -> • [End, Identifier, LPar]
1: LPar -> REDUCE: List1ListOfStatement -> • [End, Identifier, LPar]
1: Program -> GOTO: Program1
1: List1ListOfStatement -> GOTO: List1ListOfStatement1
Program1: End -> GOTO: End1
List1ListOfStatement1: End -> REDUCE: Program -> List1ListOfStatement • [End]
List1ListOfStatement1: Statement -> GOTO: Statement1
List1ListOfStatement1: Expression -> GOTO: Expression1
List1ListOfStatement1: P1Expression$ -> GOTO: P1Expression$1
List1ListOfStatement1: P2Expression$$ -> GOTO: P2Expression$$1
List1ListOfStatement1: P3Expression$$$ -> GOTO: P3Expression$$$1
List1ListOfStatement1: Identifier -> GOTO: Identifier1
List1ListOfStatement1: LPar -> GOTO: LPar1
End1:  -> ACCEPT: Start -> Program End • []
Statement1: End -> REDUCE: List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
Statement1: Identifier -> REDUCE: List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
Statement1: LPar -> REDUCE: List1ListOfStatement -> List1ListOfStatement Statement • [End, Identifier, LPar]
Expression1: Dot -> GOTO: Dot1
P1Expression$1: Dot -> REDUCE: Expression -> P1Expression$ • [Dot]
P1Expression$1: Plus -> GOTO: Plus1
P2Expression$$1: Dot -> REDUCE: P1Expression$ -> P2Expression$$ • [Dot, Plus]
P2Expression$$1: Plus -> REDUCE: P1Expression$ -> P2Expression$$ • [Dot, Plus]
P2Expression$$1: Times -> GOTO: Times1
P3Expression$$$1: Dot -> REDUCE: P2Expression$$ -> P3Expression$$$ • [Dot, Times, Plus]
P3Expression$$$1: Times -> REDUCE: P2Expression$$ -> P3Expression$$$ • [Dot, Times, Plus]
P3Expression$$$1: Plus -> REDUCE: P2Expression$$ -> P3Expression$$$ • [Dot, Times, Plus]
Identifier1: Dot -> REDUCE: P3Expression$$$ -> Identifier • [Dot, Times, Plus]
Identifier1: Times -> REDUCE: P3Expression$$$ -> Identifier • [Dot, Times, Plus]
Identifier1: Plus -> REDUCE: P3Expression$$$ -> Identifier • [Dot, Times, Plus]
Identifier1: LPar -> GOTO: LPar2
LPar1: Expression -> GOTO: Expression2
LPar1: P1Expression$ -> GOTO: P1Expression$2
LPar1: P2Expression$$ -> GOTO: P2Expression$$2
LPar1: P3Expression$$$ -> GOTO: P3Expression$$$2
LPar1: Identifier -> GOTO: Identifier2
LPar1: LPar -> GOTO: LPar3
Dot1: End -> REDUCE: Statement -> Expression Dot • [End, Identifier, LPar]
Dot1: Identifier -> REDUCE: Statement -> Expression Dot • [End, Identifier, LPar]
Dot1: LPar -> REDUCE: Statement -> Expression Dot • [End, Identifier, LPar]
Plus1: P2Expression$$ -> GOTO: P2Expression$$3
Plus1: P3Expression$$$ -> GOTO: P3Expression$$$1
Plus1: Identifier -> GOTO: Identifier1
Plus1: LPar -> GOTO: LPar1
Times1: P3Expression$$$ -> GOTO: P3Expression$$$4
Times1: Identifier -> GOTO: Identifier1
Times1: LPar -> GOTO: LPar1
LPar2: RPar -> REDUCE: List3ListOfExpression -> • [RPar]
LPar2: List3ListOfExpression -> GOTO: List3ListOfExpression1
LPar2: List2ListOfExpression$ -> GOTO: List2ListOfExpression$1
LPar2: Expression -> GOTO: Expression3
LPar2: P1Expression$ -> GOTO: P1Expression$3
LPar2: P2Expression$$ -> GOTO: P2Expression$$4
LPar2: P3Expression$$$ -> GOTO: P3Expression$$$5
LPar2: Identifier -> GOTO: Identifier5
LPar2: LPar -> GOTO: LPar6
Expression2: RPar -> GOTO: RPar1
P1Expression$2: RPar -> REDUCE: Expression -> P1Expression$ • [RPar]
P1Expression$2: Plus -> GOTO: Plus2
P2Expression$$2: RPar -> REDUCE: P1Expression$ -> P2Expression$$ • [RPar, Plus]
P2Expression$$2: Plus -> REDUCE: P1Expression$ -> P2Expression$$ • [RPar, Plus]
P2Expression$$2: Times -> GOTO: Times2
P3Expression$$$2: RPar -> REDUCE: P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus]
P3Expression$$$2: Times -> REDUCE: P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus]
P3Expression$$$2: Plus -> REDUCE: P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus]
Identifier2: RPar -> REDUCE: P3Expression$$$ -> Identifier • [RPar, Times, Plus]
Identifier2: Times -> REDUCE: P3Expression$$$ -> Identifier • [RPar, Times, Plus]
Identifier2: Plus -> REDUCE: P3Expression$$$ -> Identifier • [RPar, Times, Plus]
Identifier2: LPar -> GOTO: LPar7
LPar3: Expression -> GOTO: Expression4
LPar3: P1Expression$ -> GOTO: P1Expression$2
LPar3: P2Expression$$ -> GOTO: P2Expression$$2
LPar3: P3Expression$$$ -> GOTO: P3Expression$$$2
LPar3: Identifier -> GOTO: Identifier2
LPar3: LPar -> GOTO: LPar3
P2Expression$$3: Dot -> REDUCE: P1Expression$ -> P1Expression$ Plus P2Expression$$ • [Dot, Plus]
P2Expression$$3: Plus -> REDUCE: P1Expression$ -> P1Expression$ Plus P2Expression$$ • [Dot, Plus]
P2Expression$$3: Times -> GOTO: Times1
P3Expression$$$4: Dot -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [Dot, Times, Plus]
P3Expression$$$4: Times -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [Dot, Times, Plus]
P3Expression$$$4: Plus -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [Dot, Times, Plus]
List3ListOfExpression1: RPar -> GOTO: RPar2
List2ListOfExpression$1: RPar -> REDUCE: List3ListOfExpression -> List2ListOfExpression$ • [RPar]
List2ListOfExpression$1: Comma -> GOTO: Comma1
Expression3: RPar -> REDUCE: List2ListOfExpression$ -> Expression • [RPar, Comma]
Expression3: Comma -> REDUCE: List2ListOfExpression$ -> Expression • [RPar, Comma]
P1Expression$3: RPar -> REDUCE: Expression -> P1Expression$ • [RPar, Comma]
P1Expression$3: Comma -> REDUCE: Expression -> P1Expression$ • [RPar, Comma]
P1Expression$3: Plus -> GOTO: Plus3
P2Expression$$4: RPar -> REDUCE: P1Expression$ -> P2Expression$$ • [RPar, Plus, Comma]
P2Expression$$4: Plus -> REDUCE: P1Expression$ -> P2Expression$$ • [RPar, Plus, Comma]
P2Expression$$4: Comma -> REDUCE: P1Expression$ -> P2Expression$$ • [RPar, Plus, Comma]
P2Expression$$4: Times -> GOTO: Times4
P3Expression$$$5: RPar -> REDUCE: P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus, Comma]
P3Expression$$$5: Times -> REDUCE: P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus, Comma]
P3Expression$$$5: Plus -> REDUCE: P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus, Comma]
P3Expression$$$5: Comma -> REDUCE: P2Expression$$ -> P3Expression$$$ • [RPar, Times, Plus, Comma]
Identifier5: RPar -> REDUCE: P3Expression$$$ -> Identifier • [RPar, Times, Plus, Comma]
Identifier5: Times -> REDUCE: P3Expression$$$ -> Identifier • [RPar, Times, Plus, Comma]
Identifier5: Plus -> REDUCE: P3Expression$$$ -> Identifier • [RPar, Times, Plus, Comma]
Identifier5: Comma -> REDUCE: P3Expression$$$ -> Identifier • [RPar, Times, Plus, Comma]
Identifier5: LPar -> GOTO: LPar9
LPar6: Expression -> GOTO: Expression5
LPar6: P1Expression$ -> GOTO: P1Expression$2
LPar6: P2Expression$$ -> GOTO: P2Expression$$2
LPar6: P3Expression$$$ -> GOTO: P3Expression$$$2
LPar6: Identifier -> GOTO: Identifier2
LPar6: LPar -> GOTO: LPar3
RPar1: Dot -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [Dot, Times, Plus]
RPar1: Times -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [Dot, Times, Plus]
RPar1: Plus -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [Dot, Times, Plus]
Plus2: P2Expression$$ -> GOTO: P2Expression$$7
Plus2: P3Expression$$$ -> GOTO: P3Expression$$$2
Plus2: Identifier -> GOTO: Identifier2
Plus2: LPar -> GOTO: LPar3
Times2: P3Expression$$$ -> GOTO: P3Expression$$$9
Times2: Identifier -> GOTO: Identifier2
Times2: LPar -> GOTO: LPar3
LPar7: RPar -> REDUCE: List3ListOfExpression -> • [RPar]
LPar7: List3ListOfExpression -> GOTO: List3ListOfExpression2
LPar7: List2ListOfExpression$ -> GOTO: List2ListOfExpression$1
LPar7: Expression -> GOTO: Expression3
LPar7: P1Expression$ -> GOTO: P1Expression$3
LPar7: P2Expression$$ -> GOTO: P2Expression$$4
LPar7: P3Expression$$$ -> GOTO: P3Expression$$$5
LPar7: Identifier -> GOTO: Identifier5
LPar7: LPar -> GOTO: LPar6
Expression4: RPar -> GOTO: RPar3
RPar2: Dot -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
RPar2: Times -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
RPar2: Plus -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [Dot, Times, Plus]
Comma1: Expression -> GOTO: Expression7
Comma1: P1Expression$ -> GOTO: P1Expression$3
Comma1: P2Expression$$ -> GOTO: P2Expression$$4
Comma1: P3Expression$$$ -> GOTO: P3Expression$$$5
Comma1: Identifier -> GOTO: Identifier5
Comma1: LPar -> GOTO: LPar6
Plus3: P2Expression$$ -> GOTO: P2Expression$$10
Plus3: P3Expression$$$ -> GOTO: P3Expression$$$5
Plus3: Identifier -> GOTO: Identifier5
Plus3: LPar -> GOTO: LPar6
Times4: P3Expression$$$ -> GOTO: P3Expression$$$13
Times4: Identifier -> GOTO: Identifier5
Times4: LPar -> GOTO: LPar6
LPar9: RPar -> REDUCE: List3ListOfExpression -> • [RPar]
LPar9: List3ListOfExpression -> GOTO: List3ListOfExpression3
LPar9: List2ListOfExpression$ -> GOTO: List2ListOfExpression$1
LPar9: Expression -> GOTO: Expression3
LPar9: P1Expression$ -> GOTO: P1Expression$3
LPar9: P2Expression$$ -> GOTO: P2Expression$$4
LPar9: P3Expression$$$ -> GOTO: P3Expression$$$5
LPar9: Identifier -> GOTO: Identifier5
LPar9: LPar -> GOTO: LPar6
Expression5: RPar -> GOTO: RPar4
P2Expression$$7: RPar -> REDUCE: P1Expression$ -> P1Expression$ Plus P2Expression$$ • [RPar, Plus]
P2Expression$$7: Plus -> REDUCE: P1Expression$ -> P1Expression$ Plus P2Expression$$ • [RPar, Plus]
P2Expression$$7: Times -> GOTO: Times2
P3Expression$$$9: RPar -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus]
P3Expression$$$9: Times -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus]
P3Expression$$$9: Plus -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus]
List3ListOfExpression2: RPar -> GOTO: RPar5
RPar3: RPar -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus]
RPar3: Times -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus]
RPar3: Plus -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus]
Expression7: RPar -> REDUCE: List2ListOfExpression$ -> List2ListOfExpression$ Comma Expression • [RPar, Comma]
Expression7: Comma -> REDUCE: List2ListOfExpression$ -> List2ListOfExpression$ Comma Expression • [RPar, Comma]
P2Expression$$10: RPar -> REDUCE: P1Expression$ -> P1Expression$ Plus P2Expression$$ • [RPar, Plus, Comma]
P2Expression$$10: Plus -> REDUCE: P1Expression$ -> P1Expression$ Plus P2Expression$$ • [RPar, Plus, Comma]
P2Expression$$10: Comma -> REDUCE: P1Expression$ -> P1Expression$ Plus P2Expression$$ • [RPar, Plus, Comma]
P2Expression$$10: Times -> GOTO: Times4
P3Expression$$$13: RPar -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus, Comma]
P3Expression$$$13: Times -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus, Comma]
P3Expression$$$13: Plus -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus, Comma]
P3Expression$$$13: Comma -> REDUCE: P2Expression$$ -> P2Expression$$ Times P3Expression$$$ • [RPar, Times, Plus, Comma]
List3ListOfExpression3: RPar -> GOTO: RPar6
RPar4: RPar -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus, Comma]
RPar4: Times -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus, Comma]
RPar4: Plus -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus, Comma]
RPar4: Comma -> REDUCE: P3Expression$$$ -> LPar Expression RPar • [RPar, Times, Plus, Comma]
RPar5: RPar -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus]
RPar5: Times -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus]
RPar5: Plus -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus]
RPar6: RPar -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus, Comma]
RPar6: Times -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus, Comma]
RPar6: Plus -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus, Comma]
RPar6: Comma -> REDUCE: P3Expression$$$ -> Identifier LPar List3ListOfExpression RPar • [RPar, Times, Plus, Comma]

*/

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<foundation.rpg.sample.language.ast.Program> {
    private final foundation.rpg.sample.language.ast.AstFactory factory;

    public State(foundation.rpg.sample.language.ast.AstFactory factory) {
        this.factory = factory;
    }

    public foundation.rpg.sample.language.ast.AstFactory getFactory() {
        return factory;
    }

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

    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitP1Expression$(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitP2Expression$$(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitP3Expression$$$(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList3ListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList2ListOfExpression$(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
