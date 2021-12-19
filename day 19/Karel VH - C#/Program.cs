var scanners = new List<Scanner>();
int index = 0;
foreach (string line in File.ReadLines("input.txt"))
{
    if (line.Contains("scanner"))
    {
        scanners.Add(new Scanner(index++));
    }
    else if (!string.IsNullOrWhiteSpace(line))
    {
        int[] coords = line.Split(",").Select(int.Parse).ToArray();
        scanners[^1].Beacons.Add(new Point(coords[0], coords[1], coords[2]));
    }
}

foreach (Scanner scanner in new List<Scanner>(scanners))
    scanners.AddRange(scanner.CreateOrientations());

var unmatched = scanners.GroupBy(x => x.Number).ToDictionary(g => g.Key, g => g.ToList());
var matched = new Dictionary<int, Scanner>() { { 0, unmatched[0].First() } };
unmatched.Remove(0);
var queue = new Queue<int>();
queue.Enqueue(0);
int maxDistance = 0;
while (unmatched.Any())
{
    int current = queue.Dequeue();
    var foundMatches = FindMatches(matched[current], unmatched.Values);
    foreach (var m in foundMatches)
    {
        matched[m.Number] = m;
        queue.Enqueue(m.Number);
        unmatched.Remove(m.Number);
        foreach (Scanner p in matched.Values)
        {
            if (m.Position.DistanceTo(p.Position) > maxDistance)
            {
                maxDistance = m.Position.DistanceTo(p.Position);
            }
        }
    }
}

Console.WriteLine(matched.Values.SelectMany(r => r.AbsoluteBeacons).Distinct().Count());
Console.WriteLine(maxDistance);


IEnumerable<Scanner> FindMatches(Scanner target, IEnumerable<IEnumerable<Scanner>> scanners)
{
    List<Scanner> result = new();
    foreach (IEnumerable<Scanner> s in scanners)
    {
        foreach (Scanner ss in s)
        {
            Scanner res = FindMatch(target, ss);
            if (res != null)
            {
                result.Add(res);
                break;
            }
        }
    }
    return result;
}

Scanner FindMatch(Scanner target, Scanner scanner)
{
    foreach (Point t in target.AbsoluteBeacons)
    {
        foreach (Point s in scanner.AbsoluteBeacons)
        {
            var moved = scanner with { Position = t.Subtract(s) };
            if (target.AbsoluteBeacons.Intersect(moved.AbsoluteBeacons).Count() >= 12)
                return moved;
        }
    }
    return null;
}


record Scanner
{
    public Point Position { get; set; } = new Point(0, 0, 0);
    public Scanner(int number, IEnumerable<Point> beacons) : this(number) { Beacons = beacons.ToList(); }
    public Scanner(int number) { Number = number; }
    public int Number { get; set; }
    public List<Point> Beacons { get; } = new();
    public IEnumerable<Point> AbsoluteBeacons => Beacons.Select(v => v.Add(Position));
    public IEnumerable<Scanner> CreateOrientations()
        => Beacons.SelectMany(b => b.EnumOrientations()
                  .Select((v, i) => (index: i, vector: v)))
                  .GroupBy(v => v.index, g => g.vector)
                  .Select(g => new Scanner(Number, g));

}
record Point(int X, int Y, int Z)
{
    public List<Point> EnumOrientations()
    {
        return new List<Point>()
        {
            // Used a permutation list generator because I did not understand the rotations
            new Point(X,Y,Z), new Point(X,Y,-Z), new Point(X,Z,Y), new Point(X,Z,-Y), new Point(X,-Y,Z),
            new Point(X,-Y,-Z), new Point(X,-Z,Y), new Point(X,-Z,-Y), new Point(Y,X,-Z), new Point(Y,X,Z),
            new Point(Y,Z,X), new Point(Y,Z,-X), new Point(Y,-X,-Z), new Point(Y,-X,Z), new Point(Y,-Z,X),
            new Point(Y,-Z,-X), new Point(Z,X,-Y), new Point(Z,X,Y), new Point(Z,Y,X), new Point(Z,Y,-X),
            new Point(Z,-X,-Y), new Point(Z,-X,Y), new Point(Z,-Y,-X), new Point(Z,-Y,X), new Point(-X,Y,Z),
            new Point(-X,Y,-Z), new Point(-X,Z,Y), new Point(-X,Z,-Y), new Point(-X,-Y,Z), new Point(-X,-Y,-Z),
            new Point(-X,-Z,Y), new Point(-X,-Z,-Y), new Point(-Y,X,Z), new Point(-Y,X,-Z), new Point(-Y,Z,X),
            new Point(-Y,Z,-X), new Point(-Y,-X,Z), new Point(-Y,-X,-Z), new Point(-Y,-Z,X), new Point(-Y,-Z,-X),
            new Point(-Z,X,-Y), new Point(-Z,X,Y), new Point(-Z,Y,X), new Point(-Z,Y,-X), new Point(-Z,-X,-Y),
            new Point(-Z,-X,Y), new Point(-Z,-Y,-X), new Point(-Z,-Y,X),
        };
    }
    internal Point Add(Point p) => new(X + p.X, Y + p.Y, Z + p.Z);
    internal Point Subtract(Point p) => new(X - p.X, Y - p.Y, Z - p.Z);
    internal int DistanceTo(Point p) => Math.Abs(X - p.X) + Math.Abs(Y - p.Y) + Math.Abs(Z - p.Z);
}

