var inputText = File.ReadAllLines("input.txt");
var input = inputText.Select(x => CreateNumber(x)).ToList();

Number P1 = input.First();
//PART 1
foreach (Number n in input.Skip(1))
{
    Number root = new(null, 0) { N1 = P1, N2 = n };
    Reduce(root);
    P1 = root.IncreaseDepth();
}
Console.WriteLine(P1.Magnitude);

//PART 2
long P2 = 0;
for (int i = 0; i < input.Count; i++)
{
    for (int j = 0; j < input.Count; j++)
    {
        if (i == j) continue;
        Number root = new(null, 0);
        root.N1 = CreateNumber(inputText[i]);
        root.N2 = CreateNumber(inputText[j]);
        Reduce(root);
        P2 = root.Magnitude > P2 ? root.Magnitude : P2;
    }
}
Console.WriteLine(P2);

void Reduce(Number root)
{
    //First explode, then keep exploding until we can no longer explode, then split
    //Then do it all again
    Number? depth4 = getNumberToExplode(root);
    Number? toBeSplit = null;

    while (depth4 != null || toBeSplit != null)
    {
        while (depth4 != null)
        {
            Explode(root, depth4);
            depth4 = getNumberToExplode(root);
        }
        toBeSplit = getNumberToSplit(root);
        Split(root, toBeSplit);

        depth4 = getNumberToExplode(root);
        toBeSplit = getNumberToSplit(root);
    }
}

void Explode(Number root, Number depth4)
{
    int index = root.ToList().IndexOf(depth4);
    Number? leftNeighbor = index > 0 ? root.ToList()[index - 1] : null;
    if (leftNeighbor != null)
    {
        leftNeighbor = findNeighborWithValue(root, leftNeighbor, index, -1);
        leftNeighbor.Value += depth4.N1.Value;
    }

    Number? rightNeighbor = index < root.ToList().Count - 3 ? root.ToList()[index + 3] : null;
    if (rightNeighbor != null)
    {
        rightNeighbor = findNeighborWithValue(root, rightNeighbor, index + 2, 1);
        rightNeighbor.Value += depth4.N2.Value;
    }
    depth4.Recreate();
}

void Split(Number root, Number? toBeSplit)
{
    if (toBeSplit == null) return;

    //Console.WriteLine("Split : " + toBeSplit.ToString());
    toBeSplit.N1 = new Number(toBeSplit, toBeSplit.Depth + 1) { Value = (int)Math.Floor((double)toBeSplit.Value / 2.0) };
    toBeSplit.N2 = new Number(toBeSplit, toBeSplit.Depth + 1) { Value = (int)Math.Ceiling((double)toBeSplit.Value / 2.0) };
    toBeSplit.Value = null;
    int sIndex = root.ToList().IndexOf(toBeSplit);
    //Console.WriteLine("After split: " + root.ToString());

}

Number? getNumberToSplit(Number root)
    => root.ToList().Where(x => x.Value != null && x.Value > 9).DefaultIfEmpty(null).First();

Number? getNumberToExplode(Number root)
    => root.ToList().Where(x => x.Depth >= 4 && x.Value == null && x.N1.Value != null && x.N2.Value != null).DefaultIfEmpty(null).First();


Number findNeighborWithValue(Number root, Number leftNeighbor, int index, int step)
{
    while (leftNeighbor != null && leftNeighbor.Value == null && index > 0)
    {
        leftNeighbor = index > 0 ? root.ToList()[index + step] : null;
        index += step;
    }
    return leftNeighbor;
}

Number CreateNumber(string line)
{
    Number root = new(null, 1);
    Number currentNumber = root;
    int depth = 1;

    for (int i = 0; i < line.Length; i++)
    {
        switch (line[i])
        {
            case '[':
                depth++;
                currentNumber.N1 = new Number(currentNumber, depth);
                currentNumber = currentNumber.N1;
                break;

            case ',':
                currentNumber.Parent.N2 = new Number(currentNumber.Parent, depth);
                currentNumber = currentNumber.Parent.N2;
                break;

            case ']':
                depth--;
                currentNumber = currentNumber.Parent;
                break;

            default:
                currentNumber.Value = int.Parse(line[i].ToString());
                break;
        }
    }
    return root;
}

class Number
{
    public Number(Number? parent, int depth)
    {
        Parent = parent;
        Depth = depth;
    }

    public int Depth { get; set; }
    public Number? N1 { get; set; } = null;
    public Number? N2 { get; set; } = null;
    public int? Value { get; set; } = null;
    public Number Parent { get; set; }

    public long Magnitude => Value != null ? (long)Value : N1.Magnitude * 3 + N2.Magnitude * 2;

    public new string ToString() => Value != null ? Value.ToString() : "[" + N1.ToString() + "," + N2.ToString() + "]";

    public List<Number> ToList()
    {
        List<Number> result = new() { this };
        if (N1 != null) result.AddRange(N1.ToList());
        if (N2 != null) result.AddRange(N2.ToList());
        return result;
    }

    public Number IncreaseDepth()
    {
        Depth++;
        if (N1 != null) N1.IncreaseDepth();
        if (N2 != null) N2.IncreaseDepth();
        return this;
    }

    public void Recreate()
    {
        N1.Depth -= 1;
        Value = 0;
        N1 = null;
        N2 = null;
    }
}