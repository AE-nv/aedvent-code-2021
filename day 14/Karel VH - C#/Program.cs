Dictionary<string, long> template = new();
foreach (var item in SeqModule.Windowed(2, File.ReadLines("input.txt").Take(1).First().ToString().ToCharArray()))
{
    string key = string.Join("", item);
    if (!template.ContainsKey(key))
        template.Add(key, 0L);
    template[key]++;
}

Dictionary<string, string> rules = File.ReadAllLines("input.txt").Skip(2).Select(x => x.Split(" -> ")).ToDictionary(x => x[0], y => y[1]);
calculate(10);
calculate(40);

void calculate(int count)
{

    Dictionary<string, long> newTemplate = new();
    for (long i = 0; i < count; i++)
    {
        foreach ((string key, string value) in rules)
        {
            if (template.ContainsKey(key))
            {
                long amount = template[key];
                template.Remove(key);
                string firstPair = key[0] + value;
                string secondPair = value + key[1];
                if (!newTemplate.ContainsKey(firstPair))
                    newTemplate.Add(firstPair, 0L);
                if (!newTemplate.ContainsKey(secondPair))
                    newTemplate.Add(secondPair, 0L);
                newTemplate[firstPair] += amount;
                newTemplate[secondPair] += amount;
            }
        }
        foreach ((string k, long v) in template)
        {
            if (!newTemplate.ContainsKey(k))
                newTemplate.Add(k, 0L);
            newTemplate[k] += v;
        }
        template = new(newTemplate);
        newTemplate = new();
    }

    Dictionary<char, double> counts = new();
    foreach ((string k, long v) in template)
    {
        if (!counts.ContainsKey(k[0]))
            counts.Add(k[0], 0L);
        if (!counts.ContainsKey(k[1]))
            counts.Add(k[1], 0L);
        counts[k[0]] += v;
        counts[k[1]] += v;
    }
    var max = counts.MaxBy(x => x.Value);
    var min = counts.MinBy(x => x.Value);
    Console.WriteLine(Math.Ceiling(max.Value / 2) - Math.Ceiling(min.Value / 2));
}