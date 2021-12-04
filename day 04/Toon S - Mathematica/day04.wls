{{rawNumbers}, rawBoards} = TakeDrop[ReadList["input.txt", "String"], 1];
numbers = ToExpression /@ StringSplit[rawNumbers, ","];
boards = ToExpression@Partition[StringSplit[rawBoards], 5];

crossOff[{board_, 0}, num_] := {#, getScore[#, num]} &[board /. {num -> "X"}]
crossOff[boardScore_, _] := boardScore

hasBingo[board_] := Or @@ (MemberQ[# /. {"X" -> Nothing}, {}] & /@ {#, Transpose[#]} &@ board)

getScore[board_, num_] /; hasBingo[board] := num*Total@Flatten[board /. {"X" -> Nothing}]
getScore[board_, _] := 0
getScores[board_] := Last /@ FoldList[crossOff, {board, 0}, numbers]

firstBingoScorePos[board_] := FirstPosition[#, Max[#]] -> Max[#] &@getScores[board]
boardScorePositions = firstBingoScorePos /@ boards;
Values@Through[{MinimalBy, MaximalBy}[boardScorePositions, First]]