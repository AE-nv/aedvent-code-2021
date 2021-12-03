in = ToExpression@*Characters /@ ReadList["input.txt", String];

(* PART 1*)
gammaRateDigits = Flatten[Commonest /@ Transpose[in]];
epsilonRateDigits = gammaRateDigits /. {0 -> 1, 1 -> 0};
gammaRate = FromDigits[gammaRateDigits, 2];
epsilonRate = FromDigits[epsilonRateDigits, 2];
gammaRate*epsilonRate

(* PART 2 *)
zeroOnePositions = {#[0], #[1]} & /@ PositionIndex /@ Transpose[in];
sampleIndices = Range[Length[in]];

takeRemainingIndices[sampleIndices_, zeroOnes_] := Intersection[sampleIndices, #] & /@ zeroOnes

filterCriterium[_, {index_}, _] := {index}
filterCriterium[criterium_][sampleIndices_, zeroOnes_] := filterCriterium[criterium, sampleIndices,
                                                                          takeRemainingIndices[sampleIndices, zeroOnes]]
filterCriterium["o2",  sampleIndices_, {zeros_, ones_}] /; Length[ones] >= Length[zeros] := Intersection[sampleIndices, ones]
filterCriterium["co2", sampleIndices_, {zeros_, ones_}] /; Length[ones] <  Length[zeros] := Intersection[sampleIndices, ones]
filterCriterium[_, sampleIndices_, {zeros_, _}] := Intersection[sampleIndices, zeros]

oxygenCO2Indices = First@Fold[filterCriterium[#], sampleIndices, zeroOnePositions] & /@ {"o2", "co2"};
Times @@ (FromDigits[#, 2] & /@ in[[oxygenCO2Indices]])