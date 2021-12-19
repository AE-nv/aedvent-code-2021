var input = File.ReadAllLines("input.txt");

var scanners = Align(
    from s in CreateScanners(input)
    from t in s.Transformations() 
    select t);

var part1 = (
    from s in scanners
    from b in s.OffsetBeacons
    select b).Distinct().Count();

var part2 = (
    from s1 in scanners
    from s2 in scanners
    select s1.offset.Distance(s2.offset)).Max();

Console.WriteLine((part1, part2));

ImmutableList<Scanner> Align(IEnumerable<Scanner> scanners)
{
    var remaining = (
        from scanner in scanners.Skip(1) 
        group scanner by scanner.id
    ).ToImmutableDictionary(g => g.Key);

    var found = ImmutableDictionary<int, Scanner>.Empty.Add(0, scanners.First());

    var q = ImmutableQueue<int>.Empty.Enqueue(0);
    while (q.Any() && remaining.Any())
    {
        q = q.Dequeue(out int id);
        foreach (var scanner in found[id].FindScannersInRange(remaining.Values))
        {
            found = found.SetItem(scanner.id, scanner);
            q = q.Enqueue(scanner.id);
            remaining = remaining.Remove(scanner.id);
        }
    }

    return found.Values.ToImmutableList();
}

IEnumerable<Scanner> CreateScanners(IEnumerable<string> input)
{
    var enumerator = input.GetEnumerator();
    while (enumerator.MoveNext())
    {
        var id = int.Parse(enumerator.Current.Split(' ')[2]);
        yield return new Scanner(id, ReadPoints(enumerator).ToImmutableHashSet(), default);
    }
}

IEnumerable<P> ReadPoints(IEnumerator<string> enumerator)
{
    while (enumerator.MoveNext() && enumerator.Current != string.Empty)
    {
        var s = enumerator.Current.Split(',').Select(int.Parse).ToArray();
        yield return new P(s[0], s[1], s[2]);
    }
}

record struct P(int x, int y, int z)
{
    public static P operator -(P left, P right) => new(left.x - right.x, left.y - right.y, left.z - right.z);
    public static P operator +(P left, P right) => new(left.x + right.x, left.y + right.y, left.z + right.z);
    public int Distance(P other) => Abs(other.x - x) + Abs(other.y - y) + Abs(other.z - z);
}

;
record Scanner(int id, ImmutableHashSet<P> beacons, P offset = default)
{
    private ImmutableHashSet<P>? _offsets;
    public ImmutableHashSet<P> OffsetBeacons
    {
        get
        {
            if (_offsets == null)
            {
                _offsets = beacons.Select(v => v + offset).ToImmutableHashSet();
            }

            return _offsets;
        }
    }

    IEnumerable<Func<P, P>> TransformationFunctions()
    {
        var q =
            from i in new[] { 1, -1 } from j in new[] { 1, -1 } from k in new[] { 1, -1 } select (i, j, k);
        foreach (var t in q)
        {
            yield return (P p) => new P(t.i * p.x, t.j * p.y, t.k * p.z);
            yield return (P p) => new P(t.i * p.x, t.j * p.z, t.k * p.y);
            yield return (P p) => new P(t.i * p.y, t.j * p.x, t.k * p.z);
            yield return (P p) => new P(t.i * p.y, t.j * p.z, t.k * p.x);
            yield return (P p) => new P(t.i * p.z, t.j * p.y, t.k * p.x);
            yield return (P p) => new P(t.i * p.z, t.j * p.x, t.k * p.y);
        }
    }

    internal IEnumerable<Scanner> Transformations() =>
        from f in TransformationFunctions() select new Scanner(id, beacons.Select(f).ToImmutableHashSet(), default);

    internal IEnumerable<Scanner> FindScannersInRange(IEnumerable<IEnumerable<Scanner>> scanners) =>
        from s in scanners
        let t = (
            from scanner in s
            from a in scanner.InRange(this)
            select a).FirstOrDefault()
        where t is not null
        select t;

    private IEnumerable<Scanner> InRange(Scanner target) =>
        from a in target.OffsetBeacons
        from r in beacons
        let aligned = this with { offset = a - r }
        where target.OffsetBeacons.Intersect(aligned.OffsetBeacons).Count() >= 12
        select aligned;
}