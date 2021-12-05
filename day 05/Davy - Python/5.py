from collections import namedtuple, defaultdict
from typing import NamedTuple
from PIL import Image
import re
import numpy as np

Point = namedtuple('Point', ['x', 'y'])


class Line(NamedTuple):
    start: Point
    end: Point

    def is_horizontal_or_vertical(self):
        return self.start.y == self.end.y or self.start.x == self.end.x

    def get_points(self):
        dir_x = 1 if (self.start.x < self.end.x) else 0 if (self.start.x == self.end.x) else -1
        dir_y = 1 if (self.start.y < self.end.y) else 0 if (self.start.y == self.end.y) else -1
        x, y = self.start.x, self.start.y
        points = [Point(x, y)]
        while x != self.end.x or y != self.end.y:
            x = x + dir_x
            y = y + dir_y
            points.append(Point(x, y))
        return points

    @classmethod
    def from_string(cls, text):
        start_x, start_y, end_x, end_y = list(map(int, re.split(',| -> ', text)))
        return Line(Point(start_x, start_y), Point(end_x, end_y))


def count_intersections(lines):
    grid = defaultdict(int)
    for line in lines:
        for p in line.get_points():
            grid[p] += 1
    return len(list(filter(lambda v: v > 1, grid.values())))


def render(lines, file):
    grid = np.full((1000, 1000, 3), (0, 0, 0), np.uint8)
    for line in lines:
        for p in line.get_points():
            grid[p.y, p.x] = (255, 255, 255) if grid[p.y, p.x][0] == 0 else (255, 0, 0)
    image = Image.fromarray(grid, 'RGB')
    image.save(file)


all_lines = list(map(Line.from_string, open('input.txt', 'r').read().splitlines()))
h_v_lines = list(filter(lambda l: l.is_horizontal_or_vertical(), all_lines))
print("Part 1:", count_intersections(h_v_lines))
render(h_v_lines, 'part_1_lines.png')
print("Part 2:", count_intersections(all_lines))
render(all_lines, 'part_2_lines.png')
