internal record Point(int X, int Y);
internal record Line
{
    public Point start;
    public Point end;

    public int MaxX => start.X > end.X ? start.X : end.X;
    public int MaxY => start.Y > end.Y ? start.Y : end.Y;
    public Line(string x)
    {
        var s = x.Split(" -> ");
        start = new Point(int.Parse(s[0].Split(",")[0]), int.Parse(s[0].Split(",")[1]));
        end = new Point(int.Parse(s[1].Split(",")[0]), int.Parse(s[1].Split(",")[1]));
    }

    public IEnumerable<Point> GetCoverage(bool diagonal)
    {
        int index = 0;
        int xMultiplier = start.X > end.X ? -1 : 1;
        int yMultiplier = start.Y > end.Y ? -1 : 1;
        if (diagonal && start.X != end.X && start.Y != end.Y)
        {
            while (start.Y + yMultiplier * index != end.Y + 1 * yMultiplier)
            {
                yield return new Point(start.X + xMultiplier * index, start.Y + yMultiplier * index++);
            }
        }
        if (start.X == end.X)
        {
            while (start.Y + index * yMultiplier != end.Y + 1 * yMultiplier)
            {
                yield return new Point(start.X, start.Y + yMultiplier * index++);
            }
        }
        if (start.Y == end.Y)
        {
            while (start.X + index * xMultiplier != end.X + 1 * xMultiplier)
            {
                yield return new Point(start.X + xMultiplier * index++, start.Y);
            }
        }

    }

}