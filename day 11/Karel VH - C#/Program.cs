List<List<int>> input = File.ReadAllLines("input.txt").Select(x => x.Select(x => int.Parse(x.ToString())).ToList()).ToList();

List<(int X, int Y)> GetNeighbors((int X, int Y) p)
{
    return new List<(int X, int Y)> { (0, 1), (0, -1), (1, 0), (-1, 0), (-1, -1), (1, 1), (-1, 1), (1, -1) }
        .Select(x => (X: x.X + p.X, Y: x.Y + p.Y))
        .Where(n => n.X > -1 && n.Y > -1 && n.X < input.Count && n.Y < input[n.X].Count).ToList();
}

void Flash((int X, int Y) octo)
{
    GetNeighbors((octo.X, octo.Y)).ForEach(n =>
    {
        if (input[n.X][n.Y] > 0)
        {
            input[n.X][n.Y]++;
        }
    });
}


int sum = 0;
int flashStep = 0;
List<(int X, int Y)> flashers = new();
for (int i = 1; i < 1000; i++)
{
    Enumerable.Range(0, input.Count).ToList().ForEach(x => Enumerable.Range(0, input.Count).ToList().ForEach(y => input[x][y]++));
    ScanFlashers();
    int flashSum = 0;
    while (flashers.Count > 0)
    {
        flashers.ToList().ForEach(x =>
        {
            input[x.X][x.Y] = 0;
            Flash((x.X, x.Y));
            if (i < 101)
                sum++;
            flashSum++;
        });
        ScanFlashers();

    }
    if (flashSum == input.Count * input[0].Count)
    {
        flashStep = i;
        break;
    }

    input.ForEach(x => Console.WriteLine(string.Join("", x)));
    Console.WriteLine("----------------------------");
}

void ScanFlashers()
{
    flashers = new();
    Enumerable.Range(0, input.Count).ToList().ForEach(x => Enumerable.Range(0, input.Count).ToList().ForEach(y => { if (input[x][y] >= 10) flashers.Add((x, y)); }));
}

Console.WriteLine((sum, flashStep));

