{{s}, rules} = TakeDrop[ReadList["input.txt", String], 1];
counts = Counts@Partition[Characters@s, 2, 1];
add[p_, c_] /; MemberQ[Keys[counts], p] := counts[p] += c
add[p_, c_] := AssociateTo[counts, p ->  c]
rs = HoldPattern[p : {#1, #2} -> c_] :> (counts[p] -= c; add[{#1, #3}, c]; add[{#3, #2}, c];) & @@@ StringPart[rules, {1, 2, -1}];
Table[Normal[counts] /. rs, 40];
#2 - #1 & @@ MinMax@Ceiling[Merge[Flatten[Thread /@ Normal[counts]], Total]/2]