from functools import lru_cache

def new_position(position, roll):
    position = (position+roll)%10
    if position == 0:
        position = 10
    return position

def deterministic_dice(positions):
    scores = [0,0]
    die = 1
    while max(scores)<1000:
        roll = die*3+3
        die += 3
        player = die%2
        positions[player] = new_position(positions[player], roll)
        scores[player] += positions[player]
    return (die-1)*min(scores)

@lru_cache(maxsize=None)
def dirac_dice(positions, scores, turn):
    # check if game is over
    if scores[0] >= 21:
        return [1,0]
    elif scores[1] >= 21:
        return [0,1]
    # continue playing
    wins = [0,0]
    # check all possible combinations of the three rolls
    for roll1 in [1,2,3]:
        for roll2 in [1,2,3]:
            for roll3 in [1,2,3]:
                new_pos = new_position(positions[turn], roll1+roll2+roll3)
                new_score = scores[turn]+new_pos
                results = []
                if turn == 0:
                    results = dirac_dice((new_pos, positions[1]), (new_score, scores[1]), 1)
                else:
                    results = dirac_dice((positions[0], new_pos), (scores[0], new_score), 0)
                wins[0] += results[0]
                wins[1] += results[1]
    return wins

positions = [int(x[-1]) for x in open('input.txt').read().splitlines()]
print('Part one:', deterministic_dice(positions))

positions = [int(x[-1]) for x in open('input.txt').read().splitlines()]
print('Part two:', max(dirac_dice((positions[0], positions[1]), (0, 0), 0)))