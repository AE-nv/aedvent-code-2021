class Board:
    def __init__(self, numbers):
        self.unmarked = set()
        self.columns = [5]*5
        self.rows = [5]*5
        self.positions = dict()
        for i in range(len(numbers)):
            for j in range(len(numbers[i])):
                self.positions[numbers[i][j]] = [i, j]
                self.unmarked.add(numbers[i][j])
    
    def draw(self, draw):
        if draw in self.unmarked:
            self.unmarked.remove(draw)
            position = self.positions[draw]
            self.rows[position[0]] -= 1
            self.columns[position[1]] -= 1
            if self.rows[position[0]] == 0 or self.columns[position[1]] == 0:
                return sum(list(self.unmarked))*draw
        return -1
    
    def reset(self):
        self.unmarked = set(self.positions.keys())
        self.rows = [5]*5
        self.columns = [5]*5

# parse input
lines = open('./day 04/Xavier - Python/input.txt').read().splitlines()

draws = [int(draw) for draw in lines[0].split(',')]
lines = lines[2:]

boards = []
while len(lines) > 0:
    board = lines[:5]
    rows = [line.strip().split() for line in board]
    numbers = [[int(x) for x in row] for row in rows]
    boards.append(Board(numbers))
    lines = lines[6:]

def part_one(boards, draws):
    for draw in draws:
        for board in boards:
            score = board.draw(draw)
            if score > 0:
                return score
    return 0

def part_two(boards, draws):
    finished = set()
    for draw in draws:
        for i in range(len(boards)):
            if i not in finished:
                score = boards[i].draw(draw)
                if score > 0:
                    finished.add(i)
                    if len(finished) == len(boards):
                        return score
    return 0

print(part_one(boards, draws))

for board in boards:
    board.reset()

print(part_two(boards, draws))
