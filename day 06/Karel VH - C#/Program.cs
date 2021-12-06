var input = File.ReadAllLines("input.txt")[0].Split(",").Select(x => int.Parse(x) + 1).ToList();
List<int> ages = Enumerable.Range(1, 9).ToList();
Console.WriteLine((calc(80), calc(256)));
double calc(int n)
{
    double[] fish = new double[10];
    input.ForEach(x => fish[x]++);
    for (int i = 0; i < n; i++)
    {
        ages.ForEach(age => fish[age - 1] = fish[age]);
        fish[7] += fish[0];
        fish[9] = fish[0];
    }
    return fish.Skip(1).Sum();
}
