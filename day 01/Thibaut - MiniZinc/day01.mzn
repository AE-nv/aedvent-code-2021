/*
Parameters
*/
int:n;
array[1..n] of int:input;
int: windowSize=1;

/*
Variables
*/
array[1..n] of var 0..1: comparisons;
var 0..n: count = sum(comparisons);

/*
Constraints
*/
constraint forall(i in 1..n-windowSize)(comparisons[i]==1 <-> input[i]<input[i+windowSize]);

/*
solving command
*/
solve satisfy;

/*
Output
*/
output ["increasing numbers: \(count)"];


