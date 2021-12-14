using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2();

public static class Solver
{
    public static int Part1(this ((int x, int y)[] points, Folding[] Foldings) input)
    {
        var grid = new Grid(input.points);
        grid.Fold(input.Foldings[0]);
        return grid.NumberOfVisibleDots;
    }

    public static void Part2(this ((int x, int y)[] points, Folding[] Foldings) input)
    {
        var grid = new Grid(input.points);
        foreach (var folding in input.Foldings)
        {
            grid.Fold(folding);
        }
        
        grid.Print();
    }
}

public record Folding(bool X, int Line);

public class Grid
{
    private readonly HashSet<(int x, int y)> _dots;
    public int NumberOfVisibleDots => _dots.Count;

    public Grid(IEnumerable<(int x, int y)> points)
    {
        _dots = new HashSet<(int x, int y)>(points);
    }

    public void Fold(Folding folding)
    {
        Func<(int x, int y), int> selector = folding.X ? _ => _.x : _ => _.y;
        Func<(int x, int y), int> inverseSelector = folding.X ? _ => _.y : _ => _.x;
        Func<(int i, int j), (int x, int y)> map = folding.X ? _ => (_.j, _.i) : _ => (_.i, _.j);
        var iOffset = Math.Min(0, _dots.Min(inverseSelector));
        var jOffset = Math.Min(0, _dots.Min(selector));
        var maxI = _dots.Max(inverseSelector);
        var maxJ = _dots.Max(selector);
        for (var i = iOffset; i <= maxI; i++)
        {
            var jStep = 1;
            for (var j = jOffset + folding.Line + 1; j <= maxJ; j++, jStep++)
            {
                var from = map((i, j));
                var to = map((i, jOffset + folding.Line - jStep));
                if (_dots.Contains(from))
                {
                    _dots.Remove(from);
                    if (!_dots.Contains(to))
                    {
                        _dots.Add(to);
                    }
                }
            }
        }
    }

    public void Print()
    {
        for (var y = _dots.Min(_ => _.y); y <= _dots.Max(_ => _.y); y++)
        {
            for (var x = _dots.Min(_ => _.x); x <= _dots.Max(_ => _.x); x++)
            {
                Console.Write(_dots.Contains((x, y)) ? "█" : " ");
            }

            Console.WriteLine();
        }
    }
}

public static class Utils
{
    public static ((int x, int y)[] points, Folding[] Foldings) ParseInput(this string fileName)
    {
        var lines = File.ReadAllLines(fileName);
        var points = lines.Where(_ => Regex.IsMatch(_, "^-{0,1}\\d+,-{0,1}\\d+")).Select(_ => _.Split(','))
            .Select(_ => (x: int.Parse(_[0]), y: int.Parse(_[1]))).ToArray();
        var foldings = lines.Where(_ => _.StartsWith("fold along")).Select(_ => _.Replace("fold along ", "").Split('='))
            .Select(_ => new Folding(_[0] == "x", int.Parse(_[1]))).ToArray();
        return (points, foldings);
    }

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(17);
}
