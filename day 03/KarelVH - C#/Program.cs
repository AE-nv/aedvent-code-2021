var input = File.ReadAllLines("input.txt").Select(x => x.ToCharArray().Select(y => (int)char.GetNumericValue(y)).ToList()).ToList();

List<int> bitFrequency = new(new int[input[0].Count()]);

input.ForEach(x =>
{
    for (int i = 0; i < x.Count; i++)
    {
        bitFrequency[i] += x[i];
    }
});

string gammaBin = bitFrequency.Aggregate("", (res, curr) => curr > (input.Count / 2) ? res += "1" : res += "0");
string epsilonBin = bitFrequency.Aggregate("", (res, curr) => curr > (input.Count / 2) ? res += "0" : res += "1");
double gamma = Convert.ToInt32(string.Join("", gammaBin), 2);
double epsilon = Convert.ToInt32(string.Join("", epsilonBin), 2);

Console.WriteLine(gamma * epsilon);

int j = 0;
while (input.Count > 1)
{
    var sum = input.Select(x => x.ElementAt(j)).Sum();
    input = input.Where(x => x.ElementAt(j) == (sum >= ((double)input.Count / 2) ? 1 : 0)).ToList();
    j++;
}
double r1 = Convert.ToInt32(string.Join("", input[0]), 2);

input = File.ReadAllLines("input.txt").Select(x => x.ToCharArray().Select(y => (int)char.GetNumericValue(y)).ToList()).ToList();
j = 0;
while (input.Count > 1)
{
    var sum = input.Select(x => x.ElementAt(j)).Sum();
    input = input.Where(x => x.ElementAt(j) == (sum >= ((double)input.Count / 2) ? 0 : 1)).ToList();
    j++;
}
double r2 = Convert.ToInt32(string.Join("", input[0]), 2);
Console.WriteLine(r1 * r2);