Dictionary<string, Cave> caves = new();

File.ReadLines("input.txt").Select(x => x.Split("-")).Select(x => (c1: new Cave(x[0], char.IsUpper(x[0][0]), new()), c2: new Cave(x[1], char.IsUpper(x[1][0]), new())))
    .ToList().ForEach(x =>
    {
        caves.TryAdd(x.c1.Name, x.c1);
        caves.TryAdd(x.c2.Name, x.c2);
        caves[x.c1.Name].Neighbors.Add(caves[x.c2.Name]);
        caves[x.c2.Name].Neighbors.Add(caves[x.c1.Name]);
    });

List<List<Cave>> findRoute(List<List<Cave>> routes, List<Cave> route, int visits)
{
    if (route.Last().Neighbors.Any(x => x.Name.Equals("end")) && !routes.Any(r => r.SequenceEqual(route.Append(caves["end"]))))
        routes.Add(route.Append(caves["end"]).ToList());

    route.Last().Neighbors.Where(n => !n.Name.Equals("start") && !n.Name.Equals("end")).ToList().ForEach(n =>
    {
        int appears = route.Count(x => x.Name.Equals(n.Name));
        if (n.IsLarge || (!n.IsLarge && appears < visits))
            findRoute(routes, new(route.Append(n)), visits - (!n.IsLarge && appears > 0 ? 1 : 0));
    });
    return routes;
}

//This solution only takes a mere 5 minutes to compute
Console.WriteLine((findRoute(new(), new() { caves["start"] }, 1).Count, findRoute(new(), new() { caves["start"] }, 2).Count));

record Cave(string Name, bool IsLarge, List<Cave> Neighbors);