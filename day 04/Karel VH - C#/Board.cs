internal record Number(string Val)
{
    public bool Found = false;
};

internal class Board
{
    public List<List<Number>> Numbers { get; set; }

    public Board(IEnumerable<string> boardNumbers)
    {
        Numbers = boardNumbers.Select(x => x.Split(" ").Select(y => new Number(y)).ToList()).ToList();
        Numbers.AddRange(Enumerable.Range(0, boardNumbers.Count())
            .Select(x => boardNumbers.Select(y => y.Split(" ")[x]).Select(y => new Number(y)).ToList()).ToList());
    }


    internal void Update(string nr)
    {
        Numbers.ForEach(row => row.ForEach(n => { if (n.Val.Equals(nr)) n.Found = true; }));
    }

    public int NumbersLeftSum
    {
        get
        {
            return Numbers.Select(x => x.Where(y => !y.Found)).SelectMany(r => r).Select(x => x.Val).Select(x => int.Parse(x)).Sum() / 2;
        }
    }

    public bool IsComplete
    {
        get
        {
            return Numbers.Any(x => x.Count(y => y.Found) == Numbers[0].Count);
        }
    }
}

