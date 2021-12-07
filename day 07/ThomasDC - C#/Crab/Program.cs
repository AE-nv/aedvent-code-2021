using System;
using System.IO;
using System.Linq;
using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static int Part1(this int[] positions)
    {
        var median = positions.OrderBy(_ => _).ElementAt(positions.Length / 2);
        return positions.Sum(_ => Math.Abs(_ - median));
    }

    public static int Part2(this int[] crabs)
    {
        var min = crabs.Min();
        var max = crabs.Max();
        var totalFuelCost = new int[max - min];
        var position = min;
        for (var i = 0; i < totalFuelCost.Length; i++)
        {
            totalFuelCost[i] = crabs.Sum(crab =>
            {
                var n = Math.Abs(crab - position);
                return n * (n + 1) / 2;
            });

            position++;
        }

        return totalFuelCost.Min();
    }
}

public static class Utils
{
    public static int[] ParseInput(this string fileName) =>
        File.ReadAllLines(fileName).Single().Split(',').Select(int.Parse).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(37);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(168);
}
