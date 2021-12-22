var input = File.ReadAllLines("input.txt");

var snailfish = input.Select(SnailFish.Parse).ToImmutableList();

var part1 = snailfish.Aggregate((result, n) => (result + n).Reduce()).Magnitude();

var part2 = (
    from s1 in snailfish
    from s2 in snailfish
    where s1 != s2
    select (s1 + s2).Reduce().Magnitude()
    ).DefaultIfEmpty().Max();

Console.WriteLine((part1, part2));

record SnailFish(ImmutableList<Token> Items)
{
    public static SnailFish Parse(string s) => new SnailFish(Elements(s).ToImmutableList());
    static IEnumerable<Token> Elements(string line) =>
        from c in line
        where c != ','
        select c switch
        {
            '[' => Token.OpenBrace,
            ']' => Token.CloseBrace,
            _ => Token.FromValue(c - '0')
        };
    public static SnailFish operator +(SnailFish a, SnailFish b) 
    => new SnailFish(
        Empty<Token>()
        .Concat(Repeat(Token.OpenBrace, 1))
        .Concat(a.Items).Concat(b.Items)
        .Concat(Repeat(Token.CloseBrace, 1))
        .ToImmutableList());
    public int Magnitude()
    {
        var builder = Items.ToBuilder();
        bool keepgoing = true;
        while (keepgoing)
        {
            keepgoing = false;
            for (var i = 0; i < builder.Count - 2; i++)
            {
                if (builder[i].IsValue && builder[i + 1].IsValue)
                {
                    builder[i] = Token.Combine(builder[i], builder[i + 1]);
                    builder.RemoveAt(i + 2);
                    builder.RemoveAt(i + 1);
                    builder.RemoveAt(i - 1);
                    keepgoing = true;
                    break;
                }
            }
        }

        return builder[0].Value;
    }

    public SnailFish Reduce()
    {
        var builder = Items.ToBuilder();
        while (true)
        {
            bool exploded = false;
            var level = 0;
            for (var i = 0; i < builder.Count; i++)
            {
                level += builder[i].BraceIncrement;
                if (level == 5)
                {
                    Explode(builder, i);
                    exploded = true;
                    break;
                }
            }

            if (exploded)
                continue;
            bool split = false;
            for (var i = 0; i < builder.Count; i++)
            {
                if (builder[i].ShouldSplit)
                {
                    var item = builder[i];
                    builder.RemoveAt(i);
                    builder.InsertRange(i, item.Split());
                    split = true;
                    break;
                }
            }

            if (split)
                continue;
            break;
        }

        return new SnailFish(builder.ToImmutableList());
    }

    static void Explode(ImmutableList<Token>.Builder builder, int position)
    {
        (var left, var right) = FindLeftAndRightValues(builder, position);
        if (left.HasValue)
            builder[left.Value] = builder[left.Value] + builder[position + 1];
        else
            builder[position + 1] = Token.Zero;
        if (right.HasValue)
            builder[right.Value] = builder[right.Value] + builder[position + 2];
        else
            builder[position + 2] = Token.Zero;
        builder[position] = Token.Zero;
        builder.RemoveAt(position + 3);
        builder.RemoveAt(position + 2);
        builder.RemoveAt(position + 1);
    }

    static (int? left, int? right) FindLeftAndRightValues(ImmutableList<Token>.Builder builder, int position) 
    => (left: (
            from i in Range(0, position)
            where builder[position - i].IsValue
            select (int?)(position - i)
            ).FirstOrDefault(), 
        right: (
            from i in Range(position + 4, builder.Count - position - 4)
            where builder[i].IsValue
            select (int?)i
            ).FirstOrDefault());
}

record struct Token
{
    enum TokenType
    {
        Value,
        OpenBrace,
        CloseBrace
    }

    private TokenType _type;
    private int _value;
    public int Value => _type switch
    {
        TokenType.Value => _value,
        _ => throw new InvalidOperationException("Can not retrieve value from non-value token")
    };
    private Token(TokenType type, int value)
    {
        _type = type;
        _value = value;
    }
    public static readonly Token OpenBrace = new(TokenType.OpenBrace, 0);
    public static readonly Token CloseBrace = new(TokenType.CloseBrace, 0);
    public static readonly Token Zero = new(TokenType.Value, 0);
    public static Token FromValue(int value) => new(0, value);
    public static Token Combine(Token left, Token right) => (left, right) switch
    {
        ({ IsValue: true }, { IsValue: true }) => FromValue(left.Value * 3 + right.Value * 2),
        _ => throw new InvalidOperationException("only 'Value' tokens can be combined")
    };
    public bool IsValue => _type == TokenType.Value;
    public int BraceIncrement => _type switch
    {
        TokenType.OpenBrace => 1,
        TokenType.CloseBrace => -1,
        _ => 0
    };
    public static Token operator +(Token left, Token right) => (left, right) switch
    {
        ({ IsValue: true }, { IsValue: true }) => FromValue(left.Value + right.Value),
        _ => throw new InvalidOperationException("only 'Value' tokens can be added")
    };
    public bool ShouldSplit => IsValue && Value > 9;
    public IEnumerable<Token> Split()
    {
        var left = Value / 2;
        var right = Value - left;
        yield return OpenBrace;
        yield return FromValue(left);
        yield return FromValue(right);
        yield return CloseBrace;
    }
}