energies = ToExpression@*Characters /@ ReadList["input.txt", "String"];
{YMAX, XMAX} = Dimensions[energies];
positions = Flatten[Table[{i, j}, {i, XMAX}, {j, YMAX}], 1];
validPos[{x_, y_}] := 1 <= x <= XMAX && 1 <= y <= YMAX
adjacents[pos_] := Select[pos + # & /@ Drop[Tuples[{0, 1, -1}, 2], 1], validPos]

flashSources[energies_] := Cases[positions, {x_, y_} /; energies[[x, y]] > 9  ]
flashTargets[sources_, prevSources_] := Flatten[adjacents /@ Complement[sources, prevSources], 1]
flash[{energies_, prevSources_}] := Module[{sources = flashSources[energies]}, {MapAt[# + 1 &, energies,
                                            flashTargets[sources, prevSources]], sources}]
step[{energies_, numFlashes_}] := Module[{newEnergies, sources},
                                         {newEnergies, sources} = FixedPoint[flash, {energies + 1, {}}];
                                         {MapAt[0 &, newEnergies, sources], numFlashes + Length[sources]}]
step2[{in : {es_, numFlashes_}, count_}] := Module[{newEs, newNumFlashes},
    {newEs, newNumFlashes} = step[in];
    If[newNumFlashes - numFlashes == Length[positions],
        Throw[count + 1],
        {{newEs, newNumFlashes}, count + 1}]]
part1 = Last@Nest[step, {energies, 0}, 100]
part2 = Catch[FixedPoint[step2, {{energies, 0}, 0}]]