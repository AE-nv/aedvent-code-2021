using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static long Part1(this (string template, Rule[] rules) input) => Expand(input, 10);
    public static long Part2(this (string template, Rule[] rules) input) => Expand(input, 40);

    private static long Expand((string template, Rule[] rules) input, int numberOfSteps)
    {
        var buckets = new Dictionary<string, long>();
        for (var i = 0; i < input.template.Length - 1; i++)
        {
            var key = input.template[i..(i + 2)];
            buckets.Increment(key, 1);
        }

        for (var step = 0; step < numberOfSteps; step++)
        {
            var newBuckets = new Dictionary<string, long>();
            foreach (var bucket in buckets)
            {
                foreach (var rule in input.rules.Where(_ => bucket.Key == _.From))
                {
                    foreach (var to in new[] { $"{rule.From[0]}{rule.To}", $"{rule.To}{rule.From[1]}" })
                    {
                        newBuckets.Increment(to, bucket.Value);
                    }
                }
            }

            buckets = newBuckets;
        }

        var counts = (
                from bucket in buckets
                from letter in bucket.Key
                select (letter, bucket.Value))
            .GroupBy(_ => _.letter)
            .Select(_ => (_.Key, count:
                _.Key == input.template[0] || _.Key == input.template[^1]
                ? (_.Sum(x => x.Value) - 1) / 2 + 1
                : _.Sum(x => x.Value) / 2))
            .OrderByDescending(_ => _.count)
            .ToArray();

        return counts[0].count - counts[^1].count;
    }

    private static void Increment(this Dictionary<string, long> buckets, string key, long value)
    {
        if (buckets.ContainsKey(key))
        {
            buckets[key] += value;
        }
        else
        {
            buckets.Add(key, value);
        }
    }
}

public static class Utils
{
    public static (string template, Rule[] rules) ParseInput(this string fileName)
    {
        var lines = File.ReadAllLines(fileName);
        return (lines[0], lines.Skip(2).Select(_ =>
        {
            var parts = _.Split(" -> ");
            return new Rule(parts[0], parts[1]);
        }).ToArray());
    }

    public static void Print(this object o) => Console.WriteLine(o);
}

public record Rule(string From, string To);

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(1588);
    
    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(2188189693529);
}
