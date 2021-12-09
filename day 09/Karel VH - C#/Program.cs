List<List<int>> input = File.ReadAllLines("input.txt").Select(x => (x.ToCharArray().Select(x => int.Parse(x.ToString()))).ToList()).ToList();

List<(int X, int Y)> lowPoints = new();
List<List<(int X, int Y)>> basins = new();

IEnumerable<(int X, int Y)> GetNeighbors((int X, int Y) point)
{
    List<(int X, int Y)> distance = new() { (0, 1), (0, -1), (1, 0), (-1, 0) };
    return distance.Select(x => (x.X + point.X, x.Y + point.Y))
        .Where(x => x.Item1 > -1 && x.Item2 > -1 && x.Item1 < input.Count && x.Item2 < input[x.Item1].Count);
}

for (int i = 0; i < input.Count; i++)
{
    for (int j = 0; j < input[i].Count; j++)
    {
        if (!GetNeighbors((i, j)).Any(n => input[n.X][n.Y] <= input[i][j]))
        {
            lowPoints.Add((i, j));

            List<(int X, int Y)> result = new();
            result.Add((i, j));
            basins.Add(addToListIfLower(i, j, result));
        }
    }
}

List<(int X, int Y)> addToListIfLower(int i, int j, List<(int X, int Y)> result)
{
    IEnumerable<(int X, int Y)> lowerLocations = GetNeighbors((i, j)).Where(x => input[i][j] < input[x.X][x.Y]);

    if (lowerLocations.Any())
    {
        foreach (var k in lowerLocations)
        {
            if (input[k.X][k.Y] != 9)
            {
                if (!result.Any(result => result.X == k.X && result.Y == k.Y))
                {
                    result.Add((k.X, k.Y));
                    addToListIfLower(k.X, k.Y, result);
                }
            }
        }
    }
    return result;
}

Console.WriteLine(lowPoints.Select(x => input[x.X][x.Y]).Sum() + lowPoints.Count);
Console.WriteLine(basins.OrderByDescending(x => x.Count).Take(3).Select(x => x.Count).Aggregate(1, (x, y) => x * y));

// 494
//1048128