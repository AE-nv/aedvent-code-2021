namespace day04
{
    internal record Number
    {
        public string Val;

        public Number(string val)
        {
            Val = val;
        }

        public bool Found = false;
    }
    internal class Board
    {
        public List<List<Number>> HorizontalNumbers { get; set; }
        public List<List<Number>> VerticalNumbers { get; set; }

        public Board(IEnumerable<string> boardNumbers)
        {
            HorizontalNumbers = boardNumbers.Select(x => x.Trim().Replace("  ", " ").Split(" ").Select(y => new Number(y)).ToList()).ToList();
            VerticalNumbers = Enumerable.Range(0, boardNumbers.Count())
                .Select(x => boardNumbers.Select(y => y.Trim().Replace("  ", " ").Split(" ")[x]).Select(y => new Number(y)).ToList()).ToList();
        }


        internal void Update(string nr)
        {
            HorizontalNumbers.ForEach(row => row.ForEach(n => { if (n.Val.Equals(nr)) n.Found = true; }));
            VerticalNumbers.ForEach(row => row.ForEach(n => { if (n.Val.Equals(nr)) n.Found = true; }));
        }

        public List<string> NumbersLeft
        {
            get
            {
                //Only count horizontal since we copied all results to vertical aswell
                return HorizontalNumbers.Select(x => x.Where(y => !y.Found)).SelectMany(r => r).Select(x => x.Val).ToList();
            }
        }

        public bool IsComplete
        {
            get
            {
                return HorizontalNumbers.Any(x => x.Count(y => y.Found) == HorizontalNumbers.Count)
                    || VerticalNumbers.Any(x => x.Count(y => y.Found) == VerticalNumbers.Count);
            }
        }
    }
}
