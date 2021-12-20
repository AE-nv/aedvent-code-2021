using System.Text;

string programs = "abcdefghijklmnop";

var input = File.ReadAllLines("input.txt")
            .First().Split(",").Select(x =>
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

//pnegmkdjhbfiolca

//pnegmkdjhbfiolca