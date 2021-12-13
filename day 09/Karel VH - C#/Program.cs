var txt = File.ReadAllLines("input.txt");
List<List<(int X, int Y, int V, List<(int X, int Y)> N)>> input = txt
    .Select((r, i) => r.Select((c, j) => (i, j, int.Parse(c.ToString()), new List<(int X, int Y)> { (0, 1), (0, -1), (1, 0), (-1, 0) }
    .Select(x => (X: x.X + i, Y: x.Y + j))
    .Where(n => n.X > -1 && n.Y > -1 && n.X < txt.Count() && n.Y < txt.First().Count()).ToList())).ToList()).ToList();

List<HashSet<(int X, int Y)>> basins = new();

var p1 = input.Select(l => l.Where(c => !c.N.Any(n => input[n.X][n.Y].V <= c.V))).SelectMany(r => r).ToList();
p1.ForEach(c => basins.Add(addToListIfLower(c.X, c.Y, new HashSet<(int, int)> { (c.X, c.Y) })));


HashSet<(int, int)> addToListIfLower(int i, int j, HashSet<(int X, int Y)> res)
{
    input[i][j].N.Where(x => input[i][j].V < input[x.X][x.Y].V && input[x.X][x.Y].V != 9 && !res.Any(r => r.X == x.X && r.Y == x.Y))
        .ToList().ForEach(x => { res.Add((x.X, x.Y)); addToListIfLower(x.X, x.Y, res); });
    return res;
}

Console.WriteLine((p1.Select(x => x.V).Sum() + p1.Count, basins.OrderByDescending(x => x.Count).Take(3).Select(x => x.Count).Aggregate(1, (x, y) => x * y)));