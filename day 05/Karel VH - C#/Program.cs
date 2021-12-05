IEnumerable<Line> input = File.ReadAllLines("input.txt").Select(x => new Line(x));

int xMax = input.MaxBy(x => x.MaxX).MaxX;
int yMax = input.MaxBy(x => x.MaxX).MaxY;

var noDiagonals = input.Select(x => x.GetCoverage(false)).SelectMany(r => r).GroupBy(x => x).Where(x => x.Count() >= 2).Count();
var yesDiagonals = input.Select(x => x.GetCoverage(true)).SelectMany(r => r).GroupBy(x => x).Where(x => x.Count() >= 2).Count();

Console.WriteLine((noDiagonals, yesDiagonals));