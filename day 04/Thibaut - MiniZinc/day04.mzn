/*Parameters*/
int: nBoards;
int: boardSize;
array[1..nBoards*boardSize*boardSize] of int: rawBoards;
array[1..nBoards,1..boardSize,1..boardSize] of int: boards = array3d(1..nBoards,1..boardSize,1..boardSize,rawBoards);

int: nNumbers;
array[1..nNumbers] of int: numbers;

/*Variables*/
array[1..nBoards,1..nNumbers] of var bool: solved;

var 1..nBoards: winner;
var 1..nNumbers: winningNumber;
var 1..nBoards: loser;
var 1..nNumbers: losingNumber;

/*Predicates & Functions*/
function var bool: bingo(1..nBoards: bi, 1..nNumbers: ni) = 
  solved[bi,ni-1]
  \/
  exists(i in 1..boardSize)(array2set(boards[bi,..,i]) subset array2set(numbers[1..ni])
                            \/ array2set(boards[bi,i,..]) subset array2set(numbers[1..ni]))
  \/
  forall(i in 1..boardSize)(boards[bi,i,i] in array2set(numbers[1..ni]))
  \/
  forall(i in 1..boardSize)(boards[bi,boardSize+1-i,boardSize+1-i] in array2set(numbers[1..ni]));    

/*Constraints*/
constraint  forall(bi in 1..nBoards)(not solved[bi,1])
            /\ forall(bi in 1..nBoards, ni in 2..nNumbers)(solved[bi, ni]==bool2int(bingo(bi,ni)))
            /\ solved[winner, winningNumber] 
            /\ not solved[loser, losingNumber];
            
/*Solve command*/
solve minimize winningNumber; %maximize losingNumber for last board to be completed