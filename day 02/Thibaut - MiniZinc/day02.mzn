int:n;
array[1..n] of string: commands;
array[1..n] of int: numbers;

/*Variables*/
array[1..n+1] of var 0..2000: X;
array[1..n+1] of var 0..1000000: Y;

/*constraints*/
constraint X[1]==0 /\ Y[1]==0;
constraint forall(i in 1..n)(commands[i]=="forward" <-> (X[i+1]==X[i]+numbers[i] /\ Y[i+1]==Y[i])
                              \/ commands[i]=="up" <-> (Y[i+1]==Y[i]-numbers[i] /\ X[i+1]==X[i])
                              \/ commands[i]=="down" <-> (Y[i+1]==Y[i]+numbers[i] /\ X[i+1]==X[i]));

/*solve command*/
solve satisfy;

/*output*/
output ["X: \(X[n+1]), Y: \(Y[n+1]), X*Y: \(X[n+1]*Y[n+1])"]
                             