using System.Text.RegularExpressions;

string input = "target area: x=244..303, y=-91..-54";
Regex regex = new Regex(@".*?x\s*=\s*(.*?)\.\.(.*?), y\s*=\s*(.*?)\.\.(.*)", RegexOptions.Compiled);
var k = regex.Matches(input).First();

int xMinTarget = int.Parse(regex.Matches(input).First().Groups[1].Value);
int xMaxTarget = int.Parse(regex.Matches(input).First().Groups[2].Value);
int yMinTarget = int.Parse(regex.Matches(input).First().Groups[3].Value);
int yMaxTarget = int.Parse(regex.Matches(input).First().Groups[4].Value);

List<(int, int, int)> highest = new();

for (int i = 0; i < xMaxTarget; i++)
{
    for (int j = yMinTarget * 2; j < 100; j++) //random 100
    {
        highest.Add(TakeJump(i, -j));
    }
}

Console.WriteLine(highest.MaxBy(x => x.Item3));
Console.WriteLine(highest.Where(x => x.Item3 != -int.MaxValue).Count());


(int, int, int) TakeJump(int x, int y)
{
    (int locX, int locY) = (0, 0);
    int highestY = y;

    while (x != 0 || y >= yMinTarget)
    {
        locX += x;
        locY += y;
        if (locY > highestY) highestY = locY;
        if (x != 0)
            x = x > 0 ? x - 1 : x + 1;
        y--;
        if (locX >= xMinTarget && locX <= xMaxTarget && locY >= yMinTarget && locY <= yMaxTarget)
            return (x, y, highestY);
    }
    return (x, y, -int.MaxValue);
}