var input = File.ReadAllLines("input.txt");


var part1 = FindLastMove(new Grid((
    from row in input.Select((line, y) => (line, y))
    from col in row.line.Select((c, x) => (c, x))
    where col.c != '.'
    select (p: new P(col.x, row.y), col.c)).ToImmutableDictionary(x => x.p, x => x.c), input.Length, input[0].Length));

Console.WriteLine(part1);

int FindLastMove(Grid initial)
{
    int step = 0;
    var grid = initial;
    while (true)
    {
        step++;
        var moves1 = grid.ValidMoves('>');
        (grid, int n1) = grid.Apply(moves1, '>');
        var moves2 = grid.ValidMoves('v');
        (grid, int n2) = grid.Apply(moves2, 'v');
        if (n1 + n2 == 0)
            return step;
    }
}

readonly record struct P(int x, int y)
{
    public P Next(int width, int height, char dir) => dir switch
    {
        '>' => this with { x = (x + 1) % width },
        'v' => this with { y = (y + 1) % height },
        _ => throw new NotImplementedException()
    };
}

record Grid(ImmutableDictionary<P, char> items, int Height, int Width)
{
    public IEnumerable<P> ValidMoves(char direction) => (
        from item in items
        where item.Value == direction && !items.ContainsKey(item.Key.Next(Width, Height, direction))
        select item.Key);
    public (Grid grid, int n) Apply(IEnumerable<P> moves, char c)
    {
        int n = 0;
        var builder = items.ToBuilder();
        foreach (var m in moves)
        {
            builder.Remove(m);
            builder[m.Next(Width, Height, c)] = c;
            n++;
        }

        return (new Grid(builder.ToImmutable(), Height, Width), n);
    }
}