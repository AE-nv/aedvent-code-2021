in = ReadList["input.txt", "String"];
pointPairs = ToExpression /@ StringSplit[#, ","] & /@ StringSplit[in, "->"];

range[s_, e_] /; e >= s := Range[s, e]
range[s_, e_] := Range[s, e, -1]

verticalRule = {{x_, y1_}, {x_, y2_}} :> ({x, #} & /@ range[y1, y2]);
horizontalRule = {{x1_, y_}, {x2_, y_}} :> ({#, y} & /@ range[x1, x2]);
diagonalRule1 = {{x_, y_}, {x1_, y1_}} /; x != x1 && y != y1 -> Nothing;
diagonalRule2 = {{x1_, y1_}, {x2_, y2_}} :> Transpose[{range[x1, x2], range[y1, y2]}]

overlappingPoints[coveredPoints_] := Length@Select[# > 1 &]@Values@Counts@Flatten[coveredPoints, 1]

overlappingPoints[pointPairs /. {horizontalRule, verticalRule, diagonalRule1}]
overlappingPoints[pointPairs /. {horizontalRule, verticalRule, diagonalRule2}]
