in = Characters /@ ReadList["example.txt", "String"];
pairs = <|"<" -> ">", "(" -> ")", "{" -> "}", "[" -> "]"|>;
points1 = <|")" -> 3, "]" -> 57, "}" -> 1197, ">" -> 25137|>;
points2 = {"(" -> 1, "[" -> 2, "{" -> 3, "<" -> 4};

foldf[stack_, char_] /; MemberQ[Keys[pairs], char] := Append[stack, char]
foldf[{pre___, opening_}, closing_] /; pairs[opening] == closing := {pre}
foldf[_, closing_] := Throw[points1[closing]]

syntaxChecks = Catch[Fold[foldf, {}, #]] & /@ in
part1 = Total@Cases[syntaxChecks, _Integer]
part2 = Median@Cases[syntaxChecks, l : {__String} :> Fold[5*#1 + #2 &, Reverse[l] /. points2]] (*FoldRight zou fijn zijn*)