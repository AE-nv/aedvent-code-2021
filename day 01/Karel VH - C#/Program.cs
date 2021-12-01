string[] input = File.ReadAllLines("input.txt");

IEnumerable<int> increasedPartOne = Enumerable.Range(1, input.Length - 1)
                  .Where(i => Convert.ToInt32(input[i]) > Convert.ToInt32(input[i - 1]));

IEnumerable<int> increasedPartTwo = Enumerable.Range(3, input.Length - 3)
                  .Where(i => input.Skip(i - 2).Take(3).Select(x => Convert.ToInt32(x)).Sum()
                    > input.Skip(i - 3).Take(3).Select(x => Convert.ToInt32(x)).Sum());

Console.WriteLine("Part 1: " + increasedPartOne.Count());
Console.WriteLine("Part 2: " + increasedPartTwo.Count());