contains[s2_][s1_] := SubsetQ[s1, s2]
takeDrop[s_, f_] := {#, DeleteCases[s, #]} &@SelectFirst[s, f]
decode[{rIn_, rOut_}] := Module[{rowIn},
  rowIn = SortBy[rIn, Length];
  {one, seven, four, eight} = rowIn[[{1, 2, 3, -1}]];
  twoThreeFive = rowIn[[{4, 5, 6}]];
  zeroSixNine = rowIn[[{7, 8, 9}]];
  {three, twoFive} = takeDrop[twoThreeFive, contains[seven]];
  {five , {two}} = takeDrop[twoFive, contains[Complement[four, one]]];
  {nine , zeroSix} = takeDrop[zeroSixNine, contains[four]];
  {six, {zero}} = takeDrop[zeroSix, contains[five]];
  FromDigits[rOut /. {zero -> 0, one -> 1, two -> 2, three -> 3, four -> 4,
                        five -> 5, six -> 6, seven -> 7, eight -> 8, nine -> 9}]]

in = StringSplit /@ (StringSplit[#, "|"] & /@ ReadList["input.txt", String]);
outputLengths = StringLength /@ Flatten[in[[All, 2]]];
part1 = Count[outputLengths, length_ /; MemberQ[{2, 3, 4, 7}, length]]
part2 = Total[decode /@ (in /. s_String :> Sort[Characters[s]])]