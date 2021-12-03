in = MapAt[ToExpression, 2]@*StringSplit /@ ReadList["input.txt", String];

step[{x_, y_}, {"forward", val_}] := {x + val, y}
step[{x_, y_}, {"down", val_}] := {x, y + val}
step[{x_, y_}, {"up", val_}] := {x, y - val}
Times @@ Fold[step, {0, 0}, in]

step2[{x_, y_, aim_}, {"forward", val_}] := {x + val, y + val*aim, aim}
step2[{x_, y_, aim_}, {"down", val_}] := {x, y, aim + val}
step2[{x_, y_, aim_}, {"up", val_}] := {x, y, aim - val}
#1*#2 & @@ Fold[step2, {0, 0, 0}, in]