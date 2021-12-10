var input = File.ReadAllLines("input.txt");

var part1 = (
    from line in input
    select Score1(line)).Sum();

var part2 = (
    from line in input
    where Score1(line) == 0
    let score = Score2(line)
    orderby score
    select score).Median();

Console.WriteLine((part1, part2));

int Score1(string line)
{
    List<char> open = new();
    foreach (var c in line)
    {
        if (c is '{' or '[' or '(' or '<')
        {
            open.Add(c);
        }
        else
        {
            var score = c switch
            {
                ')' when open.Last() != '(' => 3,
                ']' when open.Last() != '[' => 57,
                '}' when open.Last() != '{' => 1197,
                '>' when open.Last() != '<' => 25137,
                _ => 0
            };
            if (score != 0)
                return score;
            else
                open.RemoveAt(open.Count - 1);
        }
    }

    return 0;
}

long Score2(string line)
{
    List<char> open = new();
    foreach (var c in line)
    {
        if (c is '{' or '[' or '(' or '<')
        {
            open.Add(c);
        }
        else
        {
            open.RemoveAt(open.Count - 1);
        }
    }

    open.Reverse();

    return (
        from c in open
        select c switch
        {
            '<' => 4,
            '{' => 3,
            '[' => 2,
            '(' => 1,
            _ => throw new Exception()
        }).Aggregate(0L, (total, i) => 5 * total + i);
}

static class Extension
{
    public static long Median(this IEnumerable<long> input) => input.OrderBy(n => n).ElementAt(input.Count() / 2);
}