internal class Signal
{
    Dictionary<int, int> displayWires = new Dictionary<int, int>
    {
        { 2, 1 },
        { 4, 4 },
        { 3, 7 },
        { 7, 8 },
    };

    public List<string> Letters { get; }

    public Signal(string letters)
    {
        this.Letters = letters.ToCharArray().Select(x => x.ToString()).ToList();
    }

    public int Number => displayWires.ContainsKey(Letters.Count) ? displayWires[Letters.Count] : -1;
}