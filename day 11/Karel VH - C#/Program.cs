List<List<int>> inp = File.ReadAllLines("input.txt").Select(x => x.Select(y => int.Parse(y.ToString())).ToList()).ToList();
List<int> r = Enumerable.Range(0, 10).ToList();

List<(int X, int Y)> GetNeighbors((int X, int Y) p)
    => new List<(int X, int Y)> { (0, 1), (0, -1), (1, 0), (-1, 0), (-1, -1), (1, 1), (-1, 1), (1, -1) }
        .Select(x => (X: x.X + p.X, Y: x.Y + p.Y))
        .Where(n => n.X > -1 && n.Y > -1 && n.X < 10 && n.Y < 10).ToList();

List<(int X, int Y)> flashers = new();
(int P1, int P2, int Sum) = (0, 0, 0);
while (Sum != 100)
{
    Sum = 0;
    r.ForEach(x => r.ForEach(y => inp[x][y]++));
    Update();
    while (flashers.Count > 0)
    {
        flashers.ToList().ForEach(x =>
        {
            inp[x.X][x.Y] = 0;
            GetNeighbors((x.X, x.Y)).ForEach(n => inp[n.X][n.Y] += inp[n.X][n.Y] > 0 ? 1 : 0);
            if (P2 < 101) P1++;
            Sum++;
        });
        flashers = new();
        Update();
    }
    P2++;
}
void Update() => r.ForEach(x => r.ForEach(y => { if (inp[x][y] >= 10) flashers.Add((x, y)); }));

Console.WriteLine((P1, P2));
