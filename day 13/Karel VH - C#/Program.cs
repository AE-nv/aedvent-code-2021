var input = ReadAllLines("input.txt").ToList();
List<Point> coordinates = input.Where(x => x.Contains(',')).Select(x => new Point() { Y = int.Parse(x.Split(",")[0]), X = int.Parse(x.Split(",")[1]) }).ToList();
List<Point> foldInstructions = input.Where(x => x.Contains('='))
    .Select(x => (v: int.Parse(x[13..]), x: x.Contains('x')))
    .Select(x => x.x ? new Point() { X = x.v } : new Point() { Y = x.v }).ToList();

Fold(foldInstructions[0]);
WriteLine(coordinates.Distinct().Count());

foldInstructions.ForEach(f => Fold(f));

for (int i = 0; i < 7; i++)
{
    for (int j = 0; j < 45; j++)
        Write(coordinates.Contains(new Point() { X = i, Y = j }) ? "@" : " ");
    WriteLine();
}

void Fold(Point fold) => coordinates.ForEach(x =>
{
    x.Y = x.Y >= fold.X ? (fold.X * 2 - x.Y) : x.Y;
    x.X = x.X >= fold.Y ? (fold.Y * 2 - x.X) : x.X;
});


class Point
{
    public int X { get; set; } = int.MaxValue;
    public int Y { get; set; } = int.MaxValue;
    public override bool Equals(object obj) => obj is Point o ? o.X == X && o.Y == Y : false;
    public override int GetHashCode() => X + Y;
}