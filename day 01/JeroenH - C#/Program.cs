var numbers = File.ReadLines("input.txt").Select(int.Parse).ToArray();

var part1 = numbers
        .Aggregate(
            (prev: int.MaxValue, count: 0), 
            (pair, next) => (prev: next, count: pair.count + (next > pair.prev ? 1 : 0))
            ).count;

var part2 = (
    from window in SeqModule.Windowed(3, numbers)
    select window.Sum()
    ).Aggregate(
            (prev: int.MaxValue, count: 0), 
            (pair, next) => (prev: next, count: pair.count + (next > pair.prev ? 1 : 0))
            ).count;

Console.WriteLine((part1, part2));
