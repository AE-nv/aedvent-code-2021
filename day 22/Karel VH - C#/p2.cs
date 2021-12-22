using System.Text.RegularExpressions;

long xMin = 0;
long yMin = 0;
long zMin = 0;
long xMax = 0;
long yMax = 0;
long zMax = 0;
List<Cuboid> cuboids = File.ReadAllLines("input.txt").Select(x =>
{
    var k = new Regex(@"(.*?)\s.*?=(.*?)\.\.(.*?),.*?=(.*?)\.\.(.*?),.*?=(.*?)\.\.(.*)").Match(x).Groups.Values.ToList();
    var action = k[1].Value.ToString();
    var nrs = k.Skip(2).Select(x => long.Parse(x.Value)).ToList();
    if (nrs[0] < xMin) xMin = nrs[0];
    if (nrs[1] < xMax) xMax = nrs[1];
    if (nrs[2] < yMin) yMin = nrs[2];
    if (nrs[3] < yMax) yMax = nrs[3];
    if (nrs[4] < zMin) zMin = nrs[4];
    if (nrs[5] < zMax) zMax = nrs[5];
    return new Cuboid() { On = action.Equals("on"), XStart = nrs[0], XEnd = nrs[1] + 1, YStart = nrs[2], YEnd = nrs[3] + 1, ZStart = nrs[4], ZEnd = nrs[5] + 1 };
}).ToList();

List<Cuboid> data = new();

//Had to check scrape reddit for this
foreach (Cuboid current in cuboids)
{
    List<Cuboid> newCuboids = new();
    foreach (Cuboid cmp in data)
    {
        Cuboid compare = new(cmp);

        if (current.XEnd > compare.XStart && current.XStart < compare.XEnd
            && current.YEnd > compare.YStart && current.YStart < compare.YEnd
            && current.ZEnd > compare.ZStart && current.ZStart < compare.ZEnd)
        {
            //Houston, we're dealing with an overlap

            if (compare.XStart < current.XStart)
            {
                Cuboid newC = new Cuboid()
                {
                    On = compare.On,
                    XStart = compare.XStart,
                    XEnd = current.XStart,
                    YStart = compare.YStart,
                    YEnd = compare.YEnd,
                    ZStart = compare.ZStart,
                    ZEnd = compare.ZEnd
                };
                compare.XStart = current.XStart;
                newCuboids.Add(newC);
            }
            if (compare.XEnd > current.XEnd)
            {
                Cuboid newC = new Cuboid()
                {
                    On = compare.On,
                    XStart = current.XEnd,
                    XEnd = compare.XEnd,
                    YStart = compare.YStart,
                    YEnd = compare.YEnd,
                    ZStart = compare.ZStart,
                    ZEnd = compare.ZEnd
                };
                compare.XEnd = current.XEnd;
                newCuboids.Add(newC);
            }
            if (compare.YStart < current.YStart)
            {
                Cuboid newC = new Cuboid()
                {
                    On = compare.On,
                    XStart = compare.XStart,
                    XEnd = compare.XEnd,
                    YStart = compare.YStart,
                    YEnd = current.YStart,
                    ZStart = compare.ZStart,
                    ZEnd = compare.ZEnd
                };
                compare.YStart = current.YStart;
                newCuboids.Add(newC);
            }
            if (compare.YEnd > current.YEnd)
            {
                Cuboid newC = new Cuboid()
                {
                    On = compare.On,
                    XStart = compare.XStart,
                    XEnd = compare.XEnd,
                    YStart = current.YEnd,
                    YEnd = compare.YEnd,
                    ZStart = compare.ZStart,
                    ZEnd = compare.ZEnd
                };
                compare.YEnd = current.YEnd;
                newCuboids.Add(newC);
            }
            if (compare.ZStart < current.ZStart)
            {
                Cuboid newC = new Cuboid()
                {
                    On = compare.On,
                    XStart = compare.XStart,
                    XEnd = compare.XEnd,
                    YStart = compare.YStart,
                    YEnd = compare.YEnd,
                    ZStart = compare.ZStart,
                    ZEnd = current.ZStart,
                };
                compare.ZStart = current.ZStart;
                newCuboids.Add(newC);
            }
            if (compare.ZEnd > current.ZEnd)
            {
                Cuboid newC = new Cuboid()
                {
                    On = compare.On,
                    XStart = compare.XStart,
                    XEnd = compare.XEnd,
                    YStart = compare.YStart,
                    YEnd = compare.YEnd,
                    ZStart = current.ZEnd,
                    ZEnd = compare.ZEnd
                };
                compare.ZEnd = current.ZEnd;
                newCuboids.Add(newC);
            }
        }
        else
        {
            newCuboids.Add(compare);
        }
    }
    newCuboids.Add(current);
    data = newCuboids;
}

long total = 0;
foreach (var c in data)
{
    if (c.On)
    {
        total += (c.XEnd - c.XStart) * (c.YEnd - c.YStart) * (c.ZEnd - c.ZStart);
    }
}
Console.WriteLine(total);

class Cuboid
{
    public Cuboid() { }
    public Cuboid(Cuboid cmp)
    {
        On = cmp.On;
        XStart = cmp.XStart;
        XEnd = cmp.XEnd;
        YStart = cmp.YStart;
        YEnd = cmp.YEnd;
        ZStart = cmp.ZStart;
        ZEnd = cmp.ZEnd;
    }

    public bool On { get; set; }
    public long XStart { get; set; }
    public long XEnd { get; set; }
    public long YStart { get; set; }
    public long YEnd { get; set; }
    public long ZStart { get; set; }
    public long ZEnd { get; set; }

    public override string ToString()
    {
        return XStart + ", " + XEnd + ", " + YStart + ", " + YEnd + ", " + ZStart + ", " + ZEnd;
    }
}
