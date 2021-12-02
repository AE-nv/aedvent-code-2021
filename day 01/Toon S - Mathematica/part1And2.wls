numIncreasingDifferences := Length@*Select[# > 0 &]@*Differences
numIncreasingDifferences@ReadList["input.txt"]
numIncreasingDifferences[Total /@ Partition[ReadList["input.txt"], 3, 1]]