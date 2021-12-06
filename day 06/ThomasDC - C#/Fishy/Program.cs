using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static long Part1(this int[] timers) => timers.Iterate().Skip(79).Take(1).Single().Sum(_ => _.Value);
    public static long Part2(this int[] timers) => timers.Iterate().Skip(255).Take(1).Single().Sum(_ => _.Value);

    private static IEnumerable<IDictionary<int, long>> Iterate(this int[] timers)
    {
        var groups = timers
            .GroupBy(_ => _)
            .Select(_ => new { timer = _.Key, numberOfFish = (long) _.Count() })
            .Union(Enumerable.Range(0, 9).Select(_ => new { timer = _, numberOfFish = 0L }))
            .GroupBy(_ => _.timer)
            .ToDictionary(_ => _.Key, _ => _.Select(x => x.numberOfFish).Sum());
        for (var iteration = 1;; iteration++)
        {
            var numberOfHatches = groups[0];
            for (var i = 0; i < 8; i++)
            {
                groups[i] = groups[i + 1];
            }

            groups[6] += numberOfHatches;
            groups[8] = numberOfHatches;

            yield return groups;
        }
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
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(5934);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(26984457539);
}
