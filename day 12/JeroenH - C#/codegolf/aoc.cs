var g=(from l in ReadLines("i") let s=l.Split('-')from e in new[]{(s:s[0],t:s[1]),(s:s[1],t:s[0])} select e).ToLookup(x=>x.s,x=>x.t);
var S="start";
WriteLine((C(L.Empty.Add(S),1),C(L.Empty.Add(S),2)));
int C(L p,int m)=>p[^1]=="end"?1:g[p[^1]].Aggregate(0,(t,n)=>t+m switch
{_ when n.All(IsUpper)||!p.Contains(n)=>C(p.Add(n),m),2 when n!=S&&n.All(IsLower)&&p.Contains(n)=>C(p.Add(n),1),_=>0});