var input = File.ReadAllText("input.txt");
var numbers = input.Split(',').Select(int.Parse).ToImmutableArray();
var part1 = (
    from i in Range(0, numbers.Max())
    let cost = (
from j in numbers
select Cost1(i, j)).Sum()
    select cost).Min();
var part2 = (
    from i in Range(0, numbers.Max())
    let cost = (
from j in numbers
select Cost2(i, j)).Sum()
    select cost).Min();
Console.WriteLine((part1, part2));
int Cost1(int x, int y) => Math.Abs(y - x);
int Cost2(int x, int y)
{
    var distance = Cost1(x, y);
    return distance * (distance + 1) / 2;
}