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

/*

N = [Start, Object, ListOfObject, MapOfObject]
T = [End, String, Integer, Double, LBr, RBr, LCurl, RCurl, Comma, Colon]
S = Start
R = {
	Start -> Object,End
	Object -> String
	Object -> Integer
	Object -> Double
	Object -> LBr,ListOfObject,RBr
	Object -> LBr,RBr
	Object -> LCurl,MapOfObject,RCurl
	Object -> LCurl,RCurl
	ListOfObject -> Object
	ListOfObject -> ListOfObject,Comma,Object
	MapOfObject -> String,Colon,Object
	MapOfObject -> MapOfObject,Comma,String,Colon,Object
}

1: {
	Start -> • Object End []
	Object -> • String [End]
	Object -> • Integer [End]
	Object -> • Double [End]
	Object -> • LBr ListOfObject RBr [End]
	Object -> • LBr RBr [End]
	Object -> • LCurl MapOfObject RCurl [End]
	Object -> • LCurl RCurl [End]
}
Object1: {
	Start -> Object • End []
}
String1: {
	Object -> String • [End]
}
Integer1: {
	Object -> Integer • [End]
}
Double1: {
	Object -> Double • [End]
}
LBr1: {
	Object -> LBr • ListOfObject RBr [End]
	Object -> LBr • RBr [End]
	ListOfObject -> • Object [RBr, Comma]
	ListOfObject -> • ListOfObject Comma Object [RBr, Comma]
	Object -> • String [RBr, Comma]
	Object -> • Integer [RBr, Comma]
	Object -> • Double [RBr, Comma]
	Object -> • LBr ListOfObject RBr [RBr, Comma]
	Object -> • LBr RBr [RBr, Comma]
	Object -> • LCurl MapOfObject RCurl [RBr, Comma]
	Object -> • LCurl RCurl [RBr, Comma]
}
LCurl1: {
	Object -> LCurl • MapOfObject RCurl [End]
	Object -> LCurl • RCurl [End]
	MapOfObject -> • String Colon Object [RCurl, Comma]
	MapOfObject -> • MapOfObject Comma String Colon Object [RCurl, Comma]
}
End1: {
	Start -> Object End • []
}
ListOfObject1: {
	Object -> LBr ListOfObject • RBr [End]
	ListOfObject -> ListOfObject • Comma Object [RBr, Comma]
}
RBr1: {
	Object -> LBr RBr • [End]
}
Object2: {
	ListOfObject -> Object • [RBr, Comma]
}
String2: {
	Object -> String • [RBr, Comma]
}
Integer2: {
	Object -> Integer • [RBr, Comma]
}
Double2: {
	Object -> Double • [RBr, Comma]
}
LBr2: {
	Object -> LBr • ListOfObject RBr [RBr, Comma]
	Object -> LBr • RBr [RBr, Comma]
	ListOfObject -> • Object [RBr, Comma]
	ListOfObject -> • ListOfObject Comma Object [RBr, Comma]
	Object -> • String [RBr, Comma]
	Object -> • Integer [RBr, Comma]
	Object -> • Double [RBr, Comma]
	Object -> • LBr ListOfObject RBr [RBr, Comma]
	Object -> • LBr RBr [RBr, Comma]
	Object -> • LCurl MapOfObject RCurl [RBr, Comma]
	Object -> • LCurl RCurl [RBr, Comma]
}
LCurl2: {
	Object -> LCurl • MapOfObject RCurl [RBr, Comma]
	Object -> LCurl • RCurl [RBr, Comma]
	MapOfObject -> • String Colon Object [RCurl, Comma]
	MapOfObject -> • MapOfObject Comma String Colon Object [RCurl, Comma]
}
MapOfObject1: {
	Object -> LCurl MapOfObject • RCurl [End]
	MapOfObject -> MapOfObject • Comma String Colon Object [RCurl, Comma]
}
RCurl1: {
	Object -> LCurl RCurl • [End]
}
String3: {
	MapOfObject -> String • Colon Object [RCurl, Comma]
}
RBr2: {
	Object -> LBr ListOfObject RBr • [End]
}
Comma1: {
	ListOfObject -> ListOfObject Comma • Object [RBr, Comma]
	Object -> • String [RBr, Comma]
	Object -> • Integer [RBr, Comma]
	Object -> • Double [RBr, Comma]
	Object -> • LBr ListOfObject RBr [RBr, Comma]
	Object -> • LBr RBr [RBr, Comma]
	Object -> • LCurl MapOfObject RCurl [RBr, Comma]
	Object -> • LCurl RCurl [RBr, Comma]
}
ListOfObject2: {
	Object -> LBr ListOfObject • RBr [RBr, Comma]
	ListOfObject -> ListOfObject • Comma Object [RBr, Comma]
}
RBr3: {
	Object -> LBr RBr • [RBr, Comma]
}
MapOfObject2: {
	Object -> LCurl MapOfObject • RCurl [RBr, Comma]
	MapOfObject -> MapOfObject • Comma String Colon Object [RCurl, Comma]
}
RCurl2: {
	Object -> LCurl RCurl • [RBr, Comma]
}
RCurl3: {
	Object -> LCurl MapOfObject RCurl • [End]
}
Comma2: {
	MapOfObject -> MapOfObject Comma • String Colon Object [RCurl, Comma]
}
Colon1: {
	MapOfObject -> String Colon • Object [RCurl, Comma]
	Object -> • String [RCurl, Comma]
	Object -> • Integer [RCurl, Comma]
	Object -> • Double [RCurl, Comma]
	Object -> • LBr ListOfObject RBr [RCurl, Comma]
	Object -> • LBr RBr [RCurl, Comma]
	Object -> • LCurl MapOfObject RCurl [RCurl, Comma]
	Object -> • LCurl RCurl [RCurl, Comma]
}
Object4: {
	ListOfObject -> ListOfObject Comma Object • [RBr, Comma]
}
RBr4: {
	Object -> LBr ListOfObject RBr • [RBr, Comma]
}
RCurl4: {
	Object -> LCurl MapOfObject RCurl • [RBr, Comma]
}
String7: {
	MapOfObject -> MapOfObject Comma String • Colon Object [RCurl, Comma]
}
Object5: {
	MapOfObject -> String Colon Object • [RCurl, Comma]
}
String8: {
	Object -> String • [RCurl, Comma]
}
Integer5: {
	Object -> Integer • [RCurl, Comma]
}
Double5: {
	Object -> Double • [RCurl, Comma]
}
LBr5: {
	Object -> LBr • ListOfObject RBr [RCurl, Comma]
	Object -> LBr • RBr [RCurl, Comma]
	ListOfObject -> • Object [RBr, Comma]
	ListOfObject -> • ListOfObject Comma Object [RBr, Comma]
	Object -> • String [RBr, Comma]
	Object -> • Integer [RBr, Comma]
	Object -> • Double [RBr, Comma]
	Object -> • LBr ListOfObject RBr [RBr, Comma]
	Object -> • LBr RBr [RBr, Comma]
	Object -> • LCurl MapOfObject RCurl [RBr, Comma]
	Object -> • LCurl RCurl [RBr, Comma]
}
LCurl5: {
	Object -> LCurl • MapOfObject RCurl [RCurl, Comma]
	Object -> LCurl • RCurl [RCurl, Comma]
	MapOfObject -> • String Colon Object [RCurl, Comma]
	MapOfObject -> • MapOfObject Comma String Colon Object [RCurl, Comma]
}
Colon2: {
	MapOfObject -> MapOfObject Comma String Colon • Object [RCurl, Comma]
	Object -> • String [RCurl, Comma]
	Object -> • Integer [RCurl, Comma]
	Object -> • Double [RCurl, Comma]
	Object -> • LBr ListOfObject RBr [RCurl, Comma]
	Object -> • LBr RBr [RCurl, Comma]
	Object -> • LCurl MapOfObject RCurl [RCurl, Comma]
	Object -> • LCurl RCurl [RCurl, Comma]
}
ListOfObject3: {
	Object -> LBr ListOfObject • RBr [RCurl, Comma]
	ListOfObject -> ListOfObject • Comma Object [RBr, Comma]
}
RBr5: {
	Object -> LBr RBr • [RCurl, Comma]
}
MapOfObject3: {
	Object -> LCurl MapOfObject • RCurl [RCurl, Comma]
	MapOfObject -> MapOfObject • Comma String Colon Object [RCurl, Comma]
}
RCurl5: {
	Object -> LCurl RCurl • [RCurl, Comma]
}
Object7: {
	MapOfObject -> MapOfObject Comma String Colon Object • [RCurl, Comma]
}
RBr6: {
	Object -> LBr ListOfObject RBr • [RCurl, Comma]
}
RCurl6: {
	Object -> LCurl MapOfObject RCurl • [RCurl, Comma]
}

1: Object -> GOTO: Object1
1: String -> GOTO: String1
1: Integer -> GOTO: Integer1
1: Double -> GOTO: Double1
1: LBr -> GOTO: LBr1
1: LCurl -> GOTO: LCurl1
Object1: End -> GOTO: End1
String1: End -> REDUCE: Object -> String • [End]
Integer1: End -> REDUCE: Object -> Integer • [End]
Double1: End -> REDUCE: Object -> Double • [End]
LBr1: ListOfObject -> GOTO: ListOfObject1
LBr1: RBr -> GOTO: RBr1
LBr1: Object -> GOTO: Object2
LBr1: String -> GOTO: String2
LBr1: Integer -> GOTO: Integer2
LBr1: Double -> GOTO: Double2
LBr1: LBr -> GOTO: LBr2
LBr1: LCurl -> GOTO: LCurl2
LCurl1: MapOfObject -> GOTO: MapOfObject1
LCurl1: RCurl -> GOTO: RCurl1
LCurl1: String -> GOTO: String3
End1:  -> ACCEPT: Start -> Object End • []
ListOfObject1: RBr -> GOTO: RBr2
ListOfObject1: Comma -> GOTO: Comma1
RBr1: End -> REDUCE: Object -> LBr RBr • [End]
Object2: RBr -> REDUCE: ListOfObject -> Object • [RBr, Comma]
Object2: Comma -> REDUCE: ListOfObject -> Object • [RBr, Comma]
String2: RBr -> REDUCE: Object -> String • [RBr, Comma]
String2: Comma -> REDUCE: Object -> String • [RBr, Comma]
Integer2: RBr -> REDUCE: Object -> Integer • [RBr, Comma]
Integer2: Comma -> REDUCE: Object -> Integer • [RBr, Comma]
Double2: RBr -> REDUCE: Object -> Double • [RBr, Comma]
Double2: Comma -> REDUCE: Object -> Double • [RBr, Comma]
LBr2: ListOfObject -> GOTO: ListOfObject2
LBr2: RBr -> GOTO: RBr3
LBr2: Object -> GOTO: Object2
LBr2: String -> GOTO: String2
LBr2: Integer -> GOTO: Integer2
LBr2: Double -> GOTO: Double2
LBr2: LBr -> GOTO: LBr2
LBr2: LCurl -> GOTO: LCurl2
LCurl2: MapOfObject -> GOTO: MapOfObject2
LCurl2: RCurl -> GOTO: RCurl2
LCurl2: String -> GOTO: String3
MapOfObject1: RCurl -> GOTO: RCurl3
MapOfObject1: Comma -> GOTO: Comma2
RCurl1: End -> REDUCE: Object -> LCurl RCurl • [End]
String3: Colon -> GOTO: Colon1
RBr2: End -> REDUCE: Object -> LBr ListOfObject RBr • [End]
Comma1: Object -> GOTO: Object4
Comma1: String -> GOTO: String2
Comma1: Integer -> GOTO: Integer2
Comma1: Double -> GOTO: Double2
Comma1: LBr -> GOTO: LBr2
Comma1: LCurl -> GOTO: LCurl2
ListOfObject2: RBr -> GOTO: RBr4
ListOfObject2: Comma -> GOTO: Comma1
RBr3: RBr -> REDUCE: Object -> LBr RBr • [RBr, Comma]
RBr3: Comma -> REDUCE: Object -> LBr RBr • [RBr, Comma]
MapOfObject2: RCurl -> GOTO: RCurl4
MapOfObject2: Comma -> GOTO: Comma2
RCurl2: RBr -> REDUCE: Object -> LCurl RCurl • [RBr, Comma]
RCurl2: Comma -> REDUCE: Object -> LCurl RCurl • [RBr, Comma]
RCurl3: End -> REDUCE: Object -> LCurl MapOfObject RCurl • [End]
Comma2: String -> GOTO: String7
Colon1: Object -> GOTO: Object5
Colon1: String -> GOTO: String8
Colon1: Integer -> GOTO: Integer5
Colon1: Double -> GOTO: Double5
Colon1: LBr -> GOTO: LBr5
Colon1: LCurl -> GOTO: LCurl5
Object4: RBr -> REDUCE: ListOfObject -> ListOfObject Comma Object • [RBr, Comma]
Object4: Comma -> REDUCE: ListOfObject -> ListOfObject Comma Object • [RBr, Comma]
RBr4: RBr -> REDUCE: Object -> LBr ListOfObject RBr • [RBr, Comma]
RBr4: Comma -> REDUCE: Object -> LBr ListOfObject RBr • [RBr, Comma]
RCurl4: RBr -> REDUCE: Object -> LCurl MapOfObject RCurl • [RBr, Comma]
RCurl4: Comma -> REDUCE: Object -> LCurl MapOfObject RCurl • [RBr, Comma]
String7: Colon -> GOTO: Colon2
Object5: RCurl -> REDUCE: MapOfObject -> String Colon Object • [RCurl, Comma]
Object5: Comma -> REDUCE: MapOfObject -> String Colon Object • [RCurl, Comma]
String8: RCurl -> REDUCE: Object -> String • [RCurl, Comma]
String8: Comma -> REDUCE: Object -> String • [RCurl, Comma]
Integer5: RCurl -> REDUCE: Object -> Integer • [RCurl, Comma]
Integer5: Comma -> REDUCE: Object -> Integer • [RCurl, Comma]
Double5: RCurl -> REDUCE: Object -> Double • [RCurl, Comma]
Double5: Comma -> REDUCE: Object -> Double • [RCurl, Comma]
LBr5: ListOfObject -> GOTO: ListOfObject3
LBr5: RBr -> GOTO: RBr5
LBr5: Object -> GOTO: Object2
LBr5: String -> GOTO: String2
LBr5: Integer -> GOTO: Integer2
LBr5: Double -> GOTO: Double2
LBr5: LBr -> GOTO: LBr2
LBr5: LCurl -> GOTO: LCurl2
LCurl5: MapOfObject -> GOTO: MapOfObject3
LCurl5: RCurl -> GOTO: RCurl5
LCurl5: String -> GOTO: String3
Colon2: Object -> GOTO: Object7
Colon2: String -> GOTO: String8
Colon2: Integer -> GOTO: Integer5
Colon2: Double -> GOTO: Double5
Colon2: LBr -> GOTO: LBr5
Colon2: LCurl -> GOTO: LCurl5
ListOfObject3: RBr -> GOTO: RBr6
ListOfObject3: Comma -> GOTO: Comma1
RBr5: RCurl -> REDUCE: Object -> LBr RBr • [RCurl, Comma]
RBr5: Comma -> REDUCE: Object -> LBr RBr • [RCurl, Comma]
MapOfObject3: RCurl -> GOTO: RCurl6
MapOfObject3: Comma -> GOTO: Comma2
RCurl5: RCurl -> REDUCE: Object -> LCurl RCurl • [RCurl, Comma]
RCurl5: Comma -> REDUCE: Object -> LCurl RCurl • [RCurl, Comma]
Object7: RCurl -> REDUCE: MapOfObject -> MapOfObject Comma String Colon Object • [RCurl, Comma]
Object7: Comma -> REDUCE: MapOfObject -> MapOfObject Comma String Colon Object • [RCurl, Comma]
RBr6: RCurl -> REDUCE: Object -> LBr ListOfObject RBr • [RCurl, Comma]
RBr6: Comma -> REDUCE: Object -> LBr ListOfObject RBr • [RCurl, Comma]
RCurl6: RCurl -> REDUCE: Object -> LCurl MapOfObject RCurl • [RCurl, Comma]
RCurl6: Comma -> REDUCE: Object -> LCurl MapOfObject RCurl • [RCurl, Comma]

*/

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<java.lang.Object> {
    private final foundation.rpg.sample.json.JsonFactory factory;

    public State(foundation.rpg.sample.json.JsonFactory factory) {
        this.factory = factory;
    }

    public foundation.rpg.sample.json.JsonFactory getFactory() {
        return factory;
    }

    // Ignored:
    public State visitWhiteSpace(foundation.rpg.common.WhiteSpace symbol) {
        return this;
    }


// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitInteger(java.lang.Integer symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitDouble(java.lang.Double symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLBr(foundation.rpg.common.LBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRBr(foundation.rpg.common.RBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLCurl(foundation.rpg.common.LCurl symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRCurl(foundation.rpg.common.RCurl symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitComma(foundation.rpg.common.Comma symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitColon(foundation.rpg.common.Colon symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitObject(java.lang.Object symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfObject(java.util.List<java.lang.Object> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitMapOfObject(java.util.Map<java.lang.String,java.lang.Object> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
