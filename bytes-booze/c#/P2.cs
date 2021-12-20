using System.Text;

string programs = "abcdefghijklmnop";

string[] input = File.ReadAllLines("input.txt").First().Split(",");


int nrOfRounds = 10;
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
    if (programs.Equals("abcdefghijklmnop"))
    {
        Console.WriteLine("Reset at " + i);
        i = 999999990 - 1;
        Console.WriteLine("Skip to " + i);
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
//eoagncdkihlmbpfj
//"ionlbkfeajgdmphc"
//ojhdkcinglabmefpo/dkcinglabmefp

//eoagncdkihlmbpfj