using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    private static readonly List<char> Segments = new() { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
    private static readonly int[][] AllCombinations = Combinations(Enumerable.Range(0, 7).ToArray()).ToArray();

    public static int Part1(this (string[] input, string[] output)[] signals) =>
        signals.SelectMany(_ => _.output).Count(_ => _.Length is 2 or 4 or 3 or 7);

    public static int Part2(this (string[] input, string[] output)[] signals)
    {
        return signals.Sum(signal =>
        {
            var mapping = ExtractMapping(signal.input);
            return Decode(signal.output, mapping);
        });
    }

    private static int[] ExtractMapping(string[] input)
    {
        foreach (var mapping in AllCombinations)
        {
            if (IsValidMapping(input, mapping))
            {
                return mapping;
            }
        }

        throw new Exception("No valid mapping found :/");
    }

    public static bool IsValidMapping(string[] input, int[] mapping)
    {
        foreach (var x in input)
        {
            if (Decode(x, mapping) == -1)
            {
                return false;
            }
        }

        return true;
    }

    public static int Decode(string[] output, int[] mapping) =>
        output.Select((value, i) => (value, i)).Sum(x =>
            Decode(output[x.i], mapping) * (int)Math.Pow(10, output.Length - 1 - x.i));

    public static int Decode(string x, int[] mapping) =>
        Map(x.Select(_ => Segments[mapping[Segments.IndexOf(_)]]));
    
    private static int Map(IEnumerable<char> x) => new string(x.OrderBy(_ => _).ToArray()) switch
    {
        "abcefg" => 0,
        "cf" => 1,
        "acdeg" => 2,
        "acdfg" => 3,
        "bcdf" => 4,
        "abdfg" => 5,
        "abdefg" => 6,
        "acf" => 7,
        "abcdefg" => 8,
        "abcdfg" => 9,
        _ => -1
    };
    
    private static IEnumerable<int[]> Combinations(int[] input)
    {
        if (input.Length == 1)
        {
            yield return input;
        } 
        else
        {
            foreach (var i in input)
            {
                foreach (var combination in Combinations(input.Except(new []{i}).ToArray()))
                {
                    yield return new List<int> { i }.Union(combination).ToArray();
                }
            }
        }
    }
}

public static class Utils
{
    public static (string[], string[])[] ParseInput(this string fileName) => (
        from line in File.ReadAllLines(fileName)
        let parts = line.Split(" | ")
        select (parts[0].Split(' '), parts[1].Split(' '))).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(26);

    [Theory]
    [InlineData(new[] { "cdfeb", "fcadb", "cdfeb", "cdbaf" }, new[] { 2, 5, 6, 0, 1, 3, 4 }, 5353)]
    public void ValidateDecoding(string[] output, int[] mapping, int expectedDecodedValue) =>
        Solver.Decode(output, mapping).ShouldBe(expectedDecodedValue);

    [Theory]
    [InlineData(new[] { "acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab" }, new[] { 2, 5, 6, 0, 1, 3, 4 })]
    public void ValidateIsValidMapping(string[] input, int[] mapping) =>
        Solver.IsValidMapping(input, mapping).ShouldBe(true);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(61229);
}
