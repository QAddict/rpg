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

package foundation.rpg.lexer.regular;

/*

N = [Start, Pattern, ListOfChain, Chain, ListOfNode, Node, ListOfItem, Item]
T = [End, Pipe, LPar, RPar, Times, Plus, Character, Bs, Dot, LBr, RBr, Tilda, Minus]
S = Start
R = {
	Start -> [Pattern, End]
	Pattern -> []
	Pattern -> [ListOfChain]
	ListOfChain -> [Chain]
	ListOfChain -> [ListOfChain, Pipe, Chain]
	Chain -> [ListOfNode]
	ListOfNode -> []
	ListOfNode -> [ListOfNode, Node]
	Node -> [LPar, Pattern, RPar]
	Node -> [Node, Times]
	Node -> [Node, Plus]
	Node -> [Character]
	Node -> [Bs, Character]
	Node -> [Bs, Bs]
	Node -> [Bs, Dot]
	Node -> [Dot]
	Node -> [LBr, ListOfItem, RBr]
	ListOfItem -> []
	ListOfItem -> [ListOfItem, Item]
	Node -> [LBr, Tilda, ListOfItem, RBr]
	Item -> [Character]
	Item -> [Bs]
	Item -> [Dot]
	Item -> [Character, Minus, Character]
}

1: {
	Start -> • Pattern End []
	Pattern -> • [End]
	Pattern -> • ListOfChain [End]
	ListOfChain -> • Chain [End, Pipe]
	ListOfChain -> • ListOfChain Pipe Chain [End, Pipe]
	Chain -> • ListOfNode [End, Pipe]
	ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
	ListOfNode -> • ListOfNode Node [End, LPar, Character, Bs, Dot, LBr, Pipe]
}
Pattern1: {
	Start -> Pattern • End []
}
ListOfChain1: {
	Pattern -> ListOfChain • [End]
	ListOfChain -> ListOfChain • Pipe Chain [End, Pipe]
}
Chain1: {
	ListOfChain -> Chain • [End, Pipe]
}
ListOfNode1: {
	Chain -> ListOfNode • [End, Pipe]
	ListOfNode -> ListOfNode • Node [End, LPar, Character, Bs, Dot, LBr, Pipe]
	Node -> • LPar Pattern RPar [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Node Times [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Node Plus [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Character [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Character [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Bs [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Dot [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Dot [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • LBr ListOfItem RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • LBr Tilda ListOfItem RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
End1: {
	Start -> Pattern End • []
}
Pipe1: {
	ListOfChain -> ListOfChain Pipe • Chain [End, Pipe]
	Chain -> • ListOfNode [End, Pipe]
	ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
	ListOfNode -> • ListOfNode Node [End, Pipe, LPar, Character, Bs, Dot, LBr]
}
Node1: {
	ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
	Node -> Node • Times [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> Node • Plus [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
LPar1: {
	Node -> LPar • Pattern RPar [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Pattern -> • [RPar]
	Pattern -> • ListOfChain [RPar]
	ListOfChain -> • Chain [RPar, Pipe]
	ListOfChain -> • ListOfChain Pipe Chain [RPar, Pipe]
	Chain -> • ListOfNode [RPar, Pipe]
	ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
	ListOfNode -> • ListOfNode Node [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
}
Character1: {
	Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Bs1: {
	Node -> Bs • Character [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> Bs • Bs [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> Bs • Dot [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Dot1: {
	Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
LBr1: {
	Node -> LBr • ListOfItem RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> LBr • Tilda ListOfItem RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> • [RBr, Character, Bs, Dot]
	ListOfItem -> • ListOfItem Item [RBr, Character, Bs, Dot]
}
Chain2: {
	ListOfChain -> ListOfChain Pipe Chain • [End, Pipe]
}
Times1: {
	Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Plus1: {
	Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Pattern2: {
	Node -> LPar Pattern • RPar [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
ListOfChain2: {
	Pattern -> ListOfChain • [RPar]
	ListOfChain -> ListOfChain • Pipe Chain [RPar, Pipe]
}
Chain3: {
	ListOfChain -> Chain • [RPar, Pipe]
}
ListOfNode3: {
	Chain -> ListOfNode • [RPar, Pipe]
	ListOfNode -> ListOfNode • Node [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
	Node -> • LPar Pattern RPar [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Node Times [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Node Plus [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Character [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Character [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Bs [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Dot [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Dot [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • LBr ListOfItem RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • LBr Tilda ListOfItem RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Character2: {
	Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Bs2: {
	Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Dot2: {
	Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
ListOfItem1: {
	Node -> LBr ListOfItem • RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> ListOfItem • Item [RBr, Character, Bs, Dot]
	Item -> • Character [RBr, Character, Bs, Dot]
	Item -> • Bs [RBr, Character, Bs, Dot]
	Item -> • Dot [RBr, Character, Bs, Dot]
	Item -> • Character Minus Character [RBr, Character, Bs, Dot]
}
Tilda1: {
	Node -> LBr Tilda • ListOfItem RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> • [RBr, Character, Bs, Dot]
	ListOfItem -> • ListOfItem Item [RBr, Character, Bs, Dot]
}
RPar1: {
	Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Pipe2: {
	ListOfChain -> ListOfChain Pipe • Chain [RPar, Pipe]
	Chain -> • ListOfNode [RPar, Pipe]
	ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
	ListOfNode -> • ListOfNode Node [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
}
Node2: {
	ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
	Node -> Node • Times [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> Node • Plus [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
LPar2: {
	Node -> LPar • Pattern RPar [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Pattern -> • [RPar]
	Pattern -> • ListOfChain [RPar]
	ListOfChain -> • Chain [RPar, Pipe]
	ListOfChain -> • ListOfChain Pipe Chain [RPar, Pipe]
	Chain -> • ListOfNode [RPar, Pipe]
	ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
	ListOfNode -> • ListOfNode Node [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
}
Character3: {
	Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Bs3: {
	Node -> Bs • Character [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> Bs • Bs [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> Bs • Dot [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Dot3: {
	Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
LBr2: {
	Node -> LBr • ListOfItem RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> LBr • Tilda ListOfItem RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> • [RBr, Character, Bs, Dot]
	ListOfItem -> • ListOfItem Item [RBr, Character, Bs, Dot]
}
RBr1: {
	Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Item1: {
	ListOfItem -> ListOfItem Item • [RBr, Character, Bs, Dot]
}
Character4: {
	Item -> Character • [RBr, Character, Bs, Dot]
	Item -> Character • Minus Character [RBr, Character, Bs, Dot]
}
Bs4: {
	Item -> Bs • [RBr, Character, Bs, Dot]
}
Dot4: {
	Item -> Dot • [RBr, Character, Bs, Dot]
}
ListOfItem2: {
	Node -> LBr Tilda ListOfItem • RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> ListOfItem • Item [RBr, Character, Bs, Dot]
	Item -> • Character [RBr, Character, Bs, Dot]
	Item -> • Bs [RBr, Character, Bs, Dot]
	Item -> • Dot [RBr, Character, Bs, Dot]
	Item -> • Character Minus Character [RBr, Character, Bs, Dot]
}
Chain4: {
	ListOfChain -> ListOfChain Pipe Chain • [RPar, Pipe]
}
Times2: {
	Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Plus2: {
	Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Pattern3: {
	Node -> LPar Pattern • RPar [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Character5: {
	Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Bs5: {
	Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
Dot5: {
	Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
ListOfItem3: {
	Node -> LBr ListOfItem • RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> ListOfItem • Item [RBr, Character, Bs, Dot]
	Item -> • Character [RBr, Character, Bs, Dot]
	Item -> • Bs [RBr, Character, Bs, Dot]
	Item -> • Dot [RBr, Character, Bs, Dot]
	Item -> • Character Minus Character [RBr, Character, Bs, Dot]
}
Tilda2: {
	Node -> LBr Tilda • ListOfItem RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> • [RBr, Character, Bs, Dot]
	ListOfItem -> • ListOfItem Item [RBr, Character, Bs, Dot]
}
Minus1: {
	Item -> Character Minus • Character [RBr, Character, Bs, Dot]
}
RBr2: {
	Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
RPar2: {
	Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
RBr3: {
	Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}
ListOfItem4: {
	Node -> LBr Tilda ListOfItem • RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> ListOfItem • Item [RBr, Character, Bs, Dot]
	Item -> • Character [RBr, Character, Bs, Dot]
	Item -> • Bs [RBr, Character, Bs, Dot]
	Item -> • Dot [RBr, Character, Bs, Dot]
	Item -> • Character Minus Character [RBr, Character, Bs, Dot]
}
Character8: {
	Item -> Character Minus Character • [RBr, Character, Bs, Dot]
}
RBr4: {
	Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}

1: End -> REDUCE: ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
1: LPar -> REDUCE: ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
1: Character -> REDUCE: ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
1: Bs -> REDUCE: ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
1: Dot -> REDUCE: ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
1: LBr -> REDUCE: ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
1: Pipe -> REDUCE: ListOfNode -> • [End, LPar, Character, Bs, Dot, LBr, Pipe]
1: Pattern -> GOTO: Pattern1
1: ListOfChain -> GOTO: ListOfChain1
1: Chain -> GOTO: Chain1
1: ListOfNode -> GOTO: ListOfNode1
Pattern1: End -> GOTO: End1
ListOfChain1: End -> REDUCE: Pattern -> ListOfChain • [End]
ListOfChain1: Pipe -> GOTO: Pipe1
Chain1: End -> REDUCE: ListOfChain -> Chain • [End, Pipe]
Chain1: Pipe -> REDUCE: ListOfChain -> Chain • [End, Pipe]
ListOfNode1: End -> REDUCE: Chain -> ListOfNode • [End, Pipe]
ListOfNode1: Pipe -> REDUCE: Chain -> ListOfNode • [End, Pipe]
ListOfNode1: Node -> GOTO: Node1
ListOfNode1: LPar -> GOTO: LPar1
ListOfNode1: Character -> GOTO: Character1
ListOfNode1: Bs -> GOTO: Bs1
ListOfNode1: Dot -> GOTO: Dot1
ListOfNode1: LBr -> GOTO: LBr1
End1:  -> ACCEPT: Start -> Pattern End • []
Pipe1: End -> REDUCE: ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe1: Pipe -> REDUCE: ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe1: LPar -> REDUCE: ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe1: Character -> REDUCE: ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe1: Bs -> REDUCE: ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe1: Dot -> REDUCE: ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe1: LBr -> REDUCE: ListOfNode -> • [End, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe1: Chain -> GOTO: Chain2
Pipe1: ListOfNode -> GOTO: ListOfNode1
Node1: End -> REDUCE: ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
Node1: LPar -> REDUCE: ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
Node1: Character -> REDUCE: ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
Node1: Bs -> REDUCE: ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
Node1: Dot -> REDUCE: ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
Node1: LBr -> REDUCE: ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
Node1: Pipe -> REDUCE: ListOfNode -> ListOfNode Node • [End, LPar, Character, Bs, Dot, LBr, Pipe]
Node1: Times -> GOTO: Times1
Node1: Plus -> GOTO: Plus1
LPar1: RPar -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar1: LPar -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar1: Character -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar1: Bs -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar1: Dot -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar1: LBr -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar1: Pipe -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar1: Pattern -> GOTO: Pattern2
LPar1: ListOfChain -> GOTO: ListOfChain2
LPar1: Chain -> GOTO: Chain3
LPar1: ListOfNode -> GOTO: ListOfNode3
Character1: End -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: LPar -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: Character -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: Bs -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: Dot -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: LBr -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: Pipe -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: Times -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character1: Plus -> REDUCE: Node -> Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs1: Character -> GOTO: Character2
Bs1: Bs -> GOTO: Bs2
Bs1: Dot -> GOTO: Dot2
Dot1: End -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: LPar -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: Character -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: Bs -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: Dot -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: LBr -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: Pipe -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: Times -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot1: Plus -> REDUCE: Node -> Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
LBr1: RBr -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr1: Character -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr1: Bs -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr1: Dot -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr1: ListOfItem -> GOTO: ListOfItem1
LBr1: Tilda -> GOTO: Tilda1
Chain2: End -> REDUCE: ListOfChain -> ListOfChain Pipe Chain • [End, Pipe]
Chain2: Pipe -> REDUCE: ListOfChain -> ListOfChain Pipe Chain • [End, Pipe]
Times1: End -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: LPar -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: Character -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: Bs -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: Dot -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: LBr -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: Pipe -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: Times -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times1: Plus -> REDUCE: Node -> Node Times • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: End -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: LPar -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: Character -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: Bs -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: Dot -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: LBr -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: Pipe -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: Times -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus1: Plus -> REDUCE: Node -> Node Plus • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Pattern2: RPar -> GOTO: RPar1
ListOfChain2: RPar -> REDUCE: Pattern -> ListOfChain • [RPar]
ListOfChain2: Pipe -> GOTO: Pipe2
Chain3: RPar -> REDUCE: ListOfChain -> Chain • [RPar, Pipe]
Chain3: Pipe -> REDUCE: ListOfChain -> Chain • [RPar, Pipe]
ListOfNode3: RPar -> REDUCE: Chain -> ListOfNode • [RPar, Pipe]
ListOfNode3: Pipe -> REDUCE: Chain -> ListOfNode • [RPar, Pipe]
ListOfNode3: Node -> GOTO: Node2
ListOfNode3: LPar -> GOTO: LPar2
ListOfNode3: Character -> GOTO: Character3
ListOfNode3: Bs -> GOTO: Bs3
ListOfNode3: Dot -> GOTO: Dot3
ListOfNode3: LBr -> GOTO: LBr2
Character2: End -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: LPar -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: Character -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: Bs -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: Dot -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: LBr -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: Pipe -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: Times -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character2: Plus -> REDUCE: Node -> Bs Character • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: End -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: LPar -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: Character -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: Bs -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: Dot -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: LBr -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: Pipe -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: Times -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs2: Plus -> REDUCE: Node -> Bs Bs • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: End -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: LPar -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: Character -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: Bs -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: Dot -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: LBr -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: Pipe -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: Times -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot2: Plus -> REDUCE: Node -> Bs Dot • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
ListOfItem1: RBr -> GOTO: RBr1
ListOfItem1: Item -> GOTO: Item1
ListOfItem1: Character -> GOTO: Character4
ListOfItem1: Bs -> GOTO: Bs4
ListOfItem1: Dot -> GOTO: Dot4
Tilda1: RBr -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda1: Character -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda1: Bs -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda1: Dot -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda1: ListOfItem -> GOTO: ListOfItem2
RPar1: End -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: LPar -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: Character -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: Bs -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: Dot -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: LBr -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: Pipe -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: Times -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar1: Plus -> REDUCE: Node -> LPar Pattern RPar • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Pipe2: RPar -> REDUCE: ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe2: Pipe -> REDUCE: ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe2: LPar -> REDUCE: ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe2: Character -> REDUCE: ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe2: Bs -> REDUCE: ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe2: Dot -> REDUCE: ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe2: LBr -> REDUCE: ListOfNode -> • [RPar, Pipe, LPar, Character, Bs, Dot, LBr]
Pipe2: Chain -> GOTO: Chain4
Pipe2: ListOfNode -> GOTO: ListOfNode3
Node2: RPar -> REDUCE: ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
Node2: LPar -> REDUCE: ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
Node2: Character -> REDUCE: ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
Node2: Bs -> REDUCE: ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
Node2: Dot -> REDUCE: ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
Node2: LBr -> REDUCE: ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
Node2: Pipe -> REDUCE: ListOfNode -> ListOfNode Node • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
Node2: Times -> GOTO: Times2
Node2: Plus -> GOTO: Plus2
LPar2: RPar -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar2: LPar -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar2: Character -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar2: Bs -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar2: Dot -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar2: LBr -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar2: Pipe -> REDUCE: ListOfNode -> • [RPar, LPar, Character, Bs, Dot, LBr, Pipe]
LPar2: Pattern -> GOTO: Pattern3
LPar2: ListOfChain -> GOTO: ListOfChain2
LPar2: Chain -> GOTO: Chain3
LPar2: ListOfNode -> GOTO: ListOfNode3
Character3: RPar -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: LPar -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: Character -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: Bs -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: Dot -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: LBr -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: Pipe -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: Times -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character3: Plus -> REDUCE: Node -> Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs3: Character -> GOTO: Character5
Bs3: Bs -> GOTO: Bs5
Bs3: Dot -> GOTO: Dot5
Dot3: RPar -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: LPar -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: Character -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: Bs -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: Dot -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: LBr -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: Pipe -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: Times -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot3: Plus -> REDUCE: Node -> Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
LBr2: RBr -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr2: Character -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr2: Bs -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr2: Dot -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
LBr2: ListOfItem -> GOTO: ListOfItem3
LBr2: Tilda -> GOTO: Tilda2
RBr1: End -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: LPar -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: Character -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: Bs -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: Dot -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: LBr -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: Pipe -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: Times -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr1: Plus -> REDUCE: Node -> LBr ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Item1: RBr -> REDUCE: ListOfItem -> ListOfItem Item • [RBr, Character, Bs, Dot]
Item1: Character -> REDUCE: ListOfItem -> ListOfItem Item • [RBr, Character, Bs, Dot]
Item1: Bs -> REDUCE: ListOfItem -> ListOfItem Item • [RBr, Character, Bs, Dot]
Item1: Dot -> REDUCE: ListOfItem -> ListOfItem Item • [RBr, Character, Bs, Dot]
Character4: RBr -> REDUCE: Item -> Character • [RBr, Character, Bs, Dot]
Character4: Character -> REDUCE: Item -> Character • [RBr, Character, Bs, Dot]
Character4: Bs -> REDUCE: Item -> Character • [RBr, Character, Bs, Dot]
Character4: Dot -> REDUCE: Item -> Character • [RBr, Character, Bs, Dot]
Character4: Minus -> GOTO: Minus1
Bs4: RBr -> REDUCE: Item -> Bs • [RBr, Character, Bs, Dot]
Bs4: Character -> REDUCE: Item -> Bs • [RBr, Character, Bs, Dot]
Bs4: Bs -> REDUCE: Item -> Bs • [RBr, Character, Bs, Dot]
Bs4: Dot -> REDUCE: Item -> Bs • [RBr, Character, Bs, Dot]
Dot4: RBr -> REDUCE: Item -> Dot • [RBr, Character, Bs, Dot]
Dot4: Character -> REDUCE: Item -> Dot • [RBr, Character, Bs, Dot]
Dot4: Bs -> REDUCE: Item -> Dot • [RBr, Character, Bs, Dot]
Dot4: Dot -> REDUCE: Item -> Dot • [RBr, Character, Bs, Dot]
ListOfItem2: RBr -> GOTO: RBr2
ListOfItem2: Item -> GOTO: Item1
ListOfItem2: Character -> GOTO: Character4
ListOfItem2: Bs -> GOTO: Bs4
ListOfItem2: Dot -> GOTO: Dot4
Chain4: RPar -> REDUCE: ListOfChain -> ListOfChain Pipe Chain • [RPar, Pipe]
Chain4: Pipe -> REDUCE: ListOfChain -> ListOfChain Pipe Chain • [RPar, Pipe]
Times2: RPar -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: LPar -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: Character -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: Bs -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: Dot -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: LBr -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: Pipe -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: Times -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Times2: Plus -> REDUCE: Node -> Node Times • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: RPar -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: LPar -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: Character -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: Bs -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: Dot -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: LBr -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: Pipe -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: Times -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Plus2: Plus -> REDUCE: Node -> Node Plus • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Pattern3: RPar -> GOTO: RPar2
Character5: RPar -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: LPar -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: Character -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: Bs -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: Dot -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: LBr -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: Pipe -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: Times -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Character5: Plus -> REDUCE: Node -> Bs Character • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: RPar -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: LPar -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: Character -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: Bs -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: Dot -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: LBr -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: Pipe -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: Times -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Bs5: Plus -> REDUCE: Node -> Bs Bs • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: RPar -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: LPar -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: Character -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: Bs -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: Dot -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: LBr -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: Pipe -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: Times -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
Dot5: Plus -> REDUCE: Node -> Bs Dot • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
ListOfItem3: RBr -> GOTO: RBr3
ListOfItem3: Item -> GOTO: Item1
ListOfItem3: Character -> GOTO: Character4
ListOfItem3: Bs -> GOTO: Bs4
ListOfItem3: Dot -> GOTO: Dot4
Tilda2: RBr -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda2: Character -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda2: Bs -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda2: Dot -> REDUCE: ListOfItem -> • [RBr, Character, Bs, Dot]
Tilda2: ListOfItem -> GOTO: ListOfItem4
Minus1: Character -> GOTO: Character8
RBr2: End -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: LPar -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: Character -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: Bs -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: Dot -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: LBr -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: Pipe -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: Times -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr2: Plus -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: RPar -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: LPar -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: Character -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: Bs -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: Dot -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: LBr -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: Pipe -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: Times -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RPar2: Plus -> REDUCE: Node -> LPar Pattern RPar • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: RPar -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: LPar -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: Character -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: Bs -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: Dot -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: LBr -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: Pipe -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: Times -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr3: Plus -> REDUCE: Node -> LBr ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
ListOfItem4: RBr -> GOTO: RBr4
ListOfItem4: Item -> GOTO: Item1
ListOfItem4: Character -> GOTO: Character4
ListOfItem4: Bs -> GOTO: Bs4
ListOfItem4: Dot -> GOTO: Dot4
Character8: RBr -> REDUCE: Item -> Character Minus Character • [RBr, Character, Bs, Dot]
Character8: Character -> REDUCE: Item -> Character Minus Character • [RBr, Character, Bs, Dot]
Character8: Bs -> REDUCE: Item -> Character Minus Character • [RBr, Character, Bs, Dot]
Character8: Dot -> REDUCE: Item -> Character Minus Character • [RBr, Character, Bs, Dot]
RBr4: RPar -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: LPar -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: Character -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: Bs -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: Dot -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: LBr -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: Pipe -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: Times -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
RBr4: Plus -> REDUCE: Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]

*/

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class State extends StateBase<foundation.rpg.lexer.regular.ast.Pattern> {

// Ignored:
// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitPipe(foundation.rpg.common.Pipe symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLPar(foundation.rpg.common.LPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRPar(foundation.rpg.common.RPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitTimes(foundation.rpg.common.Times symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitPlus(foundation.rpg.common.Plus symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitCharacter(java.lang.Character symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitBs(foundation.rpg.common.Bs symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitDot(foundation.rpg.common.Dot symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLBr(foundation.rpg.common.LBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRBr(foundation.rpg.common.RBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitTilda(foundation.rpg.common.Tilda symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitMinus(foundation.rpg.common.Minus symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitPattern(foundation.rpg.lexer.regular.ast.Pattern symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfChain(java.util.List<foundation.rpg.lexer.regular.ast.Chain> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitChain(foundation.rpg.lexer.regular.ast.Chain symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfNode(java.util.List<foundation.rpg.lexer.regular.ast.Node> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitNode(foundation.rpg.lexer.regular.ast.Node symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfItem(java.util.List<foundation.rpg.lexer.regular.ast.Item> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitItem(foundation.rpg.lexer.regular.ast.Item symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
