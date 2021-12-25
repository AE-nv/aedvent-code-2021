// should work for any input (at least if all inputs differ only by the parameters for the same instructions...)

var input = File.ReadAllLines("input.txt");

var regex = new Regex(@"inp w     mul x 0   add x z   mod x 26  div z (?<p1>\d+) +add x (?<p2>[-\d]+) +eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y (?<p3>\d+) +mul y x   add z y");

var parameters = (
    from line in FoldInput()
    let match = regex.Match(line)
    select (p1: match.GetInt32("p1"), p2: match.GetInt32("p2"), p3: match.GetInt32("p3"))).ToImmutableArray();

var part1 = FindValidNumber(1);
var part2 = FindValidNumber(2);

Console.WriteLine((part1, part2));

IEnumerable<string> FoldInput()
{
    var sb = new StringBuilder();
    foreach (var instruction in input)
    {
        if (sb.Length > 0 && instruction.StartsWith("inp"))
        {
            yield return sb.ToString();
            sb.Clear();
        }

        sb.Append(instruction.PadRight(10));
    }
    yield return sb.ToString();
}

// reverse engineered solution (yes, I tried the brute force path...)
// noticed that program contains similar instructions
/*
foreach (var line in items)
    Console.WriteLine(line);

This gives (for my input):

inp w     mul x 0   add x z   mod x 26  div z 1   add x 15  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 9   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 1   add x 11  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 1   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 1   add x 10  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 11  mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 1   add x 12  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 3   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 26  add x -11 eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 10  mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 1   add x 11  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 5   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 1   add x 14  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 0   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 26  add x -6  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 7   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 1   add x 10  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 9   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 26  add x -6  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 15  mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 26  add x -6  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 4   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 26  add x -16 eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 10  mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 26  add x -4  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 4   mul y x   add z y   
inp w     mul x 0   add x z   mod x 26  div z 26  add x -2  eql x w   eql x 0   mul y 0   add y 25  mul y x   add y 1   mul z y   mul y 0   add y w   add y 9   mul y x   add z y

// which is equivalent to:
foreach (var i in input)
{
    w = i;              // inp w 
    x *= 0;             // mul x 0
    x += z;             // add x z
    x %= 26;            // mod x 26
    z /= p1;            // div z p1  <- first param
    x += p2;            // add x p2  <- second param
    x = x == w ? 1 : 0; // eql x w   
    x = x == 0 ? 1 : 0; // eql x 0   
    y *= 0;             // mul y 0   
    y += 25;            // add y 25  
    y *= x;             // mul y x   
    y += 1;             // add y 1   
    z *= y;             // mul z y   
    y *= 0;             // mul y 0   
    y += w;             // add y w   
    y += p3;            // add y p3  <- third param
    y *= x;             // mul y x   
    z += y;             // add z y       
}

After much sweating, I reversed this to:
*/

IEnumerable<int> CalculatePreviousZ(int w, int p1, int p2, int p3, int z)
{
    var x = z - w - p3;
    if (x % 26 == 0)
    {
        yield return x / 26 * p1;
    }
    if ((0..26).Contains(w-p2))
    {
        yield return w - p2 + z * p1;
    }
}

long FindValidNumber(int part)
{
    // find possible input values for which result (z) = 0
    // keep a list of digits that, for each parameter combination (in reverse)
    // leads to the final result (0)

    // the set of z-values for which to calculate the previous z-value(s) leading to this number
    var zvalues = new HashSet<int>() { 0 };

    // cache the digits which lead to a specific z-value (list will contain 14 values at the end)
    var results = new Dictionary<int, ImmutableList<int>>(); 
    var digits = Range(1, 9).ToArray();
    if (part == 2) digits = digits.Reverse().ToArray();
    foreach (var (p1,p2,p3) in parameters.Reverse())
    {
        var q = from z in zvalues
                from w in digits
                from previous in CalculatePreviousZ(w, p1, p2, p3, z)
                let list = results.ContainsKey(z) ? results[z] : ImmutableList<int>.Empty
                select (previous, list.Insert(0,w));

        var previousvalues = new HashSet<int>();
        foreach (var (previous, list) in q)
        {
            previousvalues.Add(previous);
            results[previous] = list;
        }
        zvalues = previousvalues;
    }

    return results[0].Reverse().Select((d, i) => d * (long)Math.Pow(10, i)).Sum();
}
static class Ex
{
    public static int GetInt32(this Match m, string name) => int.Parse(m.Groups[name].Value);

    public static bool Contains(this Range r, int v) => r.Start.Value <= v && v < r.End.Value;
}