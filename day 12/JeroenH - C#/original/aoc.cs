var input = File.ReadAllLines("input.txt");

var edges = (
    from line in input
    let s = line.Split('-')
    from edge in new[] { (source: s[0], target: s[1]), (source: s[1], target: s[0]) }
    select edge).ToLookup(x => x.source, x => x.target);

const string START = "start";
const string END = "end";

var part1 = Count(ImmutableList<string>.Empty.Add(START), 1);
var part2 = Count(ImmutableList<string>.Empty.Add(START), 2);

Console.WriteLine((part1, part2));

int Count(ImmutableList<string> path, int mode) => path[^1] == END
? 1 
: edges[path[^1]].Aggregate(0, (total, n) => total + (mode, n) switch
{
    (2, not START) when n.All(char.IsLower) && path.Contains(n) => Count(path.Add(n), 1),
    _ when n.All(char.IsUpper) || !path.Contains(n) => Count(path.Add(n), mode),
    _ => 0
});