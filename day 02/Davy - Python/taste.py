from functools import reduce
from typing import NamedTuple


class Coordinate(NamedTuple):
    x: int
    y: int

    def up(self, n):
        return Coordinate(self.x, self.y - n)

    def down(self, n):
        return Coordinate(self.x, self.y + n)

    def forward(self, n):
        return Coordinate(self.x + n, self.y)

    def move(self, direction, n):
        return getattr(self, direction)(n)


instructions = list(map(lambda s: s.split(), open('input.txt', 'r').readlines()))
final_coordinate = reduce(lambda pos, cmd: pos.move(cmd[0], int(cmd[1])), instructions, Coordinate(0, 0))
print("Part 1:", final_coordinate.x * final_coordinate.y)
