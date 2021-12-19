using Shouldly;
using Xunit;

"input.txt".ParseInput().Part2().Print();

public static class Solver
{
    public static long Part2(this int[][] risks)
    {
        return Part1(Expand(risks));
    }

    public static long Part1(this int[][] risks)
    {
        var height = risks.Length;
        var width = risks[0].Length;

        (int x, int y)[] neighbourVectors = new[] { (0, -1), (0, 1), (-1, 0), (1, 0) };
        IEnumerable<(int x, int y)> Neighbours((int x, int y) point) => neighbourVectors
            .Select(_ => (x: point.x + _.x, y: point.y + _.y))
            .Where(_ => !(_.x < 0 || _.x >= width || _.y < 0 || _.y >= height));

        var initial = (0, 0);
        var destination = (height - 1, width - 1);
        var costs = new Dictionary<(int x, int y), int>
        {
            [initial] = 0
        };

        var via = new Dictionary<(int x, int y), (int x, int y)>();
        var visited = new HashSet<(int x, int y)>();
        var priorityQ = new PriorityQueue<(int x, int y), int>();
        priorityQ.Enqueue(initial, costs[initial]);

        while (priorityQ.TryDequeue(out var current, out var _))
        {
            visited.Add(current);
            foreach (var neighbour in Neighbours(current))
            {
                var cost = costs[current] + risks[neighbour.y][neighbour.x];
                if (cost < costs.GetValueOrDefault(neighbour, int.MaxValue))
                {
                    costs[neighbour] = cost;
                    via[neighbour] = current;
                    if (!visited.Contains(neighbour))
                    {
                        priorityQ.Enqueue(neighbour, cost);
                    }
                }
            }
        }

        return costs[destination];
    }

    private static int[][] Expand(int[][] risks)
    {
        const int multiplicator = 5;
        var height = risks.Length;
        var newHeight = height * multiplicator;
        var width = risks[0].Length;
        var newWidth = width * multiplicator;
        var expanded = new int[newHeight][];
        for (var i = 0; i < newHeight; i++)
        {
            expanded[i] = new int[newWidth];
        }

        for (var x = 0; x < width; x++)
        {
            for (var y = 0; y < height; y++)
            {
                for (var mY = 0; mY < multiplicator; mY++)
                {
                    for (var mX = 0; mX < multiplicator; mX++)
                    {
                        expanded[mY * height + y][mX * width + x] = ((risks[y][x] - 1 + mX + mY) % 9) + 1;
                    }
                }
            }
        }

        return expanded;
    }
}

public static class Utils
{
    public static int[][] ParseInput(this string fileName) => 
        File.ReadAllLines(fileName).Select(_ => _.Select(x => int.Parse(x.ToString())).ToArray()).ToArray();

    public static void Print(this object o) => Console.WriteLine(o);
}

public class Tests
{
    [Fact]
    public void ValidatePart1Example() => "example.txt".ParseInput().Part1().ShouldBe(40);
    
    [Fact]
    public void ValidatePart2Example() => "example.txt".ParseInput().Part2().ShouldBe(315);
}
