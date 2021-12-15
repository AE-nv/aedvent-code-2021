int: size;
array[1..size, 1..size] of int:matrix;

array[1..size, 1..size] of var bool:road;

predicate neighbours(var int: k,var int: l,var int: x,var int: y)=
        (k==x   /\ l==y-1)
    \/  (k==x   /\ l==y+1)
    \/  (k==x-1 /\ l==y  )
    \/  (k==x+1 /\ l==y  );

predicate nbOfNeighbours(var int: x, var int: y, int: n)=
    n==sum(k,l in 1..size where neighbours(k,l,x,y))(bool2int(road[k,l]));

predicate toTheEnd(var int: x, var int: y)=
    (x==size /\ y==size)
    \/
    (
    road[x,y]
    /\
    exists(k,l in 1..size where neighbours(k,l,x,y))(toTheEnd(k,l))
    );
    

constraint road[1, 1]==1 /\ road[size, size]==1
           /\
           forall(i, j in 1..size where road[i,j])(nbOfNeighbours(i,j,2))
           /\
           toTheEnd(1,1);

var int: score=sum(i,j in 1..size where i+j>2)(bool2int(road[i,j])*matrix[i,j]);

solve minimize score;  

output ["score: \(score)"]