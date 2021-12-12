in = TwoWayRule @@ StringSplit[#, "-"] & /@ ReadList["input.txt", "String"]
g = Graph[in, VertexLabels -> Automatic]
containsTwoSmallCaves[path_] := MemberQ[path, n_ /; LowerCaseQ[n] && Count[path, n] == 2]
children[1][path : {prev___, node_}] := Select[AdjacencyList[g, node], UpperCaseQ[#] || !MemberQ[path, #] &]
children[2][path : {prev___, node_}] := Select[AdjacencyList[g, node], # != "start"
                                                                       && (UpperCaseQ[#]
                                                                           || !MemberQ[path, #]
                                                                           || !containsTwoSmallCaves[path]) &]
reached[{prev___, lastNode_}] := lastNode == "end"
expand[version_][path_] := Append[path, #] & /@ children[version][path]
expandPaths[version_][{paths_, numComplete_}] := Module[{expandedPaths = Flatten[expand[version] /@ paths, 1]},
 {Select[expandedPaths, Not@*reached], numComplete + Length@Select[expandedPaths, reached]}]

part1 = Last@FixedPoint[expandPaths[1], {{{"start"}}, 0}]
part2 = Last@FixedPoint[expandPaths[2], {{{"start"}}, 0}]