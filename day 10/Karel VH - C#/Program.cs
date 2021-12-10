var input = File.ReadAllLines("input.txt");

List<Stack<long>> openedCharactersList = new();
string openers = "([{<";
string closers = ")]}>";
Dictionary<char, long> closerPoints = new() { { ')', 3 }, { ']', 57 }, { '}', 1197 }, { '>', 25137 } };
List<char> errorsList = new();

foreach (string line in input)
{
    char error = '.';
    Stack<long> openedCharacters = new();
    foreach (char character in line)
    {
        long openIndex = openers.IndexOf(character);
        if (openIndex != -1)
        {
            openedCharacters.Push(openIndex);
        }
        else
        {
            long closeIndex = closers.IndexOf(character);
            if ((openedCharacters.Count == 0 || openedCharacters.Pop() != closeIndex))
            {
                error = (character);
                openedCharacters.Clear();
                break;
            }
        }
    }
    if (openedCharacters.Count != 0)
        openedCharactersList.Add(openedCharacters);
    if (error != '.')
        errorsList.Add(error);

}

Console.WriteLine(errorsList.Select(x => closerPoints[x]).Sum());
Console.WriteLine(openedCharactersList.Select(x => x.Aggregate(0L, (acc, curr) => acc = (acc * 5) + (1 + curr))).OrderBy(x => x).ToList()[openedCharactersList.Count() / 2]);