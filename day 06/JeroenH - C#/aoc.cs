var input = File.ReadAllText("input.txt");
var numbers = input.Split(',').Select(int.Parse).ToImmutableArray();
var part1 = CountFish(80);
var part2 = CountFish(256);
Console.WriteLine((part1, part2));
long CountFish(int iterations)
{
    var noffish = new long[9];
    foreach (var n in numbers)
        noffish[n]++;
    for (int i = 0; i < iterations; i++)
    {
        var age0 = noffish[0];
        for (int age = 1; age < 9; age++)
        {
            noffish[age - 1] = noffish[age];
        }

        noffish[6] += age0;
        noffish[8] = age0;
    }

    return noffish.Sum();
}