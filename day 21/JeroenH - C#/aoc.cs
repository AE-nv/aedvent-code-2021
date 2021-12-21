var part1 = RunPart1();
var part2 = RunPart2();
Console.WriteLine((part1, part2));

long RunPart1()
{
    var player1 = new Player(1, 0, 1);
    var player2 = new Player(2, 0, 5);
    var game = Play(new Game(player1, player2, 1, 0));
    var loser = game.Loser!.Value;
    return game.turn * loser.points;
}

long RunPart2()
{
    var player1 = new Player(1, 0, 1);
    var player2 = new Player(2, 0, 5);
    var result = Play2(player1, player2, 1, new());
    return Math.Max(result.p1wins, result.p2wins);
}

static Game Play(Game game)
{
    while (true)
    {
        game = game.Play();
        if (game.Winner.HasValue)
        {
            break;
        }
    }

    return game;
}

static (long p1wins, long p2wins) Play2(Player player1, Player player2, int playing, Dictionary<(Player, Player, int turn), (long w1, long w2)> cache)
{
    if (cache.ContainsKey((player1, player2, playing)))
        return cache[(player1, player2, playing)];
    if (player1.points >= 21)
        return (1, 0);
    else if (player2.points >= 21)
        return (0, 1);
    else
    {
        (long p1wins, long p2wins) = (0, 0);
        foreach (var score in
            from i in Range(1, 3) from j in Range(1, 3) from k in Range(1, 3) select i + j + k)
        {
            var wins = playing switch
            {
                1 => Play2(player1.Move(score), player2, 2, cache),
                _ => Play2(player1, player2.Move(score), 1, cache)
            };
            (p1wins, p2wins) = (p1wins + wins.p1wins, p2wins + wins.p2wins);
        }

        cache[(player1, player2, playing)] = (p1wins, p2wins);
        return (p1wins, p2wins);
    }
}

record struct Player(int n, int points, int position)
{
    public Player Move(int score)
    {
        var p = (position + score) % 10;
        if (p == 0) p = 10;
        return this with { position = p, points = points + p };
    }
}

record Game(Player player1, Player player2, int playing, int turn)
{
    public Game Play()
    {
        var t = turn + 1;
        var score = Range(t, 3).Select(t => t % 100).Sum();
        return playing switch
        {
            1 => this with { player1 = player1.Move(score), playing = 2, turn = t + 2 },
            _ => this with { player2 = player2.Move(score), playing = 1, turn = t + 2 },
        };
    }

    public Player? Winner => player1.points >= 1000 ? player1 : player2.points >= 1000 ? player2 : null;
    public Player? Loser => player1.points >= 1000 ? player2 : player2.points >= 1000 ? player1 : null;
}