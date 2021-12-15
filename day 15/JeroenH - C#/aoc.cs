var input = File.ReadAllLines("input.txt");
var graph = (
    from row in input.Select((l, y) => (l, y))
    from col in row.l.Select((c, x) => (value: c - '0', x))
    select (node: new Node(col.x, row.y), col.value)
    ).ToImmutableDictionary(x => x.node, x => x.value);

var origin = new Node(0, 0);
var target1 = new Node(input[0].Length - 1, input.Length - 1);
var target2 = new Node((target1.x + 1) * 5 - 1, (target1.y + 1) * 5 - 1);

var part1 = Dijkstra(graph, origin, target1);
var part2 = Dijkstra(graph.Resize((x: target1.x + 1, y: target1.y + 1)), origin, target2);

Console.WriteLine((part1, part2));

// https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Using_a_priority_queue
int Dijkstra(ImmutableDictionary<Node, int> graph, Node source, Node target)
{
    var queue = new PriorityQueue<Node, int>();
    var costs = new Dictionary<Node, int>();
    costs[source] = 0;
    queue.Enqueue(source, 0);
    while (queue.Count > 0)
    {
        var current = queue.Dequeue(); // current lowest cost point
        var updates =
            from next in current.Neighbours(target.x, target.y)
            let cost = costs[current] + graph[next]
            where cost < costs.GetValueOrDefault(next, int.MaxValue)
            select (next, cost);
        foreach (var (v, cost) in updates)
        {
            costs[v] = cost;
            queue.Enqueue(v, cost);
        }
    }

    return costs[target];
}

record Node(int x, int y)
{
    public IEnumerable<Node> Neighbours(int maxX, int maxY)
    {
        if (x > 0)
            yield return this with { x = x - 1 };
        if (y > 0)
            yield return this with { y = y - 1 };
        if (x < maxX)
            yield return this with { x = x + 1 };
        if (y < maxY)
            yield return this with { y = y + 1 };
    }
}

static class Ex
{
    internal static ImmutableDictionary<Node, int> Resize(this ImmutableDictionary<Node, int> graph, (int x, int y) max) => (
        from y in Range(0, (graph.Keys.Select(k => k.y).Max() + 1) * 5)
        from x in Range(0, (graph.Keys.Select(k => k.x).Max() + 1) * 5)
        let value = (graph[new(x % max.x, y % max.y)] + (y / max.y + x / max.x) - 1) % 9 + 1
        select (node: new Node(x, y), value)).ToImmutableDictionary(x => x.node, x => x.value);
}