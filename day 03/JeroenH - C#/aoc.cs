var input = File.ReadAllLines("input.txt");
var numbers = input.Select(s => Convert.ToInt32(s, 2)).ToImmutableArray();
var part1 = GetBitmasks(numbers).Multiply();
var part2 = DoPart2();
Console.WriteLine((part1, part2));
(int max, int min) GetBitmasks(IEnumerable<int> numbers) => (
    from p in
        from n in numbers
        from shift in Range(0, input[0].Length)
        let bit = (n >> shift) & 1
        group bit by (bit, shift) into g
        let bit = g.Key.bit
        let shift = g.Key.shift
        let count = g.Count()
        group (count, bit) by shift into g
        select (shift: g.Key, max: g.MaxBy(x => x.count).bit, min: g.MinBy(x => x.count).bit)
    select (max: p.max << p.shift, min: p.min << p.shift)).Aggregate((max: 0, min: 0), (n, i) => (n.max | i.max, n.min | i.min));
int DoPart2()
{
    int v1 = FindNumberByBitCounts(numbers, (x, y) => x >= y ? 1 : 0);
    int v0 = FindNumberByBitCounts(numbers, (x, y) => x >= y ? 0 : 1);
    return v1 * v0;
}

int FindNumberByBitCounts(IEnumerable<int> numbers, Func<int, int, int> selectBit)
{
    var shift = input[0].Length - 1;
    while (shift >= 0 && numbers.Skip(1).Any())
    {
        var lookup = GetBitCounts(numbers);
        var bit = selectBit(lookup[(1, shift)].SingleOrDefault(), lookup[(0, shift)].SingleOrDefault());
        numbers = (
            from n in numbers
            where ((n >> shift) & 1) == bit
            select n).ToImmutableArray();
        shift--;
    }

    return numbers.Single();
}

ILookup<(int bit, int shift), int> GetBitCounts(IEnumerable<int> numbers) => (
    from n in numbers
    from shift in Range(0, input[0].Length)
    let bit = (n >> shift) & 1
    group bit by (bit, shift) into g
    select (g.Key, count: g.Count())).ToLookup(x => x.Key, x => x.count);
static class Extension
{
    public static int Multiply(this (int x, int y) p) => p.x * p.y;
}