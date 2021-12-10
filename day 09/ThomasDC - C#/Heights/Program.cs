using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    private static readonly (int x, int y)[] NeighbourVectors = { (0, -1), (0, 1), (-1, 0), (1, 0) };

    public static int Part1(this int[][] heights) => heights.Lows().Select(_ => heights[_.y][_.x]).Sum(_ => _ + 1);

    public static int Part2(this int[][] heights)
    {
        var height = heights.Length;
        var width = heights[0].Length;
        var basins = new List<(int x, int y)[]>();
        foreach (var (lowX, lowY) in heights.Lows())
        {
            var basin = new List<(int x, int y)> { (lowX, lowY) };
            var newAdditions = new List<(int x, int y)> { (lowX, lowY) };
            while (newAdditions.Any())
            {
                newAdditions.Clear();
                foreach (var point in basin)
                {
                    newAdditions.AddRange(point.Neighbours(width, height)
                        .Where(_ => !basin.Contains(_) && !newAdditions.Contains(_))
                        .Where(_ => heights[_.y][_.x] != 9));
                }

                basin.AddRange(newAdditions);
            }

            basins.Add(basin.ToArray());
        }

        return basins
            .OrderByDescending(_ => _.Length)
            .Take(3)
            .Select(_ => _.Length)
            .Aggregate(1, (result, size) => result * size);
    }

    private static IEnumerable<(int x, int y)> Lows(this int[][] heights)
    {
        var height = heights.Length;
        var width = heights[0].Length;
        for (var y = 0; y < height; y++)
        {
            for (var x = 0; x < width; x++)
            {
                var neighbours = (x, y).Neighbours(width, height).Select(_ => heights[_.y][_.x]).ToArray();
                if (!neighbours.Any(_ => _ <= heights[y][x]))
                {
                    yield return (x, y);
                }
            }
        }
    }

    private static (int x, int y)[] Neighbours(this (int x, int y) point, int width, int height) => NeighbourVectors
            .Select(_ => (x: point.x + _.x, y: point.y + _.y))
            .Where(_ => !(_.x < 0 || _.x >= width || _.y < 0 || _.y >= height))
            .ToArray();
}

public static class Utils
{
    public static int[][] ParseInput(this string fileName) => File.ReadAllLines(fileName)
        .Select(line => line.Select(_ => int.Parse(_.ToString())).ToArray()).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(15);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(1134);
}
