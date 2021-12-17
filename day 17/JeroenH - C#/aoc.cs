var target = new Area(new P(185, -74), new P(221, -122));

var part1 = GetHits(target).MaxBy(x => x.max).max;
var part2 = GetHits(target).Count();

Console.WriteLine((part1, part2));

IEnumerable<Probe> GetHits(Area target) =>
    from v in CandidateVelocities(target.bottomright)
    let p = DoProbe(target, v)
    where p.Hit
    select p;

Probe DoProbe(Area target, V v0)
{
    var p = new Probe(target, new P(0, 0), v0, int.MinValue);
    while (!p.Hit && !p.Missed)
    {
        p = p.Step();
    }
    return p;
}

IEnumerable<V> CandidateVelocities(P bottomright)
{
    var max = (x: Math.Abs(bottomright.x), y: Math.Abs(bottomright.y));
    for (var dx = -Math.Abs(max.x); dx <= Math.Abs(max.x); dx++)
        for (var dy = -Math.Abs(max.y); dy <= Math.Abs(max.y); dy++)
            yield return new V(dx, dy);
}

readonly record struct Probe(Area target, P p, V v, int max)
{
    public bool Hit => p.IsIn(target);
    public bool Missed => p.y < target.bottomright.y || v.dx == 0 && (p.x < target.topleft.x || p.x > target.bottomright.x);
    public Probe Step() => this with { p = p.Next(v), v = v.Next(), max = p.y > max ? p.y : max };
}

readonly record struct Area(P topleft, P bottomright);
readonly record struct P(int x, int y)
{
    public P Next(V v) => this with { x = x + v.dx, y = y + v.dy };
    public bool IsIn(Area a) => x >= a.topleft.x && x <= a.bottomright.x && y <= a.topleft.y && y >= a.bottomright.y;
}

readonly record struct V(int dx, int dy)
{
    public V Next() => this with
    {
        dx = dx switch
        {
            > 0 => dx - 1,
            0 => 0,
            < 0 => dx + 1
        },
        dy = dy - 1
    };
}