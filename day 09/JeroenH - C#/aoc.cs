var input = File.ReadAllLines("input.txt");

var grid = new Grid(input);

var part1 = (
    from p in grid.Points()
    let value = grid[p]
    where grid.Neighbours(p).All(n => value < grid[n])
    select value + 1
    ).Sum();

var part2 = (
    from low in
        from p in grid.Points()
        let neighbours = grid.Neighbours(p)
        let value = grid[p]
        where neighbours.All(n => value < grid[n])
        select p
    let size = grid.Basin(low).Distinct().Count()
    orderby size descending
    select size
    ).Take(3).Aggregate(1, (a, i) => i * a);

Console.WriteLine((part1, part2));

readonly record struct Point(int x, int y);

class Grid
{
    readonly string[] input;
    readonly Point origin = new Point(0, 0);
    readonly Point length;
    public Grid(string[] input)
    {
        this.input = input;
        this.length = new Point(input[0].Length, input.Length);
    }

    public int this[Point p] => input[p.y][p.x] - '0';
    public IEnumerable<Point> Basin(Point p)
    {
        yield return p;
        foreach (var n in Neighbours(p).Where(n => this[n] > this[p] && this[n] < 9))
        {
            foreach (var x in Basin(n))
                yield return x;
        }
    }

    public IEnumerable<Point> Points() =>
        from x in Range(origin.x, length.x) from y in Range(origin.y, length.y) select new Point(x, y);
    public IEnumerable<Point> Neighbours(Point p)
    {
        if (p.x > origin.x) yield return p with { x = p.x - 1 };
        if (p.y < length.y - 1) yield return p with { y = p.y + 1 };
        if (p.y > origin.y) yield return p with { y = p.y - 1 };
        if (p.x < length.x - 1) yield return p with { x = p.x + 1 };
    }
}