int: nPositions;
int: range;
array[1..nPositions] of 0..range: positions;

/*helper*/
array[0..range] of int: consumption = array1d(0..range,[sum([j|j in 1..i])|i in 0..range]);

/*variable*/
var 0..range: target;
var int: consumedP1 = sum([abs(positions[i]-target) | i in 1..nPositions]);
var int: consumedP2 = sum([consumption[abs(positions[i]-target)]|i in 1..nPositions]);

/*Constraints*/

/*solver command*/
solve minimize consumedP2;

/*output command*/
output ["consumed fuel: \(consumedP2)"]