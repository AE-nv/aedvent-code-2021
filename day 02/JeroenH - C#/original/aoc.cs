var ins=from s in ReadAllLines("input.txt") let m=new Regex(@"(\w+) (\d+)").Match(s) select(d: m.Groups[1].Value,v:Parse(m.Groups[2].Value));
var p=ins.Aggregate(new P(0,0),(p,i)=>i.d[0]switch{'f'=>new(p.x+i.v,p.y),'u'=>new(p.x,p.y-i.v),'d'=>new(p.x,p.y+i.v)}).V;
var q=ins.Aggregate((p:new P(0,0),a:0),(t,i)=>i.d[0]switch{'f'=>(new(t.p.x+i.v,t.p.y+t.a*i.v),t.a),'u'=>(t.p,t.a-i.v),'d'=>(t.p,t.a+i.v)}).p.V;
WriteLine((p,q));
record P(int x,int y){public int V=>x*y;}