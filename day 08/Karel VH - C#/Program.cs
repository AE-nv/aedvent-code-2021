List<List<Signal>> input = File.ReadAllLines("input.txt").Select(x => x.Split("|")[0].Trim().Split(" ").Select(y => new Signal(y)).ToList()).ToList();
List<List<Signal>> input2 = File.ReadAllLines("input.txt").Select(x => x.Split("|")[1].Trim().Split(" ").Select(y => new Signal(y)).ToList()).ToList();
var signal1478 = input2.SelectMany(r => r).Where(x => x.Number != -1);
Console.WriteLine(signal1478.Count());

int sum = 0;
for (int i = 0; i < input.Count; i++)
{
    SignalsDecoder d = new(input[i]);
    var p = input2[i].Select(x => d.Decode(x));
    Console.WriteLine(string.Join("", p));
    sum += int.Parse(string.Join("", p));
}

Console.WriteLine(sum);
