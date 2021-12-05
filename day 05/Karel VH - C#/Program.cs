IEnumerable<Line> input = File.ReadAllLines("input.txt").Select(x => new Line(x));

Line xMaxLine = input.MaxBy(x => x.start.X > x.end.X ? x.start.X : x.end.X);
int xMax = xMaxLine.start.X > xMaxLine.end.X ? xMaxLine.start.X : xMaxLine.end.X;

Line yMaxLine = input.MaxBy(x => x.start.Y > x.end.Y ? x.start.Y : x.end.Y);
int yMax = yMaxLine.start.Y > yMaxLine.end.Y ? yMaxLine.start.Y : yMaxLine.end.Y;

List<List<int>> map = new();
for (int row = 0; row <= yMax; row++)
{
    map.Add(new(new int[xMax + 1]));
}

var noDiagonals = input.Select(x => x.GetCoverage(false)).SelectMany(r => r).GroupBy(x => x).Where(x => x.Count() >= 2).Count();
var yesDiagonals = input.Select(x => x.GetCoverage(true)).SelectMany(r => r).GroupBy(x => x).Where(x => x.Count() >= 2).Count();

Console.WriteLine((noDiagonals, yesDiagonals));