var input = File.ReadAllLines("input.txt")[0];

var part1 = new Packet(input.ToBinary()).GetVersionSum();
var part2 = new Packet(input.ToBinary()).Value;

Console.WriteLine((part1, part2));

static class Ex
{
    public static string ToBinary(this string input) => string.Join(string.Empty, input.Select(c => Convert.ToString(Convert.ToInt32(c.ToString(), 16), 2).PadLeft(4, '0')));
}

internal class Packet
{
    private int Length;
    public IEnumerable<Packet> Children => children;
    private List<Packet> children { get; } = new();
    private int version;
    private int type;
    private string binary;
    public Packet(string binary)
    {
        this.binary = binary;
        // header
        version = Convert.ToInt32(Read(3).ToString(), 2);
        type = Convert.ToInt32(Read(3).ToString(), 2);
        // data
        if (type == 4)
        {
            StringBuilder result = new();
            bool done = false;
            while (!done)
            {
                if (Read(1)[0] == '0')
                    done = true;
                result.Append(Read(4));
            }

            Value = Convert.ToInt64(result.ToString(), 2);
        }
        else
        {
            var subpackets = (Read(1)[0] switch
            {
                '0' => ReadNumberOfBits(Convert.ToInt32(Read(15).ToString(), 2)),
                _ => ReadNumberOfPackets(Convert.ToInt32(Read(11).ToString(), 2))
            }).ToList();
            children.AddRange(subpackets);
            var subValues = subpackets.Select(packet => packet.Value);
            Value = type switch
            {
                0 => subValues.Sum(),
                1 => subValues.Aggregate((long)1, (x, y) => x * y),
                2 => subValues.Min(),
                3 => subValues.Max(),
                4 => Value,
                5 => subValues.First() > subValues.Last() ? 1 : 0,
                6 => subValues.First() < subValues.Last() ? 1 : 0,
                7 => subValues.First() == subValues.Last() ? 1 : 0,
                _ => throw new NotImplementedException(),
            };
        }
    }

    public long Value { get; set; }

    public int GetVersionSum() => children.Aggregate(version, (s, sub) => s + sub.GetVersionSum());
    private ReadOnlySpan<char> Read(int amount)
    {
        var span = binary.AsSpan(Length, amount);
        Length += amount;
        return span;
    }

    Packet ReadOnePacket()
    {
        var subpacket = new Packet(binary.Substring(Length));
        Length += subpacket.Length;
        return subpacket;
    }

    private IEnumerable<Packet> ReadNumberOfPackets(int number)
    {
        for (int i = 0; i < number; i++)
            yield return ReadOnePacket();
    }

    private IEnumerable<Packet> ReadNumberOfBits(int length)
    {
        var max = Length + length;
        while (Length < max)
            yield return ReadOnePacket();
    }
}