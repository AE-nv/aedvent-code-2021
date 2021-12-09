in = ToExpression@*Characters /@ ReadList["input.txt", "String"];

{YMAX, XMAX} = Dimensions[in]
positions = Flatten[Table[{i, j}, {i, XMAX}, {j, YMAX}], 1]

value[{x_, y_}] := in[[y, x]]
validPos[{x_, y_}] := 1 <= x <= XMAX && 1 <= y <= YMAX
adjacents[{x_, y_}] := Select[{{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}}, validPos]
basinAdjacents[pos_] := Select[adjacents[pos], value[#] != 9 &]
riskLevel[pos_] := value[pos] + 1
isLowPoint[pos_] := And @@ (value[pos] < value[#] & /@ adjacents[pos]);
basin[lowPoint_] := FixedPoint[Union[# /. p : {_Integer, _Integer} :>  Sequence @@ Append[basinAdjacents[p], p]] &, {lowPoint}];

lowPoints = Select[positions, isLowPoint];
part1 = Total[riskLevel /@ lowPoints]
part2 = Times @@ TakeLargest[Length@*basin /@ lowPoints, 3]