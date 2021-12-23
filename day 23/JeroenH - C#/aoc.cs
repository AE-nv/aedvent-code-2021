var input = File.ReadAllLines("input.txt");

var initial = GameState.Parse(input);

var part1 = Dijkstra(initial, GameState.Goal(2));
var part2 = Dijkstra(initial.Part2(), GameState.Goal(4));
Console.WriteLine((part1, part2));

int Dijkstra(GameState source, GameState target)
{
    var costs = new Dictionary<GameState, int>()
    { [source] = 0 };
    var visited = new HashSet<GameState>();
    var queue = new PriorityQueue<GameState, int>();
    queue.Enqueue(source, 0);
    while (queue.Count > 0)
    {
        var state = queue.Dequeue();
        if (!visited.Contains(state))
        {
            visited.Add(state);
            if (state == target)
                break;
            var updates =
                from move in state.PossibleMoves()
                let cost = costs[state] + move.Cost
                where cost < costs.GetValueOrDefault(move.NewState, int.MaxValue)
                select (move.NewState, cost);
            foreach (var (next, cost) in updates)
            {
                queue.Enqueue(next, cost);
                costs[next] = cost;
            }
        }
    }

    return costs[target];
}

record GameState(Hall Hall, Rooms Rooms)
{
    public override string ToString()
    {
        return $"Hall = {Hall}, Rooms = {Rooms.A.Value},{Rooms.B.Value},{Rooms.C.Value},{Rooms.B.Value}";
    }

    public static GameState Parse(string[] input)
    {
        var rooms = Rooms.Create(
            from line in input
            let entries = line.Where(char.IsLetter).Select((c, i) => (c, i))
            where entries.Count() == 4
            from entry in entries
            group entry.c by entry.i into g
            select string.Join("", g));
        return new GameState(Hall.Empty, rooms);
    }

    public static GameState Goal(int roomSize)
    {
        return new GameState(Hall.Empty, Rooms.Create(
            from a in Amphipods
            select new string(Repeat(a.Id, roomSize).ToArray())));
    }
    public GameState Part2() => this with { Rooms = Rooms.Create(new[] { Rooms.A.Value.Insert(1, "DD"), Rooms.B.Value.Insert(1, "CB"), Rooms.C.Value.Insert(1, "BA"), Rooms.D.Value.Insert(1, "AC"), }) };

    private static ImmutableArray<Amphipod> Amphipods = Range(0, 4).Select(x => new Amphipod((char)('A' + x), (int)Math.Pow(10, x), (x + 1) * 2)).ToImmutableArray();

    public IEnumerable<Move> PossibleMoves() => (
        from a in Amphipods
        from pos in Hall.EmptyPositionsAround(a.Position)
        where !Amphipods.Any(a => a.Position == pos)
        let move = TryMoveOut(a, pos)
        where move.HasValue
        select move.Value).Concat(
        from i in Range(0, Hall.Length)
        let move = TryMoveIn(i)
        where move.HasValue
        select move.Value);

    private Move? TryMoveOut(Amphipod a, int target)
    {
        var room = Rooms[a.Id];
        if (room.IsEmpty)
            return null;
        var depth = room.Depth;
        var amphipod = room[depth];
        var steps = Math.Abs(target - a.Position) + depth + 1;
        return new Move(this with { Hall = Hall.SetItem(target, amphipod), Rooms = Rooms.SetItem(a.Id, room.Clear(depth)) }, steps * Amphipods[amphipod - 'A'].Energy);
    }

    private Move? TryMoveIn(int fromhallway)
    {
        var id = Hall[fromhallway];
        if (id == '.')
            return null;
        var room = Rooms[id];
        var target = Amphipods[id - 'A'].Position;
        var start = target > fromhallway ? fromhallway + 1 : fromhallway - 1;
        var range = target < start ? target..start : start..target;
        if (!Hall.IsEmpty(range))
            return null;
        if (!room.CanEnter(id))
            return null;
        var depth = room.LastEmptyPosition;
        return new Move(this with { Hall = Hall.Clear(fromhallway), Rooms = Rooms.SetItem(id, room.SetItem(depth, id)) }, (range.End.Value - range.Start.Value + depth + 2) * Amphipods[id - 'A'].Energy);
    }
}

readonly record struct Amphipod(char Id, int Energy, int Position);

readonly record struct Rooms(Room A, Room B, Room C, Room D)
{
    public static Rooms Create(IEnumerable<string> values)
    {
        var v = values.Select((v, i) => new Room(v)).ToArray();
        return new Rooms(v[0], v[1], v[2], v[3]);
    }

    internal Rooms SetItem(char index, Room room) => index switch
    {
        'A' => this with { A = room },
        'B' => this with { B = room },
        'C' => this with { C = room },
        'D' => this with { D = room },
        _ => throw new NotImplementedException()
    };
    internal Room this[char index] => index switch
    {
        'A' => A,
        'B' => B,
        'C' => C,
        'D' => D,
        _ => throw new NotImplementedException()
    };
}

readonly record struct Hall(string Value)
{
    internal readonly static Hall Empty = new("...........");
    internal IEnumerable<int> EmptyPositionsAround(int position)
    {
        for (var i = position - 1; i >= 0 && Value[i] == '.'; --i)
            yield return i;
        for (var i = position + 1; i < Value.Length && Value[i] == '.'; ++i)
            yield return i;
    }

    internal char this[int i] => Value[i];
    internal int Length => Value.Length;
    internal Hall Clear(int index) => SetItem(index, '.');
    internal Hall SetItem(int target, char amphipod) => this with
    {
        Value = new StringBuilder(Value) { [target] = amphipod }.ToString()
    };
    internal bool IsEmpty(Range range) => Value[range].All(ch => ch == '.');
}

readonly record struct Room(string Value)
{
    internal bool IsEmpty => Value.All(c => c == '.');
    internal int LastEmptyPosition => Value.Select((c, i) => (c, i)).Last(c => c.c == '.').i;
    static char[] Values = new[] { 'A', 'B', 'C', 'D' };
    internal int Depth => Value.IndexOfAny(Values);
    internal char this[int index] => Value[index];
    internal Room Clear(int index) => SetItem(index, '.');
    internal Room SetItem(int index, char value) => this with
    {
        Value = new StringBuilder(Value) { [index] = value }.ToString()
    };
    internal bool CanEnter(char amphipod) => Value.All(ch => ch == '.' || ch == amphipod);
}

readonly record struct Move(GameState NewState, int Cost);