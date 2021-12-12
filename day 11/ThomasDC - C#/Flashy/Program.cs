using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static long Part1(this int[][] input)
    {
        var grid = new Grid(input);
        for (var step = 0; step < 100; step++)
        {
            grid.Iterate();
        }

        return grid.TotalFlashes;
    }

    public static long Part2(this int[][] input)
    {
        var grid = new Grid(input);
        for (var step = 0; step < 1000; step++)
        {
            var flashed =grid.Iterate();
            if (flashed == 100)
            {
                return step + 1;
            }
        }

        return -1;
    }

    private static readonly (int x, int y)[] NeighbourVectors = (
        from x in new[] { -1, 0, 1 }
        from y in new[] { -1, 0, 1 }
        where !(x == 0 && y == 0)
        select (x, y)).ToArray();

    public static (int x, int y)[] Neighbours(this (int x, int y) point) => NeighbourVectors
        .Select(_ => (x: point.x + _.x, y: point.y + _.y))
        .Where(_ => !(_.x < 0 || _.x >= 10 || _.y < 0 || _.y >= 10))
        .ToArray();
}

public class Grid
{
    private readonly IDictionary<(int x, int y), int> _octopuses;
    public long TotalFlashes { get; private set; }

    public Grid(int[][] input)
    {
        _octopuses = (
            from x in Enumerable.Range(0, 10)
            from y in Enumerable.Range(0, 10)
            select (x, y, level: input[y][x])).ToDictionary(_ => (_.x, _.y), _ => _.level);
    }

    public int Iterate()
    {
        foreach (var (key, value) in _octopuses)
        {
            _octopuses[key] = value + 1;
        }

        var totalFlashes = new List<(int x, int y)>();
        var newFlashes = new List<(int x, int y)>();
        do
        {
            newFlashes.Clear();
            newFlashes = _octopuses
                .Where(_ => _.Value > 9)
                .Where(_ => !totalFlashes.Contains(_.Key))
                .Select(_ => _.Key)
                .ToList();

            totalFlashes.AddRange(newFlashes);
            foreach (var n in newFlashes.SelectMany(x => x.Neighbours()))
            {
                _octopuses[n] += 1;
            }
        } while (newFlashes.Any());

        foreach (var n in totalFlashes)
        {
            _octopuses[n] = 0;
        }

        TotalFlashes += totalFlashes.Count;
        return totalFlashes.Count;
    }
}

public static class Utils
{
    public static int[][] ParseInput(this string fileName) => File.ReadAllLines(fileName)
        .Select(_ => _.Select(x => int.Parse(x.ToString())).ToArray()).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(1656);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(195);
}
