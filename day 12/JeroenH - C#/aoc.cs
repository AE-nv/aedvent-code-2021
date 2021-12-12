var input = File.ReadAllLines("input.txt");

var edges = (
    from line in input
    let s = line.Split('-')
    from edge in new[] { (source: s[0], target: s[1]), (source: s[1], target: s[0]) }
    select edge).ToLookup(x => x.source, x => x.target);

var part1 = Count(ImmutableList<string>.Empty.Add("start"));
var part2 = Count2(ImmutableList<string>.Empty.Add("start"));

Console.WriteLine((part1, part2));

int Count(ImmutableList<string> path) => path[^1] == "end" ? 1 : (
    from n in edges[path[^1]]
    where n.All(char.IsUpper) || !path.Contains(n)
    select Count(path.Add(n))).Sum();

int Count2(ImmutableList<string> path) => path[^1] == "end" 
? 1 
: edges[path[^1]].Aggregate(0, (total, n) => total + (largeOrFirst: n.All(char.IsUpper) || !path.Contains(n), 
                                                      secondVisitToSmall: n != "start" && n.All(char.IsLower) && path.Contains(n)
                                                      ) switch
{
    { largeOrFirst: true } => Count2(path.Add(n)),
    { secondVisitToSmall: true } => Count(path.Add(n)),
    _ => 0
});