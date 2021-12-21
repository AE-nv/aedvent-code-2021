from functools import lru_cache
player1 = 9
player2 = 6

score1 = 0
score2 = 0

dice = 1
rolls = 0


def get3numbers():
    global rolls
    global dice
    numbers = 0
    for i in range(3):
        if dice > 100:
            dice = 1
        numbers += dice
        dice += 1
        rolls += 1
    return numbers


def get_position(location, amount):
    # space is 1-10, modulo is 0-9, so add 1 and sub 1
    return ((location - 1 + amount) % 10) + 1


while score2 < 1000:
    player1 = get_position(player1, get3numbers())
    score1 += player1
    if score1 >= 1000:
        break
    player2 = get_position(player2, get3numbers())
    score2 += player2

if score1 < score2:
    print('part 1: %i' % (score1 * rolls))
else:
    print('part 1: %i' % (score2 * rolls))


@lru_cache(maxsize=None)
def count_wins(score1, pos1, score2, pos2, turn):
    if score1 >= 21:
        return [1, 0]
    elif score2 >= 21:
        return [0, 1]
    result = [0, 0]
    # sum of 3 dice : frequency in universes
    for k, v in {3: 1, 9: 1, 4: 3, 8: 3, 5: 6, 7: 6, 6: 7}.items():
        if turn == 1:
            p = get_position(pos1, k)
            sub_result = count_wins(score1 + p, p, score2, pos2, 2)
        else:
            p = get_position(pos2, k)
            sub_result = count_wins(score1, pos1, score2 + p, p, 1)
        result[0], result[1] = result[0] + v * sub_result[0], result[1] + v * sub_result[1]  # v * because result is v times
    return result


player1 = 9
player2 = 6


print('part 2: %i' % sorted(count_wins(0, player1, 0, player2, 1))[1])
