Console.WriteLine((new Solver().P1()));
Console.WriteLine((new Solver().P2()));

class Solver
{
    private List<List<K>> _input;

    public int P1()
    {
        _input = File.ReadAllLines("input.txt").Select(x => x.Select(x => new K() { C = int.MaxValue, V = int.Parse(x.ToString()) }).ToList()).ToList();
        CreateNeighbors();
        UpdateCostsWithDijkstra();
        return _input[^1][^1].C;
    }
    public int P2()
    {
        _input = File.ReadAllLines("input.txt").Select(x => x.Select(x => new K() { C = int.MaxValue, V = int.Parse(x.ToString()) }).ToList()).ToList();
        MakeItHarder();
        CreateNeighbors();
        UpdateCostsWithDijkstra();
        return _input[^1][^1].C;
    }

    private void MakeItHarder()
    {
        int originalLength = _input.Count;
        for (int r = 0; r < originalLength; r++)
        {
            for (int c = originalLength; c < originalLength * 5; c++)
            {
                _input[r].Add(new K() { C = int.MaxValue });
            }
        }
        for (int r = originalLength; r < originalLength * 5; r++)
        {
            _input.Add(new List<K>());
            for (int c = 0; c < originalLength * 5; c++)
            {
                _input[r].Add(new K() { C = int.MaxValue });
            }
        }

        for (int r = 0; r < originalLength * 5; r++)
        {
            if (_input[r][0].V == 0)
            {
                for (int i = 0; i < originalLength; i++)
                {
                    _input[r][i].V = _input[r - originalLength][i].V >= 9 ? 1 : _input[r - originalLength][i].V + 1;
                }
            }

            for (int i = 0; i < originalLength * 5; i++)
            {
                if (_input[r][i].V == 0)
                {
                    _input[r][i].V = _input[r][i - originalLength].V >= 9 ? 1 : _input[r][i - originalLength].V + 1;
                }
            }
        }
    }

    private void UpdateCostsWithDijkstra()
    {
        PriorityQueue<K, int> toVisit = new();
        _input[0][0].Neighbors.ForEach(x => { x.C = x.V; toVisit.Enqueue(x, x.C); });
        HashSet<K> visited = new() { _input[0][0] };
        while (toVisit.Count > 0)
        {
            K current = toVisit.Dequeue();
            visited.Add(current);
            current.Neighbors.ForEach(x =>
            {
                if (!visited.Contains(x))
                {
                    if (x.C > current.C + x.V)
                    {
                        x.C = current.C + x.V;
                        toVisit.Enqueue(x, x.C);
                    }
                }
            });
        }
    }

    private void CreateNeighbors()
    {
        for (int i = 0; i < _input.Count(); i++)
        {
            for (int j = 0; j < _input.Count(); j++)
            {
                new List<(int X, int Y)> { (0, 1), (0, -1), (1, 0), (-1, 0) }
                .Select(x => (X: x.X + i, Y: x.Y + j))
                .Where(n => n.X > -1 && n.Y > -1 && n.X < _input.Count() && n.Y < _input.Count()).ToList().ForEach(x => _input[i][j].Neighbors.Add(_input[x.X][x.Y]));
            }
        }
    }

}

class K
{
    public int V { get; set; }
    public int C { get; set; }
    public List<K> Neighbors { get; set; } = new();
}