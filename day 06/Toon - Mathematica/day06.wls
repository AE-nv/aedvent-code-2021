in = ToExpression /@ StringSplit[ReadString["input.txt"], ","];
birth = {0, n_} :> Sequence[{8, n}, {6, n}];
fishCycle = {x_, n_} :> {If[x <= 6, Mod[x - 1, 7], x - 1], n};
merge = {pre___, {x_, n1_}, middle___, {x_, n2_},  post___} :> {pre, {x, n1 + n2}, middle, post};
simulate[n_] := Last@Total@Nest[# /. {birth, fishCycle} /. merge &, Tally[in], n]
simulate[80]
simulate[256]