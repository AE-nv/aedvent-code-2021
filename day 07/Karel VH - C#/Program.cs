var input = File.ReadAllLines("input.txt")[0].Split(",").Select(int.Parse).ToList();

Console.WriteLine(
    Enumerable.Range(0, input.Count)
    .Select(i =>
        input.Aggregate(0L, (acc, curr) => acc += Math.Abs(i - curr)))
    .Min());

Console.WriteLine(
    Enumerable.Range(0, input.Count)
    .Select(i =>
        input.Aggregate(0L, (acc, curr) => acc += Enumerable.Range(1, Math.Abs(i - curr))
        .Sum()))
    .Min());