in = ToExpression /@ StringSplit[ReadString["input.txt"], ","];
dist[x1_, x2_] := (Abs[x2 - x1] + 1)*(Abs[x2 - x1])/2
totalCost[c_, d_] := Total[d[c, #] & /@ in]
totalCost[CentralFeature[in, DistanceFunction -> #], #] & /@ {ManhattanDistance, dist}