using System.Text.RegularExpressions;

int xMin = 0;
int yMin = 0;
int zMin = 0;
int xMax = 0;
int yMax = 0;
int zMax = 0;
List<Instruction> instructions = File.ReadAllLines("input.txt").Select(x =>
{
    var k = new Regex(@"(.*?)\s.*?=(.*?)\.\.(.*?),.*?=(.*?)\.\.(.*?),.*?=(.*?)\.\.(.*)").Match(x).Groups.Values.ToList();
    var action = k[1].Value.ToString();
    var nrs = k.Skip(2).Select(x => int.Parse(x.Value)).ToList();
    if (nrs[0] < xMin) xMin = nrs[0];
    if (nrs[1] < xMax) xMax = nrs[1];
    if (nrs[2] < yMin) yMin = nrs[2];
    if (nrs[3] < yMax) yMax = nrs[3];
    if (nrs[4] < zMin) zMin = nrs[4];
    if (nrs[5] < zMax) zMax = nrs[5];
    return new Instruction(action, nrs[0], nrs[1], nrs[2], nrs[3], nrs[4], nrs[5]);
}).ToList();
int cubeSize = 50;


HashSet<(int, int, int)> cubes = new();

int changed = 0;
foreach (var inst in instructions.Where(x => x.X1 > -cubeSize && x.X2 < cubeSize && x.Y1 > -cubeSize && x.Y2 < cubeSize && x.Z1 > -cubeSize && x.Z2 < cubeSize))
{
    // Console.WriteLine(inst.Action);
    for (int i = inst.X1; i <= inst.X2; i++)
    {
        for (int j = inst.Y1; j <= inst.Y2; j++)
        {
            for (int k = inst.Z1; k <= inst.Z2; k++)
            {
                if (inst.Action.Equals("on"))
                {
                    if (!cubes.Contains((i, j, k)))
                    {
                        //Console.WriteLine((i, j, k));
                        changed++;
                        cubes.Add((i, j, k));
                    }
                }
                if (inst.Action.Equals("off"))
                {
                    if (cubes.Contains((i, j, k)))
                    {
                        //Console.WriteLine((i, j, k));
                        changed++;
                        cubes.Remove((i, j, k));

                    }
                }
            }
        }
    }
    Console.WriteLine(changed);
    changed = 0;
}

Console.WriteLine(cubes.Count());


record Instruction(string Action, int X1, int X2, int Y1, int Y2, int Z1, int Z2);
