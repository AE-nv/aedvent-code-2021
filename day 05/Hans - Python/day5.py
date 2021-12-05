from collections import namedtuple
from typing import List

Point = namedtuple("Point", "x y")


def get_points_on_line(p1: Point, p2: Point) -> List[Point]:
    rico = (p2.y - p1.y) / (p2.x - p1.x)

    if abs(rico) != 1.0:
        return None

    sign_x = 1 if p2.x - p1.x > 0 else - 1
    sign_y = 1 if p2.y - p1.y > 0 else - 1

    return [Point(p1.x + sign_x * i, p1.y + sign_y * i) for i in range(abs(p2.x - p1.x) + 1)]


def part_1(input_lines):
    map_ = dict()
    for l in input_lines.splitlines():
        p1, p2 = l.split("->")

        p1 = Point(*map(int, p1.split(",")))
        p2 = Point(*map(int, p2.split(",")))

        if (p1.x == p2.x) or (p1.y == p2.y):
            for i in range(abs(p2.x - p1.x) + 1):
                for j in range(abs(p2.y - p1.y) + 1):
                    f_p = min(p1, p2)
                    key = f"{f_p.x + i}-{f_p.y + j}"
                    map_.update({key: map_.get(key, 0) + 1})

    return len({k: v for k, v in map_.items() if v > 1})


def part_2(input_lines):
    map_ = dict()

    for l in input_lines.splitlines():
        p1, p2 = l.split("->")

        p1 = Point(*map(int, p1.split(",")))
        p2 = Point(*map(int, p2.split(",")))

        if (p1.x == p2.x) or (p1.y == p2.y):
            for i in range(abs(p2.x - p1.x) + 1):
                for j in range(abs(p2.y - p1.y) + 1):
                    f_p = min(p1, p2)
                    key = f"{f_p.x + i}-{f_p.y + j}"
                    map_.update({key: map_.get(key, 0) + 1})

        else:
            points = get_points_on_line(p1, p2)
            for p in points:
                key = f"{p.x}-{p.y}"
                map_.update({key: map_.get(key, 0) + 1})

    return len({k: v for k, v in map_.items() if v > 1})


if __name__ == '__main__':
    input_ = open("./input.txt", "r").read()
    print(f"Solution of part 1: {part_1(input_)}")
    print(f"Solution of part 2: {part_2(input_)}")
