var input = File.ReadAllLines("input.txt");

var instructions = (
    from line in input
    let v = Instruction.Parse(line)
    where v.HasValue
    select v.Value).ToImmutableArray();

var part1 = (
    from instruction in instructions
    let c = instruction.prism
    where c.min >= new P(-50, -50, -50) && c.max <= new P(50, 50, 50)
    from p in c.Points()
    select (instruction.@on, p)).Aggregate(ImmutableHashSet<P>.Empty, (set, p) => p.on ? set.Add(p.p) : set.Remove(p.p)).Count();

var part2 = SolvePart2();

Console.WriteLine((part1, part2));

long SolvePart2()
{
    Dictionary<Prism, long> counts = new();
    foreach (var (turnon, n) in instructions)
    {
        var newprisms = (
            from item in counts
            let p = item.Key             let count = item.Value
            let intersection = p.Intersect(n)
            where intersection.HasValue
            select (intersection: intersection.Value, count)).ToLookup(x => x.intersection, x => -x.count).ToDictionary(x => x.Key, x => x.Sum());

        if (turnon)
            newprisms[n] = newprisms.GetValueOrDefault(n, 0) + 1;

        foreach (var (prism, count) in newprisms)
            counts[prism] = counts.GetValueOrDefault(prism, 0) + count;
    }

    return counts.Sum(a => a.Key.Volume * a.Value);
}

record struct Instruction(bool on, Prism prism)
{
    static Regex regex = new Regex(@"(?<switch>on|off) x=(?<x1>[-\d]+)..(?<x2>[-\d]+),y=(?<y1>[-\d]+)..(?<y2>[-\d]+),z=(?<z1>[-\d]+)..(?<z2>[-\d]+)", RegexOptions.Compiled);
    public static Instruction? Parse(string s)
    {
        var m = regex.Match(s);
        if (m.Success)
        {
            var p1 = new P(m.GetInt32("x1"), m.GetInt32("y1"), m.GetInt32("z1"));
            var p2 = new P(m.GetInt32("x2"), m.GetInt32("y2"), m.GetInt32("z2"));
            var c = new Instruction(m.Groups["switch"].Value == "on", new Prism(p1 < p2 ? p1 : p2, p1 < p2 ? p2 : p1));
            return c;
        }
        else
        {
            return null;
        }
    }
}

record struct Prism(P min, P max)
{
    public long Volume => (max.x - min.x + 1L) * (max.y - min.y + 1L) * (max.z - min.z + 1L);
    public Prism? Intersect(Prism other)
    {
        var i1 = new P(Max(min.x, other.min.x), Max(min.y, other.min.y), Max(min.z, other.min.z));
        var i2 = new P(Min(max.x, other.max.x), Min(max.y, other.max.y), Min(max.z, other.max.z));
        if (i1 <= i2)
            return new Prism(i1, i2);
        else
            return null;
    }

    public IEnumerable<P> Points()
    {
        var p1 = this.min;
        var p2 = this.max;
        return
            from x in Range(p1.x, p2.x - p1.x + 1) from y in Range(p1.y, p2.y - p1.y + 1) from z in Range(p1.z, p2.z - p1.z + 1) select new P(x, y, z);
    }
}

record struct P(int x, int y, int z)
{
    public static bool operator <(P left, P right) => left.x < right.x && left.y < right.y && left.z < right.z;
    public static bool operator >(P left, P right) => left.x > right.x && left.y > right.y && left.z > right.z;
    public static bool operator <=(P left, P right) => left.x <= right.x && left.y <= right.y && left.z <= right.z;
    public static bool operator >=(P left, P right) => left.x >= right.x && left.y >= right.y && left.z >= right.z;
}

static class Ext
{
    public static int GetInt32(this Match m, string name) => int.Parse(m.Groups[name].Value);
}