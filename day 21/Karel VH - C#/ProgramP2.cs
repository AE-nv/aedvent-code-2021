Dictionary<State, List<long>> solutions = new();
Dictionary<int, int> gamesPerSum = new() { { 3, 1 }, { 4, 3 }, { 5, 6 }, { 6, 7 }, { 7, 6 }, { 8, 3 }, { 9, 1 } };
(long k1, long k2) = PlayGame(new State(1, 1, 6, 0, 0));
Console.WriteLine("wins: " + ((k1 > k2) ? k1 : k2));

(long, long) PlayGame(State cs)
{
    if (solutions.ContainsKey(cs)) return (solutions[cs][0], solutions[cs][1]);
    if (cs.S1 >= 21) return (1, 0);
    if (cs.S2 >= 21) return (0, 1);

    (long wins1, long wins2) = (0, 0);
    foreach ((int sum, int count) in gamesPerSum)
    {
        State newState = WinnerWinnerChickenDinner(cs, sum);
        (long w1, long w2) = PlayGame(newState);
        wins1 += w1 * count;
        wins2 += w2 * count;
    }
    if (!solutions.ContainsKey(cs))
        solutions[cs] = new() { wins1, wins2 };
    return (wins1, wins2);
}


State WinnerWinnerChickenDinner(State s, int sum)
{
    if (s.Turn == 1)
    {
        int newPos1 = (s.P1 + sum) % 10;
        if (newPos1 == 0) newPos1 = 10;
        return s with { Turn = 2, P1 = newPos1, S1 = s.S1 + newPos1 };
    }
    int newPos2 = (s.P2 + sum) % 10;
    if (newPos2 == 0) newPos2 = 10;
    return s with { Turn = 1, P2 = newPos2, S2 = s.S2 + newPos2 };
}
record State(int Turn, int P1, int P2, int S1, int S2);