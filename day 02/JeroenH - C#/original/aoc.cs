var input = File.ReadAllLines("input.txt");
var regex = new Regex(@"(?<direction>\w+) (?<value>\d+)", RegexOptions.Compiled);
var instructions = (
    from s in input
    where !string.IsNullOrEmpty(s)
    let m = regex.Match(s)
    select new Instruction(m.Groups["direction"].Value, int.Parse(m.Groups["value"].Value))).ToImmutableArray();
var part1 = instructions.Aggregate(new Pos(0, 0), (p, i) => i.direction[0] switch
{
    'f' => new(p.x + i.value, p.y),
    'u' => new(p.x, p.y - i.value),
    'd' => new(p.x, p.y + i.value),
    _ => throw new Exception()
}).Value;
var part2 = instructions.Aggregate((p: new Pos(0, 0), aim: 0), (t, i) => i.direction[0] switch
{
    'f' => (new(t.p.x + i.value, t.p.y + t.aim * i.value), t.aim),
    'u' => (t.p, t.aim - i.value),
    'd' => (t.p, t.aim + i.value),
    _ => throw new Exception()
}).p.Value;
Console.WriteLine((part1, part2));
readonly record struct Instruction(string direction, int value);
readonly record struct Pos(int x, int y)
{
    public long Value => x * y;
}