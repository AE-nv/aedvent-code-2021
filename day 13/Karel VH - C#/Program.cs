var input = File.ReadAllLines("input.txt");
List<Point> coordinates = new();
List<Point> foldInstructions = new();
foreach (string line in input)
{
    if (line.StartsWith("fold along y="))
        foldInstructions.Add(new Point() { X = int.MaxValue, Y = int.Parse(line.Substring(13)) });
    else if (line.StartsWith("fold along x="))
        foldInstructions.Add(new Point() { Y = int.MaxValue, X = int.Parse(line.Substring(13)) });
    else if (!string.IsNullOrWhiteSpace(line))
        coordinates.Add(new Point() { Y = int.Parse(line.Split(",")[0]), X = int.Parse(line.Split(",")[1]) });
}
Fold(foldInstructions[0]);
Console.WriteLine(coordinates.Distinct().Count());
foreach (Point fold in foldInstructions)
{
    Fold(fold);
}
for (int i = 0; i < 7; i++)
{
    for (int j = 0; j < 45; j++)
    {
        if (coordinates.Contains(new Point() { X = i, Y = j }))
            Console.Write("#");
        else
            Console.Write(".");
    }
    Console.WriteLine();
}
void Fold(Point fold)
{
    coordinates.ForEach(x =>
    {
        if (x.Y >= fold.X)
            x.Y = (fold.X * 2 - x.Y);
        if (x.X >= fold.Y)
            x.X = (fold.Y * 2 - x.X);
    });
}

class Point
{
    public int X { get; set; }
    public int Y { get; set; }

    public override bool Equals(object obj)
    {
        if (obj is Point o) return o.X == X && o.Y == Y;
        return false;
    }
    public override int GetHashCode()
    {
        return X + Y;
    }
}