var input = File.ReadAllLines("input.txt").Select(x => x.Select(x => int.Parse(x.ToString())).ToList()).ToList();

List<(int X, int Y)> lowPoints = new();
List<List<(int X, int Y)>> basins = new();

IEnumerable<(int X, int Y)> GetNeighbors((int X, int Y) p)
{
    return new List<(int X, int Y)> { (0, 1), (0, -1), (1, 0), (-1, 0) }.Select(x => (X: x.X + p.X, Y: x.Y + p.Y))
        .Where(n => n.X > -1 && n.Y > -1 && n.X < input.Count && n.Y < input[n.X].Count);
}

for (int i = 0; i < input.Count; i++)
{
    for (int j = 0; j < input[i].Count; j++)
    {
        if (!GetNeighbors((i, j)).Any(n => input[n.X][n.Y] <= input[i][j]))
        {
            lowPoints.Add((i, j));
            basins.Add(addToListIfLower(i, j, new List<(int, int)> { (i, j) }));
        }
    }
}

List<(int, int)> addToListIfLower(int i, int j, List<(int X, int Y)> res)
{
    IEnumerable<(int X, int Y)> lowers = GetNeighbors((i, j)).Where(x => input[i][j] < input[x.X][x.Y]);
    if (lowers.Any())
    {
        foreach (var (X, Y) in lowers)
        {
            if (input[X][Y] != 9 && !res.Any(r => r.X == X && r.Y == Y))
            {
                res.Add((X, Y));
                addToListIfLower(X, Y, res);
            }
        }
    }
    return res;
}

Console.WriteLine((lowPoints.Select(x => input[x.X][x.Y]).Sum() + lowPoints.Count, basins.OrderByDescending(x => x.Count).Take(3).Select(x => x.Count).Aggregate(1, (x, y) => x * y)));