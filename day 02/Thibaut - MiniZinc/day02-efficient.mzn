int:n;
array[1..n] of string: commands;
array[1..n] of int: numbers;

/*Variables*/
array[1..n] of var int:X;
array[1..n] of var int: Y;
var int: depth=sum(Y);
var int: H=sum(X);

/*constraints*/
constraint forall(i in 1..n)(commands[i]=="forward" <-> (X[i]==numbers[i] /\ Y[i]==0)
                              \/ commands[i]=="up" <-> (Y[i]==-numbers[i] /\ X[i]==0)
                              \/ commands[i]=="down" <-> (Y[i]==numbers[i] /\ X[i]==0));
                              
/*solve command*/
solve satisfy;

/*output*/
output ["X: \(H), Y: \(depth), X*Y: \(depth*H)"]