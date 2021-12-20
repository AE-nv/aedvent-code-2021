using System.Diagnostics;
using System.Text;

Stopwatch s = Stopwatch.StartNew();
string programs = "abcdefghijklmnop";

string[] input = File.ReadAllLines("input.txt").First().Split(",");

List<string> solutions = new();

int nrOfRounds = 1000000000;
for (int i = 0; i < nrOfRounds; i++)
{
    input.Select(x =>
                {
                    switch (x[0])
                    {
                        case 's':
                            Spin(int.Parse(x[1..].ToString()));
                            break;
                        case 'x':
                            string[] split = x[1..].Split("/");
                            SwapInt(int.Parse(split[0].ToString()), int.Parse(split[1].ToString()));
                            break;
                        case 'p':
                            SwapChar(x[1], x[3]);
                            break;
                    }
                    return x;
                }).ToArray();

    solutions.Add(programs);
    if (programs.Equals("abcdefghijklmnop"))
    {
        i = 1000000000 - (1000000000 % (i + 1)) - 1;
        programs = solutions[(1000000000 % (i + 1)) - 1];
        break;
    }
}
void SwapChar(char v1, char v2)
{
    SwapInt(programs.IndexOf(v1), programs.IndexOf(v2));
}


void SwapInt(int v1, int v2)
{
    programs = new StringBuilder(programs) { [v1] = programs[v2], [v2] = programs[v1] }.ToString();
}

void Spin(int v)
{
    programs = programs.Substring(programs.Length - v) + programs.Substring(0, programs.Length - v);
}

Console.WriteLine(programs);
Console.WriteLine(s.ElapsedMilliseconds);