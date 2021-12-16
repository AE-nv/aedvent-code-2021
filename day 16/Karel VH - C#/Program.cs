var input = string.Join("", File.ReadLines("input.txt").First().Select(x => Convert.ToString(int.Parse(x.ToString(), System.Globalization.NumberStyles.HexNumber), 2).PadLeft(4, '0')));

List<int> versions = new();

Console.WriteLine(CheckType(0).Item1);
Console.WriteLine(versions.Sum());


(long, int) CheckType(int index, int maxSize = 0)
{
    int originalIndex = index;
    (int typeVersion, int typeID) = GetHeaders(index);
    index += 6;
    if (maxSize != 0 && index > maxSize)
        return (0, 0);
    versions.Add(typeVersion);
    switch (typeID)
    {
        case 4:
            string number = "";
            while (input[index].Equals('1'))
            {
                number += string.Join("", input[(index + 1)..(index + 5)]);
                index += 5;
            }
            number += string.Join("", input[(index + 1)..(index + 5)]);
            index += 5;
            return (Convert.ToInt64(number, 2), index - originalIndex);
        default:
            int typeLengthID = int.Parse(input[index].ToString());
            index++;
            List<long> values = new();
            if (typeLengthID == 0)
            {
                int length = Convert.ToInt32(string.Join("", input[(index)..(index + 15)]), 2);
                index += 15;
                int packetsRead = 0;
                while (packetsRead < length)
                {
                    (long num, int read) = CheckType(index + packetsRead, index + length);
                    packetsRead += read;
                    values.Add(num);
                }
                index += length;
            }
            if (typeLengthID == 1)
            {
                int numberOfPackets = Convert.ToInt32(string.Join("", input[(index)..(index + 11)]), 2);
                index += 11;
                int packetsRead = 0;
                for (int i = 0; i < numberOfPackets; i++)
                {
                    (long num, int read) = CheckType(index + packetsRead);
                    packetsRead += read;
                    values.Add(num);
                }
                index += packetsRead;
            }
            switch (typeID)
            {
                case 0: return (values.Sum(), index - originalIndex);
                case 1: return (values.Aggregate(1L, (mul, curr) => mul *= curr), index - originalIndex);
                case 2: return (values.Min(), index - originalIndex);
                case 3: return (values.Max(), index - originalIndex);
                case 5: return ((values[0] > values[1] ? 1L : 0L), index - originalIndex);
                case 6: return ((values[0] < values[1] ? 1L : 0L), index - originalIndex);
                case 7: return ((values[0] == values[1] ? 1L : 0L), index - originalIndex);
            }
            return (0, 0);
    }
}

(int, int) GetHeaders(int index) => (Convert.ToInt32(string.Join("", input[index..(index + 3)]), 2), Convert.ToInt32(string.Join("", input[(index + 3)..(index + 6)]), 2));


