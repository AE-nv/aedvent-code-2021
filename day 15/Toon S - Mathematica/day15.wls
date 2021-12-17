lowestTotalRisk[risks_] := Module[{positions, edges, g, XMAX, YMAX},
   {XMAX, YMAX} = Dimensions[risks];
   positions = Flatten[Table[{i, j}, {i, XMAX}, {j, YMAX}], 1];
   risk[{x_, y_}] := risks[[x, y]];
   validPos[{x_, y_}] := 1 <= x <= XMAX && 1 <= y <= YMAX;
   adjacents[{x_, y_}] := Select[{{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}}, validPos];
   edgeWeight[HoldPattern[_ -> p_]] := risk[p];
   edges = Flatten[Function[p, p -> # & /@ adjacents[p]] /@ positions];
   g = Graph[edges, EdgeWeight -> edgeWeight /@ edges];
   Total[risk /@ Drop[FindShortestPath[g, {1, 1}, {XMAX, YMAX}], 1]]];

risks1 = ToExpression@*Characters /@ ReadList["example.txt", "String"];
join2[{x__}] := Join[x, 2]
risks2 = Join @@ join2 /@ Table[Mod[risks1 + i + j - 1, 9] + 1, {i, 0, 4}, {j, 0, 4}];
lowestTotalRisk /@ {risks1, risks2}