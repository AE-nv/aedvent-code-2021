parse[e_] := ToExpression@StringReplace[e, {"[" -> "{", "]" -> "}"}]

leftQ[{h1_}][{h2_, __}] := False
leftQ[{h1_, ___}][{h2_}] := h1 >= h2
leftQ[{h1_, t1__}][{h2_, t2__}] := h1 > h2 || h1 == h2 && leftQ[{t1}][{t2}]

explode[exp_, pairPos_] := Module[{l, r, intPositions = Position[exp, _Integer], leftPositions, expNew},
  {l, r} = Extract[exp, pairPos];
  leftPositions = TakeWhile[intPositions, leftQ[pairPos]];
  expNew = addLeft[exp, leftPositions, l];
  expNew = If[Length[leftPositions] == Length[intPositions] - 2,
        expNew,
        addRight[expNew, intPositions[[Length[leftPositions] + 3]], r]];
  MapAt[0 &, expNew, pairPos]
  ]
addLeft[exp_, {}, _] := exp
addLeft[exp_, {___, rightmostLeft_}, l_] := MapAt[# + l &, exp, rightmostLeft]
addRight[exp_, leftMostRight_, r_] := MapAt[# + r &, exp, leftMostRight]
split[exp_, numPos_] := MapAt[Through[{Floor, Ceiling}[Extract[exp, numPos]/2]] &, exp, numPos]

reduceStep[exp_] := Module[{explPos, splitPos, newExp = exp},
  explPos = SelectFirst[Position[exp, {_Integer, _Integer}], Length[#] >= 4 &];
  splitPos = Position[newExp, x_Integer /; x > 9];
  If[Not[MissingQ[explPos]],
    newExp = explode[exp, explPos],
    If[Length[splitPos] != 0,
        newExp = split[newExp, First@splitPos]]
  ];
  newExp
 ]

reduce[exp_] := FixedPoint[reduceStep, exp]
add[exp1_, exp2_] := reduce[{exp1, exp2}]
magnitude[exp_] := exp //. {x_Integer, y_Integer} :> 3 x + 2 y
in = parse /@ ReadList["input.txt", String];
magnitude@Fold[add, in]
Max[magnitude /@ (add @@@ Permutations[in, {2}])]
