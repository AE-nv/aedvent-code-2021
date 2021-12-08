internal class SignalsDecoder
{
    private Dictionary<string, int> _displayWiresMapping = new();

    //read from top to down, left to right
    private Dictionary<int, List<string>> _combinations = new();

    public SignalsDecoder(List<Signal> signals)
    {
        Signal nr1 = signals.Where(x => x.Number == 1).First();
        Signal nr4 = signals.Where(x => x.Number == 4).First();
        Signal nr7 = signals.Where(x => x.Number == 7).First();
        Signal nr8 = signals.Where(x => x.Number == 8).First();

        Console.WriteLine("nr1: " + string.Join("", nr1.Letters));
        Console.WriteLine("nr4: " + string.Join("", nr4.Letters));
        Console.WriteLine("nr7: " + string.Join("", nr7.Letters));
        Console.WriteLine("nr8: " + string.Join("", nr8.Letters));

        Console.WriteLine("nr with 6 digits: " + string.Join(", ", signals.Where(x => x.Letters.Count == 6).Select(x => string.Join("", x.Letters))));

        //Find index 0 by comparing 1 and 7
        string letterAtIndex0 = nr7.Letters.Except(nr1.Letters).First().ToString();

        //Find three by using the rest of 7
        Signal nr3 = signals.Where(x => x.Letters.Count == 5 && nr7.Letters.All(x.Letters.Contains)).First();
        Console.WriteLine("nr3: " + string.Join("", nr3.Letters));

        //Find index 1 by comparing 3 and 4
        string letterAtIndex1 = nr4.Letters.Except(nr3.Letters).First().ToString();

        //Find nr9 by adding index2 to nr3
        Signal nr9 = signals.Where(x => x.Letters.Count == 6 && string.Join("", x.Letters.Except(nr3.Letters)).Equals(letterAtIndex1)).First();
        Console.WriteLine("nr9: " + string.Join("", nr9.Letters));

        //Find index 4 by comparing nr9 and nr8
        string letterAtIndex4 = nr8.Letters.Except(nr9.Letters).First().ToString();

        //Find nr2 by using 3 + index 4
        Signal nr2 = signals.Where(x => x.Letters.Count == 5 && !x.Equals(nr3) && x.Letters.All((nr3.Letters.Append(letterAtIndex4)).Contains)).First();
        Console.WriteLine("nr2: " + string.Join("", nr2.Letters));

        string letterAtIndex5 = nr3.Letters.Except(nr2.Letters).First().ToString();
        string letterAtIndex2 = string.Join("", nr7.Letters).Except(letterAtIndex0).Except(letterAtIndex5).First().ToString();

        Signal nr5 = signals.Where(x => x.Letters.Count == 5 && nr9.Letters.Except(x.Letters).First().ToString().Equals(letterAtIndex2)).First();
        string letterAtIndex3 = nr4.Letters.Except((letterAtIndex1 + letterAtIndex2 + letterAtIndex5).ToCharArray().Select(x => x.ToString()).ToList()).First().ToString();
        Console.WriteLine("nr5: " + string.Join("", nr5.Letters));


        Signal nr0 = signals.Where(x => x.Letters.Count == 6 && x.Letters.Contains(letterAtIndex2) && x.Letters.Except(nr9.Letters).DefaultIfEmpty("").First().ToString().Equals(letterAtIndex4)).First();
        Console.WriteLine("nr0: " + string.Join("", nr0.Letters));


        Signal nr6 = signals.Where(x => x.Letters.Count == 6 && x.Letters.All((nr5.Letters.Append(letterAtIndex4)).Contains) && !x.Equals(nr5)).First();
        Console.WriteLine("nr6: " + string.Join("", nr6.Letters));


        _displayWiresMapping.Add(string.Join("", nr0.Letters), 0);
        _displayWiresMapping.Add(string.Join("", nr1.Letters), 1);
        _displayWiresMapping.Add(string.Join("", nr2.Letters), 2);
        _displayWiresMapping.Add(string.Join("", nr3.Letters), 3);
        _displayWiresMapping.Add(string.Join("", nr4.Letters), 4);
        _displayWiresMapping.Add(string.Join("", nr5.Letters), 5);
        _displayWiresMapping.Add(string.Join("", nr6.Letters), 6);
        _displayWiresMapping.Add(string.Join("", nr7.Letters), 7);
        _displayWiresMapping.Add(string.Join("", nr8.Letters), 8);
        _displayWiresMapping.Add(string.Join("", nr9.Letters), 9);

    }

    public int Decode(Signal signal)
    {
        var p = _displayWiresMapping.Where(x => signal.Letters.Count == x.Key.Length && string.Join("", signal.Letters).All(x.Key.Contains));
        return p.First().Value;
    }
}