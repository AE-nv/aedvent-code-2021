var input = File.ReadAllLines("input.txt");
var numbers = input.Select(int.Parse).ToArray();
var part1 = numbers.Aggregate((prev: int.MaxValue, count: 0), (p, i) => (prev: i, count: p.count + (i > p.prev ? 1 : 0))).count;
var part2 = SeqModule.Windowed(3, numbers).Select(window => window.Sum()).Aggregate((prev: int.MaxValue, count: 0), (p, i) => (prev: i, count: p.count + (i > p.prev ? 1 : 0))).count;
Console.WriteLine((part1, part2));