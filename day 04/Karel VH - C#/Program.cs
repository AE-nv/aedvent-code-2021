var input = File.ReadAllLines("input.txt");

string[] gameCommands = input[0].Split(",");

List<Board> boards = Enumerable.Range(0, input.Length / 6).Select(x => new Board(input.Skip(2 + 6 * x).Take(5).Select(x => x.Trim().Replace("  ", " ")))).ToList();

int index = -1;
Dictionary<Board, int> winOrder = new();
while (++index < gameCommands.Length && winOrder.Keys.Count < boards.Count)
{
    boards.ForEach(b =>
    {
        if (!b.IsComplete)
            b.Update(gameCommands[index]);
        else if (!winOrder.ContainsKey(b))
            winOrder.Add(b, index);
    });
}
Board firstBoard = winOrder.MinBy(x => x.Value).Key;
Board lastBoard = winOrder.MaxBy(x => x.Value).Key;
Console.WriteLine((firstBoard.NumbersLeftSum * int.Parse(gameCommands[winOrder[firstBoard]]), lastBoard.NumbersLeftSum * int.Parse(gameCommands[winOrder[lastBoard]])));