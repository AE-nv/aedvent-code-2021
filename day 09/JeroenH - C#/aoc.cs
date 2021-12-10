var grid = new Grid(File.ReadAllLines("input.txt"));

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

        var q = from n in Neighbours(p) 
                where this[n] > this[p] && this[n] < 9
                from b in Basin(n)
                select b;

        foreach (var n in q) yield return n;
    }

    public IEnumerable<Point> Points() =>
        from x in Range(origin.x, length.x) 
        from y in Range(origin.y, length.y) 
        select new Point(x, y);
    public IEnumerable<Point> Neighbours(Point p) => 
        from d in new (int x, int y)[] { (-1, 0), (0, 1), (1, 0), (0, -1) }
        where (p.x + d.x, p.y + d.y) is (>= 0 and < 100, >= 0 and < 100)  
        select new Point(p.x + d.x, p.y + d.y);
}