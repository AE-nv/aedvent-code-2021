internal record Point(int X, int Y)
{
};
internal record Line
{
    public Point start;
    public Point end;

    public Line(string x)
    {
        var s = x.Split(" -> ");
        start = new Point(int.Parse(s[0].Split(",")[0]), int.Parse(s[0].Split(",")[1]));
        end = new Point(int.Parse(s[1].Split(",")[0]), int.Parse(s[1].Split(",")[1]));
    }

    public IEnumerable<Point> GetCoverage(bool diagonal)
    {
        int index = 0;
        int indexDirection = 1;
        if (diagonal && start.X != end.X && start.Y != end.Y)
        {
            int indexDirectionX = 1;
            int indexDirectionY = 1;
            if (start.Y + index >= end.Y)
            {
                indexDirectionY = -1;
            }
            if (start.X + index >= end.X)
            {
                indexDirectionX = -1;
            }
            while (start.Y + indexDirectionY * index != end.Y + 1 * indexDirectionY)
            {
                yield return new Point(start.X + indexDirectionX * index, start.Y + indexDirectionY * index++);
            }

        }
        if (start.X == end.X)
        {
            if (start.Y + index >= end.Y)
            {
                indexDirection = -1;
            }
            while (start.Y + index * indexDirection != end.Y + 1 * indexDirection)
            {
                yield return new Point(start.X, start.Y + indexDirection * index++);
            }
        }
        if (start.Y == end.Y)
        {
            if (start.X + index >= end.X)
            {
                indexDirection = -1;
            }
            while (start.X + index * indexDirection != end.X + 1 * indexDirection)
            {
                yield return new Point(start.X + indexDirection * index++, start.Y);
            }
        }
    }

}