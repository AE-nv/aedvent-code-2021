int:byteSize;
int:bytes;
array[1..bytes*byteSize] of 0..1:rawBits;
array[1..bytes, 1..byteSize] of 0..1:bits=array2d(1..bytes, 1..byteSize, rawBits);


/*Variables*/
array[1..byteSize] of var 0..bytes: oneCounts;
array[1..byteSize] of var 0..1: gamArr;
array[1..byteSize] of var 0..1: epsArr;
var int: gammaRate=sum([pow(2,byteSize-i)*gamArr[i] | i in 1..byteSize]);
var int: epsRate=sum([pow(2,byteSize-i)*epsArr[i] | i in 1..byteSize]);

/*constraints*/
constraint forall(i in 1..byteSize)(oneCounts[i]=count(bits[..,i],1))
            /\ forall(i in 1..byteSize)(gamArr[i]=bool2int(oneCounts[i]>500))
            /\ forall(i in 1..byteSize)(gamArr[i]=1 <-> epsArr[i]=0);

/*functions*/
function int: byteToInt(array[1..byteSize] of 0..1:X)=sum([pow(2,i)*X[i] | i in 1..byteSize]);

/*solve command*/
solve satisfy;

/*output*/
output["product: \(gammaRate*epsRate)"]
