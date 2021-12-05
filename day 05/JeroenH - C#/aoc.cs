var input = File.ReadAllLines("input.txt");
var lines = input.Select(Line.Parse).ToImmutableArray();
var part1 = CountOverlaps(lines.Where(l => l.IsStraightLine));
var part2 = CountOverlaps(lines);
Console.WriteLine((part1, part2));
int CountOverlaps(IEnumerable<Line> lines) => lines.SelectMany(l => l.Points()).GroupBy(p => p).Select(g => g.Count()).Where(c => c >= 2).Sum();
readonly record struct Line(Point from, Point to)
{
    static Regex regex = new Regex(@"(?<x1>\d+),(?<y1>\d+) -> (?<x2>\d+),(?<y2>\d+)");
    internal static Line Parse(string s)
    {
        var match = regex.Match(s);
        Point p1 = new(int.Parse(match.Groups["x1"].Value), int.Parse(match.Groups["y1"].Value));
        Point p2 = new(int.Parse(match.Groups["x2"].Value), int.Parse(match.Groups["y2"].Value));
        return new Line(p1, p2);
    }

    internal bool IsStraightLine => (to.y - from.y, to.x - from.x) is (_, 0) or (0, _);
    internal IEnumerable<Point> Points()
    {
        var p = from;
        while (true)
        {
            yield return p;
            if (p == to)
                break;
            p = p.Next(to);
        }
    }
}

readonly record struct Point(int x, int y)
{
    internal Point Next(Point end) => this with
    {
        x = (end.x - x) switch
        {
            < 0 => x - 1,
            0 => x,
            > 0 => x + 1
        },
        y = (end.y - y) switch
        {
            < 0 => y - 1,
            0 => y,
            > 0 => y + 1
        }
    };
}