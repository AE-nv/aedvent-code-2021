from functools import reduce


class Position:
    pos: int
    depth: int
    aim: int

    def __init__(self, pos, depth, aim):
        self.pos = pos
        self.depth = depth
        self.aim = aim

    def up(self, x):
        return Position(self.pos, self.depth, self.aim - x)

    def down(self, x):
        return Position(self.pos, self.depth, self.aim + x)

    def forward(self, x):
        return Position(self.pos + x, self.depth + self.aim * x, self.aim)

    def move(self, direction, x):
        return getattr(self, direction)(x)


instructions = list(map(lambda s: s.split(), open('input.txt', 'r').readlines()))
final_coordinate = reduce(lambda pos, cmd: pos.move(cmd[0], int(cmd[1])), instructions, Position(0, 0, 0))
print("Part 2:", final_coordinate.pos * final_coordinate.depth)
