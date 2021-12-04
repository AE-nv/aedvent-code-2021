using day04;

var input = File.ReadAllLines("input.txt");

string[] gameCommands = input[0].Split(",");

List<Board> boards = Enumerable.Range(0, input.Length / 6).Select(x => new Board(input.Skip(2 + 6 * x).Take(5))).ToList();

int index = 0;
Dictionary<Board, int> winOrder = new();
while (index < gameCommands.Length && winOrder.Keys.Count < boards.Count)
{
    foreach (Board b in boards)
    {
        if (!b.IsComplete)
            b.Update(gameCommands[index]);
        else if (!winOrder.ContainsKey(b))
            winOrder.Add(b, index);
    }
    index++;
}
Board firstBoard = winOrder.MaxBy(x => x.Value).Key;
Board lastBoard = winOrder.MaxBy(x => x.Value).Key;
Console.WriteLine(firstBoard.NumbersLeft.Select(x => int.Parse(x)).Sum() * int.Parse(gameCommands[winOrder[firstBoard]]));
Console.WriteLine(lastBoard.NumbersLeft.Select(x => int.Parse(x)).Sum() * int.Parse(gameCommands[winOrder[lastBoard]]));