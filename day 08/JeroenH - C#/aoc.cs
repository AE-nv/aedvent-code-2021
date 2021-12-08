var input = File.ReadAllLines("input.txt");

var part1 = (
        from p in input
        from value in p.Split('|').Last().Split(' ')
        where value.Length is 2 or 3 or 4 or 7
        select value
    ).Count();

var part2 = (
        from line in input
        let fragments = line.Split('|', StringSplitOptions.TrimEntries)
        let encoding = fragments[0].Split(' ', StringSplitOptions.RemoveEmptyEntries).Select(s => new string(s.OrderBy(c => c).ToArray())).ToArray()
        let output = fragments[1].Split(' ', StringSplitOptions.RemoveEmptyEntries).Select(s => new string(s.OrderBy(c => c).ToArray())).ToArray()
        select (long)Decode(encoding, output)
    ).Sum();

Console.WriteLine((part1, part2));

int Decode(string[] encoding, string[] output)
{
    // Algorithm applied
    //     A
    //  F     B
    //     G
    //  E     C
    //     D
    // D | L |  A B C D E F G      |   algorithm
    //---|---|---------------------|---------------------------------------------------
    // 8 | 7 |  A B C D E F G      |   // l = 7 => 8
    //---|---|---------------------|---------------------------------------------------
    // 1 | 2 |  . B C . . . .      |   // l = 2 => 1 
    //   |   |                     |                 => bc
    //---|---|---------------------|---------------------------------------------------
    // 7 | 3 |  a B C . . . .      |   // l = 3 => 7 
    //   |   |                     |                 => [7] except bc => a
    //---|---|---------------------|---------------------------------------------------
    // 4 | 4 |  . B C . . F G      |   // l = 4 => 4
    //   |   |                     |                 => [4] except bc => fg
    //---|---|---------------------|---------------------------------------------------
    // 3 | 5 |  A B C D . . G      |   // l = 5 intersect bc == bc => 3
    //   |   |                     |                 => [3] except abcfg => d
    //   |   |                     |                 => [8] except abcdfg => e
    //   |   |                     |                 => [3] except abcd  => g
    //---|---|---------------------|---------------------------------------------------
    // 2 | 5 |  a B . d e . g      |   // l = 5 containing e => 2
    //   |   |                     |                 => [2] except adeg => b
    //---|---|---------------------|---------------------------------------------------
    // 5 | 5 |  a . C d . F g      |   // l = 5 not containing b => 5
    //   |   |                     |                 => [5] intersect bc => c
    //   |   |                     |                 => [5] intersect fg except g => f
    //---|---|---------------------|---------------------------------------------------
    // 0 | 6 |  a b c d e f .      |   // length 6 without g = 0
    // 9 | 6 |  a b c d . f g      |   // length 6 without e = 9
    // 6 | 6 |  a . c d e f g      |   // length 6 without b = 6
    var lookup = encoding.ToLookup(x => x.Length);
    string[] map = new string[10];
    map[8] = lookup[7].Single();
    map[1] = lookup[2].Single();
    var bc = map[1];
    map[4] = lookup[4].Single();
    var fg = map[4].Except(bc);
    map[7] = lookup[3].Single();
    var a = map[7].Except(bc).Single();
    var abc = Repeat(a, 1).Concat(bc);
    var abcfg = abc.Concat(fg);
    map[3] = lookup[5].Single(s => s.Intersect(bc).SequenceEqual(bc));
    var d = map[3].Except(abcfg).Single();
    var abcd = abc.Concat(Repeat(d, 1));
    var abcdfg = abcd.Concat(fg);
    var e = map[8].Except(abcdfg).Single();
    var g = map[3].Except(abcd).Single();
    map[2] = lookup[5].Single(s => s.Contains(e));
    var adeg = new[] { a, d, e, g };
    var b = map[2].Except(adeg).Single();
    map[5] = lookup[5].Single(s => !s.Contains(b));
    var c = map[5].Intersect(bc).Single();
    var f = map[5].Intersect(fg).Except(Repeat(g, 1)).Single();
    map[0] = lookup[6].Single(s => !s.Contains(g));
    map[9] = lookup[6].Single(s => !s.Contains(e));
    map[6] = lookup[6].Single(s => !s.Contains(b));
    var dictionary = Range(0, 10).ToDictionary(i => map[i], i => i);
    return dictionary[output[0]] * 1000 + dictionary[output[1]] * 100 + dictionary[output[2]] * 10 + dictionary[output[3]] * 1;
}