var input = File.ReadAllLines("input.txt");

var algorithm = input[0];

var grid = CreateGrid(input.Skip(2));

var part1 = grid.Enhance(algorithm).Skip(1).First().Pixels.Values.Count(c => c == '#');
var part2 = grid.Enhance(algorithm).Skip(49).First().Pixels.Values.Count(c => c == '#');
Console.WriteLine((part1, part2));

Grid CreateGrid(IEnumerable<string> input) => new Grid((
    from line in input.Select((s, y) => (s, y)) from c in line.s.Select((c, x) => (c, x)) select (c.x, line.y, c.c)).ToImmutableDictionary(c => new Coordinate(c.x, c.y), c => c.c));

readonly record struct Coordinate(int x, int y);

record Grid(ImmutableDictionary<Coordinate, char> Pixels)
{
    public IEnumerable<Grid> Enhance(string algorithm)
    {
        var grid = this;
        bool odd = true;
        while (true)
        {
            grid = grid.Enhance(algorithm, odd);
            yield return grid;
            odd = !odd;
        }
    }

    (Coordinate min, Coordinate max)? _minmax;
    (Coordinate min, Coordinate max) MinMax
        => _minmax
        ?? (_minmax = Pixels.Keys.Aggregate(
                (min: new Coordinate(int.MaxValue, int.MaxValue),
                 max: new Coordinate(int.MinValue, int.MinValue)),
                (acc, v) => (new(Min(acc.min.x, v.x), Min(acc.min.y, v.y)), new(Max(acc.max.x, v.x), Max(acc.max.y, v.y))))).Value;

    IEnumerable<Coordinate> FullRange()
        => from y in Range(MinMax.min.y - 1, MinMax.max.y - MinMax.min.y + 3)
           from x in Range(MinMax.min.x - 1, MinMax.max.x - MinMax.min.x + 3)
           select new Coordinate(x, y);

    private Grid Enhance(string algorithm, bool odd) => new Grid((
        from c in FullRange()
        let index = (
            from j in Range(-1, 3) 
            from i in Range(-1, 3) 
            let n = new Coordinate(c.x + i, c.y + j) 
            select Pixels.GetValueOrDefault(n, odd ? '.' : '#')
            ).Aggregate(0, (index, c) => (index << 1) | (c == '#' ? 1 : 0))
        let p = algorithm[index]
        select (c, p)).ToImmutableDictionary(x => x.c, x => x.p));
}