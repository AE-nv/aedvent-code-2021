int startPos1 = 1;
int startPos2 = 6;

long score1 = 0;
long score2 = 0;

int turn = 1;
int rolled = 0;
int i = 0;
while (score1 < 1000 && score2 < 1000)
{
    rolled += 3;
    int sum = 0;
    if (i < 98)
    {
        sum = (i + 1) + (i + 2) + (i + 3);
        i += 3;
    }
    else if (i == 98)
    {
        sum = 99 + 100 + 1;
        i = 1;
    }
    else if (i == 99)
    {
        sum = 100 + 1 + 2;
        i = 2;
    }
    else if (i == 100)
    {
        sum = 1 + 2 + 3;
        i = 3;
    }

    if (turn == 1)
    {
        startPos1 = (startPos1 + sum) % 10;
        if (startPos1 == 0) startPos1 = 10;
        score1 += startPos1;
        turn = 2;
    }
    else if (turn == 2)
    {
        startPos2 = (startPos2 + sum) % 10;
        if (startPos2 == 0) startPos2 = 10;
        score2 += startPos2;
        turn = 1;
    }
}

long sc = Math.Min(score1, score2);
Console.WriteLine(rolled * sc);
