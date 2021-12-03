using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static int Part1(this bool[][] bits)
    {
        var gamma = bits.FindCommonVector(false);
        var epsilon = bits.FindCommonVector(true);
        return gamma.ToDecimal() * epsilon.ToDecimal();
    }

    public static int Part2(this bool[][] bits)
    {
        var oygenGeneratorRating = bits.ExtractRating(false);
        var co2ScrubberRating = bits.ExtractRating(true);
        return oygenGeneratorRating.ToDecimal() * co2ScrubberRating.ToDecimal();
    }

    private static bool[] ExtractRating(this bool[][] bits, bool inverse)
    {
        var common = bits.FindCommonVector(inverse);
        var remaining = bits;
        for (var x = 0; x < bits[0].Length; x++)
        {
            remaining = remaining.Where(_ => _[x] == common[x]).ToArray();
            if (remaining.Length == 1)
            {
                return remaining.Single();
            }

            common = remaining.FindCommonVector(inverse);
        }

        return Array.Empty<bool>();
    }

    private static bool[] FindCommonVector(this bool[][] bits, bool inverse)
    {
        var common = new List<bool>();
        for (int x = 0; x < bits[0].Length; x++)
        {
            var grouped = bits.Select(_ => _[x]).GroupBy(_ => _).ToDictionary(_ => _.Key, _ => _.Count());
            var trues = grouped.GetValueOrDefault(true, 0);
            var falses = grouped.GetValueOrDefault(false, 0);
            common.Add(trues >= falses);
        }

        return (inverse ? common.Select(_ => !_) : common).ToArray();
    }

    private static int ToDecimal(this bool[] bits) => bits.Aggregate(0, (result, bit) => (result << 1) + (bit ? 1 : 0));
}

public static class Utils
{
    public static bool[][] ParseInput(this string fileName) =>
        File.ReadAllLines(fileName).Select(_ => _.Select(_ => _ == '1').ToArray()).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(198);

    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(230);
}
