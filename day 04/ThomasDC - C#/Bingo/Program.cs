using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static int Part1(this (int[] draws, Board[] boards) game)
    {
        foreach (var draw in game.draws)
        {
            foreach (var board in game.boards)
            {
                board.Cross(draw);
                if (board.Bingo())
                {
                    return board.UnmarkedSum * draw;
                }
            }
        }

        return -1;
    }

    public static int Part2(this (int[] draws, Board[] boards) game)
    {
        var bingoBoards = new List<Board>();
        foreach (var draw in game.draws)
        {
            foreach (var board in game.boards.Except(bingoBoards))
            {
                board.Cross(draw);
                if (board.Bingo())
                {
                    if (game.boards.Except(bingoBoards).Count() == 1)
                    {
                        return board.UnmarkedSum * draw;
                    }

                    bingoBoards.Add(board);
                }
            }
        }

        return -1;
    }
}

public record struct Board(Cell[][] Cells)
{
    public const int Dimension = 5;

    public int UnmarkedSum => Cells.SelectMany(_ => _).Where(_ => !_.Crossed).Sum(_ => _.Number);

    public void Cross(int draw)
    {
        for (var y = 0; y < Dimension; y++)
        {
            for (var x = 0; x < Dimension; x++)
            {
                if (Cells[y][x].Number == draw)
                {
                    Cells[y][x].Crossed = true;
                }
            }
        }
    }

    public bool Bingo()
    {
        if (Cells.Any(row => row.All(_ => _.Crossed)))
        {
            return true;
        }

        if (Columns().Any(column => column.All(_ => _.Crossed)))
        {
            return true;
        }

        return false;
    }

    public Cell[][] Columns()
    {
        var columns = new Cell[Dimension][];
        for (var i = 0; i < Dimension; i++)
        {
            columns[i] = new Cell[Dimension];
            for (var j = 0; j < Dimension; j++)
            {
                columns[i][j] = Cells[j][i];
            }   
        }

        return columns;
    }
}

public record struct Cell(int Number, bool Crossed);

public static class Utils
{
    public static (int[] draws, Board[] boards) ParseInput(this string fileName)
    {
        var lines = File.ReadAllLines(fileName);
        var draws = lines[0].Split(',').Select(int.Parse).ToArray();
        var remainingLines = lines.Skip(2).ToArray();
        var boards = new List<Board>();
        for (var boardIndex = 0; boardIndex <= remainingLines.Length / (Board.Dimension + 1); boardIndex++)
        {
            var cells = new Cell[Board.Dimension][];
            for (var y = 0; y < Board.Dimension; y++)
            {
                cells[y] = new Cell[Board.Dimension];
                for (var x = 0; x < Board.Dimension; x++)
                {
                    var number = remainingLines[boardIndex * (Board.Dimension + 1) + y].Skip(x * 3).Take(2).ToArray();
                    cells[y][x] = new Cell(int.Parse(number), false);
                }
            }

            boards.Add(new Board(cells));
        }

        return (draws, boards.ToArray());
    }

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(4512);
    
    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(1924);
}
