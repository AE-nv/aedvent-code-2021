f["x", n_][p : {x_, y_}] := If[x >= n, {2 n - x, y}, p]
f["y", n_][p : {x_, y_}] := If[y >= n, {x, 2 n - y}, p]
f[ax_, n_][l_] := DeleteDuplicates[f[ax, n] /@ l]
plot[r_] := ArrayPlot@Transpose@Array[{#1, #2} /. Thread[r -> Black] /. {_, _} -> White &, Max /@ Transpose[r]]
{Length[First[#]], plot[Last[#] + 1]} &@(ComposeList @@ (Flatten[#, 1] &
            /@ Transpose[Through@*(StringCases /@ {ax_ ~~ "=" ~~ n__ :> f[ax, ToExpression@n],
                                                   x__ ~~ "," ~~ y__ :> ToExpression@{x, y}}) /@ ReadList["input.txt", String]]))