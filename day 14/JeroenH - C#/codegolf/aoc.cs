var j=ReadAllLines("i");
var r=j[0];
T t=j[2..].ToDictionary(l=>l[0..2],l=>(l[0]+l[6..],l[6..]+l[1]));
D a=Range(0,r.Length-1).Select(i=>r[i..(i+2)]).GroupBy(p=>p).ToImmutableDictionary(g=>g.Key,g=>(long)g.Count());
WriteLine((U(10),U(40)));
long U(int n){var C=Range(0,n).Aggregate(a,(d,_)=>F(d,t));
var S=C.Select(p=>(c:p.Key[0],o:C[p.Key])).GroupBy(x=>x.c,x=>x.o).ToDictionary(g=>g.Key,g=>g.Key==r[^1]?g.Sum()+1:g.Sum()).Values;
return S.Max()-S.Min();}
D F(D d,T t)=>(from i in d let p=t[i.Key]from k in new[]{p.Item1,p.Item2}group i.Value by k).ToImmutableDictionary(g=>g.Key,g=>g.Sum());