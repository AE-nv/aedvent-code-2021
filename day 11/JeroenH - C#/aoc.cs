using Builder = System.Collections.Immutable.ImmutableDictionary<Coordinate, int>.Builder;

var input = File.ReadAllLines("input.txt");

var grid = Grid.FromLines(input);

var part1 = Cycle(grid).Take(100).Sum(g => g.Flashed);

var part2 = Cycle(grid).TakeWhile(g => !g.AllFlashed).Count() + 1;

Console.WriteLine((part1, part2));

IEnumerable<Grid> Cycle(Grid grid)
{
    while (true)
    {
        grid = grid.Cycle();
        yield return grid;
    }
}

record Coordinate(int x, int y);
class Grid
{
    const int WIDTH = 10;
    const int HEIGHT = 10;
    private ImmutableDictionary<Coordinate, int> Cells { get; }

    public int Flashed { get; }

    public Grid(ImmutableDictionary<Coordinate, int> cells, int flashed)
    {
        Cells = cells;
        Flashed = flashed;
    }

    public bool AllFlashed => Flashed == Cells.Count;
    public static Grid FromLines(string[] lines) => new(Coordinates().ToImmutableDictionary(item => item, item => lines[item.y][item.x] - '0'), 0);
    static IEnumerable<Coordinate> Coordinates()
    {
        foreach (var (x, y) in
            from y in Range(0, 10) from x in Range(0, 10) select (x, y))
            yield return new Coordinate(x, y);
    }

    static IEnumerable<Coordinate> Neighbors(Coordinate c) =>
        from dx in Range(-1, 3)
        from dy in Range(-1, 3)
        where (dx, dy) is not (0, 0)
        where (c.x + dx, c.y + dy) is ( >= 0 and < WIDTH, _) and (_, >= 0 and < HEIGHT)
        select new Coordinate(c.x + dx, c.y + dy);
    public Grid Cycle()
    {
        var builder = Cells.ToBuilder();
        var flashed = new HashSet<Coordinate>();
        Flash(Coordinates(), builder, flashed);
        return new(builder.ToImmutable(), flashed.Count);
    }

    void Flash(IEnumerable<Coordinate> coordinates, Builder builder, HashSet<Coordinate> flashed)
    {
        foreach (var c in coordinates.Where(c => !flashed.Contains(c)))
        {
            builder[c]++;
        }

        foreach (var c in coordinates.Where(c => builder[c] > 9 && !flashed.Contains(c)))
        {
            flashed.Add(c);
            builder[c] = 0;
            Flash(Neighbors(c), builder, flashed);
        }
    }
}