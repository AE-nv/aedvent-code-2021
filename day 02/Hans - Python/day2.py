from typing import List, Tuple
from collections import namedtuple
from enum import Enum


class Direction(Enum):
    FORWARD = "FORWARD"
    UP = "UP"
    DOWN = "DOWN"


Instruction = namedtuple('Instruction', 'direction units')


def get_combined_directions(instructions_list: List[Instruction]) -> Tuple[int, int]:
    result = dict.fromkeys([d for d in Direction], 0)

    for i in instructions_list:
        result[i.direction] += int(i.units)

    return result[Direction.FORWARD], result[Direction.DOWN] - result[Direction.UP]


def get_combined_directions_aim(instructions_list: List[Instruction]) -> Tuple[int, int, int]:
    c_aim = 0
    c_horizontal = 0
    c_vertical = 0

    for i in instructions_list:
        if i.direction in [Direction.UP, Direction.DOWN]:
            c_aim += int(i.units) if i.direction == Direction.DOWN else -int(i.units)

        elif i.direction == Direction.FORWARD:
            c_vertical += c_aim * int(i.units)
            c_horizontal += int(i.units)

    return c_horizontal, c_vertical, c_aim


def parse_input(input_str: str):
    direction, unit = input_str.upper().split()
    return Instruction(Direction(direction), int(unit))


if __name__ == '__main__':
    instructions = list(map(parse_input, open('./example.txt').readlines()))

    horizontal, vertical = get_combined_directions(instructions_list=instructions)
    print(f"Solution of part 1: {horizontal * vertical}")

    horizontal, vertical, aim = get_combined_directions_aim(instructions_list=instructions)
    print(f"Solution of part 2: {horizontal * vertical}")
