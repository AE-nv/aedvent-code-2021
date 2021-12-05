var input = File.ReadAllLines("input.txt");
var numbers = input[0].Split(",").Select(int.Parse).ToArray();
var part1 = ReadBoards().Play(numbers).First();
var part2 = ReadBoards().Play(numbers).Last();
Console.WriteLine((part1, part2));
IEnumerable<Board> ReadBoards() => input.Skip(2).GetBoards().ToArray();
class Board
{
    int[,] _numbers;
    public Board(int[,] numbers)
    {
        _numbers = numbers;
    }

    public bool Apply(int number)
    {
        foreach (var c in Coordinates().Where(c => this[c] == number))
            this[c] = -1;
        return Won;
    }

    public bool Won
    {
        get
        {
            for (var row = 0; row < 5; row++)
                if (Range(0, 5).All(col => this[(row, col)] == -1))
                    return true;
            for (var col = 0; col < 5; col++)
                if (Range(0, 5).All(row => this[(row, col)] == -1))
                    return true;
            return false;
        }
    }

    int this[(int row, int col) p] { get => _numbers[p.row, p.col]; set => _numbers[p.row, p.col] = value; }

    public int Sum() => Coordinates().Select(c => this[c]).Where(i => i != -1).Sum();
    static IEnumerable<(int row, int col)> Coordinates()
    {
        for (int row = 0; row < 5; row++)
            for (int col = 0; col < 5; col++)
                yield return (row, col);
    }
}

static class Extensions
{
    public static IEnumerable<int> Play(this IEnumerable<Board> boards, IEnumerable<int> numbers) =>
        from draw in numbers
        from board in boards
        where !board.Won // required for part 2
        let win = board.Apply(draw)
        where win
        select (draw * board.Sum());
    public static IEnumerable<Board> GetBoards(this IEnumerable<string> input) =>
        from chunk in input.Chunk(6) select CreateBoard(chunk.Take(5));
    static Board CreateBoard(IEnumerable<string> chunk)
    {
        var board = new int[5, 5];
        var row = 0;
        foreach (var line in chunk)
        {
            var span = line.AsSpan();
            for (var col = 0; col < 5; col++)
            {
                board[row, col] = int.Parse(span.Slice(col * 3, 2));
            }

            row++;
        }

        return new Board(board);
    }
}