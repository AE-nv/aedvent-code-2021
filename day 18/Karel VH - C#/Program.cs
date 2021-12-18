var inputText = File.ReadAllLines("input.txt");
var input = inputText.Select(x => CreateNumber(x)).ToList();

Number currentResult = input.First();

//PART 1
foreach (Number n in input.Skip(1))
{
    Number root = new(null, 0);
    root.N1 = currentResult;
    root.N2 = n;

    Console.WriteLine("   " + root.N1.ToString());
    Console.WriteLine("+  " + root.N2.ToString());

    Reduce(root);

    Console.WriteLine("=    " + root.ToString());
    Console.WriteLine("");

    currentResult = root;
    currentResult.IncreaseDepth();
}

Console.WriteLine(currentResult.Magnitude);

//PART 2
long currentMax = 0;
for (int i = 0; i < input.Count; i++)
{
    for (int j = 0; j < input.Count; j++)
    {
        if (i == j) continue;
        Number root = new(null, 0);
        root.N1 = CreateNumber(inputText[i]);
        root.N2 = CreateNumber(inputText[j]);
        Reduce(root);
        if (root.Magnitude > currentMax) currentMax = root.Magnitude;
    }
}
Console.WriteLine(currentMax);

void Reduce(Number root)
{
    //First explode, then keep exploding until we can no longer explode, then split
    //Then do it all again
    Number? depth4 = root.ToList().Where(x => x.Depth >= 4 && x.Value == null && x.N1.Value != null && x.N2.Value != null).DefaultIfEmpty(null).First();
    Number? toBeSplit = null;

    while (depth4 != null || toBeSplit != null)
    {
        while (depth4 != null)
        {
            Explode(root, depth4);
            depth4 = root.ToList().Where(x => x.Depth >= 4 && x.Value == null && x.N1.Value != null && x.N2.Value != null).DefaultIfEmpty(null).First();
        }
        Split(root);

        depth4 = root.ToList().Where(x => x.Depth >= 4 && x.Value == null && x.N1.Value != null && x.N2.Value != null).DefaultIfEmpty(null).First();
        toBeSplit = root.ToList().Where(x => x.Value != null && x.Value > 9).DefaultIfEmpty(null).First();
    }
}

void Split(Number root)
{
    Number toBeSplit = root.ToList().Where(x => x.Value != null && x.Value > 9).DefaultIfEmpty(null).First();
    if (toBeSplit != null)
    {
        //Console.WriteLine("Split : " + toBeSplit.ToString());
        toBeSplit.N1 = new Number(toBeSplit, toBeSplit.Depth + 1) { Value = (int)Math.Floor((double)toBeSplit.Value / 2.0) };
        toBeSplit.N2 = new Number(toBeSplit, toBeSplit.Depth + 1) { Value = (int)Math.Ceiling((double)toBeSplit.Value / 2.0) };
        toBeSplit.Value = null;
        int sIndex = root.ToList().IndexOf(toBeSplit);
        //Console.WriteLine("After split: " + root.ToString());
    }
}

void Explode(Number root, Number depth4)
{
    int index = root.ToList().IndexOf(depth4);
    //Console.WriteLine("Explode : " + depth4.ToString());
    Number? leftNeighbor = index > 0 ? root.ToList()[index - 1] : null;
    if (leftNeighbor != null)
    {
        while (leftNeighbor != null && leftNeighbor.Value == null && index > 0)
        {
            leftNeighbor = index > 0 ? root.ToList()[index - 1] : null;
            index--;
        }
    }

    index = root.ToList().IndexOf(depth4);
    Number? rightNeighbor = index < root.ToList().Count - 3 ? root.ToList()[index + 3] : null;
    index += 2;
    if (rightNeighbor != null)
    {
        while (rightNeighbor != null && rightNeighbor.Value == null && index > 0)
        {
            rightNeighbor = index > 0 ? root.ToList()[index + 1] : null;
            index++;
        }
    }

    if (leftNeighbor != null)
        leftNeighbor.Value += depth4.N1.Value;

    if (rightNeighbor != null)
        rightNeighbor.Value += depth4.N2.Value;

    depth4.N1.Depth -= 1;
    depth4.Value = 0;
    depth4.N1 = null;
    depth4.N2 = null;
    //Console.WriteLine("Explode result: " + root.ToString());
    depth4 = root.ToList().Where(x => x.Depth >= 4 && x.Value == null && x.N1.Value != null && x.N2.Value != null).DefaultIfEmpty(null).First();
}


Number CreateNumber(string line)
{
    Number root = new(null, 1);
    Number currentNumber = root;
    //numbersOrdered.Add(currentNumber);
    int depth = 1;

    //Read
    for (int i = 0; i < line.Length; i++)
    {

        switch (line[i])
        {
            case '[':
                depth++;
                currentNumber.N1 = new Number(currentNumber, depth);
                currentNumber = currentNumber.N1;
                //numbersOrdered.Add(currentNumber);
                break;

            case ',':
                currentNumber.Parent.N2 = new Number(currentNumber.Parent, depth);
                currentNumber = currentNumber.Parent.N2;
                //numbersOrdered.Add(currentNumber);
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
        List<Number> result = new();
        result.Add(this);
        if (N1 != null)
            result.AddRange(N1.ToList());
        if (N2 != null)
            result.AddRange(N2.ToList());
        return result;
    }

    public void IncreaseDepth()
    {
        Depth++;
        if (N1 != null) N1.IncreaseDepth();
        if (N2 != null) N2.IncreaseDepth();
    }
}