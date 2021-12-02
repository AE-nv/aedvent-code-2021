var input = File.ReadAllLines("input.txt")
                .Select(
                        x => (x.Split(" ")[0],
                        (x.Split(" ")[0].Equals("up") ? -Convert.ToInt32(x.Split(" ")[1]): Convert.ToInt32(x.Split(" ")[1])
            )));

var result = input.Aggregate((Forward: 0, Depth: 0), (acc, next) =>
{
    if (next.Item1.Equals("forward")) acc.Forward += next.Item2;
    else acc.Depth += next.Item2;
    return acc;
});

Console.WriteLine(result.Forward * result.Depth);

var result2 = input.Aggregate((Forward: 0, Depth: 0, Aim: 0), (acc, next) => {
    if (next.Item1.Equals("forward"))
    {
        acc.Forward += next.Item2;
        acc.Depth += next.Item2 * acc.Aim;
    }
    else acc.Aim += next.Item2;
    return acc;
});

Console.WriteLine(result2.Forward * result2.Depth);
