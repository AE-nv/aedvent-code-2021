using Shouldly;
using Xunit;

"input.txt".ParseInput<int>().Part2().Print();

public static class Solver
{
    public static int Part1(this IReadOnlyCollection<int> depths) => depths
        .Skip(1)
        .Select((depth, i) => depth > depths.ElementAt(i))
        .Count(increased => increased);

    public static int Part2(this IReadOnlyCollection<int> depths) => Enumerable.Range(0, depths.Count)
        .Take(depths.Count - 2)
        .Select(i => depths.ElementAt(i) + depths.ElementAt(i + 1) + depths.ElementAt(i + 2))
        .ToArray()
        .Part1();
}

public static class Utils
{
    public static IReadOnlyCollection<T> ParseInput<T>(this string fileName) =>
        File.ReadAllLines(fileName).Select(_ => (T)Convert.ChangeType(_, typeof(T))).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput<int>().Part1().ShouldBe(7);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput<int>().Part2().ShouldBe(5);
}