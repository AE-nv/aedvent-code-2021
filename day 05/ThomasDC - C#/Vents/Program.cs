using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static int Part1(this Line[] lines) => lines
        .Where(_ => _.IsHorizontal || _.IsVertical)
        .SelectMany(_ => _.Cells())
        .GroupBy(_ => _)
        .Where(_ => _.Count() > 1)
        .Select(_ => _.Key)
        .Distinct()
        .Count();

    public static int Part2(this Line[] lines) => lines
        .SelectMany(_ => _.Cells())
        .GroupBy(_ => _)
        .Where(_ => _.Count() > 1)
        .Select(_ => _.Key)
        .Distinct()
        .Count();
}

public record struct Cell(int X, int Y);

public record struct Line(Cell Start, Cell End)
{
    public bool IsHorizontal => Start.Y == End.Y;
    public bool IsVertical => Start.X == End.X;

    public IEnumerable<Cell> Cells()
    {
        if (IsVertical)
        {
            var from = Math.Min(Start.Y, End.Y);
            var to = Math.Max(Start.Y, End.Y);
            for (var i = from; i <= to; i++)
            {
                yield return new Cell(Start.X, i);
            }
        }
        else if (IsHorizontal)
        {
            var from = Math.Min(Start.X, End.X);
            var to = Math.Max(Start.X, End.X);
            for (var i = from; i <= to; i++)
            {
                yield return new Cell(i, Start.Y);
            }
        } 
        else
        {
            int minX = Math.Min(Start.X, End.X);
            var left = Start.X == minX ? Start : End;
            var right = left == Start ? End : Start;
            var yDelta = right.Y > left.Y ? 1 : -1;
            var y = left.Y;
            for (var x = left.X; x <= right.X; x++)
            {
                yield return new Cell(x, y);
                y += yDelta;
            }
        }
    }
}

public static class Utils
{
    public static Line[] ParseInput(this string fileName) =>
        (from line in File.ReadAllLines(fileName)
         let parts = line.Split(" -> ")
         let left = parts[0].Split(',')
         let start = new Cell(int.Parse(left[0]), int.Parse(left[1]))
         let right = parts[1].Split(',')
         let end = new Cell(int.Parse(right[0]), int.Parse(right[1]))
         select new Line(start, end)).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(5);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(12);
}
