var input = File.ReadAllLines("input.txt");
Dictionary<string, Cave> caves = new();

foreach (string line in input)
{
    string cave1Name = line.Split("-")[0];
    string cave2Name = line.Split("-")[1];
    if (!caves.ContainsKey(cave1Name))
        caves.Add(cave1Name, new() { Name = cave1Name });
    if (!caves.ContainsKey(cave2Name))
        caves.Add(cave2Name, new() { Name = cave2Name });

    Cave cave1 = caves[cave1Name];
    Cave cave2 = caves[cave2Name];

    cave1.Neighbors.Add(cave2);
    cave2.Neighbors.Add(cave1);
}

List<List<Cave>> routes = new();
List<List<Cave>> routesLong = new();

foreach (Cave n in caves["start"].Neighbors)
{
    List<Cave> route = new();
    List<Cave> routeLong = new();
    route.Add(caves["start"]);
    route.Add(n);
    routeLong.Add(caves["start"]);
    routeLong.Add(n);
    findRoute(routes, new(route), 1);
    findRoute(routesLong, new(routeLong), 2);
}

void findRoute(List<List<Cave>> routes, List<Cave> route, int doubleVisits)
{
    Cave current = route.Last();
    Console.WriteLine(string.Join("->", route.Select(x => x.Name)));
    if (current.Neighbors.Any(x => x.Name.Equals("end")))
    {
        List<Cave> tmpRoute = new(route);
        tmpRoute.Add(caves["end"]);
        if (!routes.Any(r => r.SequenceEqual(tmpRoute)))
        {
            routes.Add(tmpRoute);
        }

    }
    foreach (Cave n in current.Neighbors)
    {
        if (!n.Name.Equals("start") && !n.Name.Equals("end"))
        {
            List<Cave> newRoute = new(route);
            newRoute.Add(n);
            int appears = route.Count(x => x.Name.Equals(n.Name));
            if (n.IsLarge || (!n.IsLarge && appears < doubleVisits))
            {
                if (!n.IsLarge && appears > 0)
                    findRoute(routes, newRoute, doubleVisits - 1);
                else
                    findRoute(routes, newRoute, doubleVisits);
            }
        }
    }
}

//This solution takes a mere 5 minutes to compute
Console.WriteLine((routes.Count, routesLong.Count));
class Cave
{
    public List<Cave> Neighbors { get; set; } = new();
    public bool IsLarge => Name.ToUpper().Equals(Name) || Name.Equals("start") || Name.Equals("end");
    public string Name { get; set; }
    public bool Visited { get; set; } = false;

    public bool Equals(Cave c)
    {
        return Name.Equals(c.Name);
    }
}