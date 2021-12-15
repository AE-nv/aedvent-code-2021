using Shouldly;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static int Part1(this (string from, string to)[] input)
    {
        return Traverse(input, (node, currentPath) =>
        {
            if (!node.IsSmall) return true;
            return !currentPath.Contains(node);
        });
    }

    public static int Part2(this (string from, string to)[] input)
    {
        return Traverse(input, (node, currentPath) =>
        {
            if (node.IsStart) return false;
            if (!node.IsSmall) return true;
            if (!currentPath.Contains(node)) return true;
            return currentPath.Where(_ => _.IsSmall).GroupBy(_ => _.Name).All(_ => _.Count() == 1);
        });
    }

    private static int Traverse(this (string from, string to)[] input, Func<Node, Node[], bool> canVisit)
    {
        var nodes = input
            .Select(_ => _.from)
            .Union(input.Select(_ => _.to))
            .Distinct()
            .Select(_ => new Node(_))
            .ToDictionary(_ => _.Name, _ => _);
        foreach (var (from, to) in input)
        {
            nodes[from].Add(new Connection(nodes[from], nodes[to]));
        }

        var start = nodes.Values.Where(_ => _.IsStart).ToArray();
        return Step(start, canVisit).Count();
    }

    private static IEnumerable<Node[]> Step(Node[] currentPath, Func<Node, Node[], bool> canVisit)
    {
        var rear = currentPath[^1];
        if (rear.IsEnd)
        {
            yield return currentPath;
        }
        else
        {
            foreach (var next in rear.Neighbours.Where(_ => canVisit(_, currentPath)))
            {
                var newStack = new List<Node>(currentPath) { next }.ToArray();
                foreach (var path in Step(newStack, canVisit))
                {
                    yield return path;
                }
            }
        }
    }
}

public record Node(string Name)
{
    public bool IsStart => Name == "start";
    public bool IsEnd => Name == "end";
    public bool IsSmall => Name == Name.ToLowerInvariant();

    private readonly HashSet<Node> _neighbours = new();
    public IReadOnlySet<Node> Neighbours => _neighbours;

    public void Add(Connection connection)
    {
        if (connection.From == this)
        {
            _neighbours.Add(connection.To);
            connection.To._neighbours.Add(this);
        }
        else if (connection.To == this)
        {
            _neighbours.Add(connection.From);
            connection.From._neighbours.Add(this);
        }
    }
}

public record Connection(Node From, Node To);

public static class Utils
{
    public static (string from, string to)[] ParseInput(this string fileName) => File.ReadAllLines(fileName)
        .Select(_ => _.Split('-')).Select(_ => (_[0], _[1])).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example1() => "example1.txt".ParseInput().Part1().ShouldBe(10);
    
    [Fact]
    public void ValidatePart1Example2() => "example2.txt".ParseInput().Part1().ShouldBe(19);
    
    [Fact]
    public void ValidatePart1Example3() => "example3.txt".ParseInput().Part1().ShouldBe(226);

    [Fact]
    public void ValidatePart2Example1() => "example1.txt".ParseInput().Part2().ShouldBe(36);

    [Fact]
    public void ValidatePart2Example2() => "example2.txt".ParseInput().Part2().ShouldBe(103);

    [Fact]
    public void ValidatePart2Example3() => "example3.txt".ParseInput().Part2().ShouldBe(3509);
}
