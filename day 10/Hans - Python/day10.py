from functools import reduce
from typing import Tuple, List, Any

ERROR_POINTS = dict(zip([')', '>', ']', '}'], [3, 25137, 57, 1197]))
CORRECTION_POINTS = dict(zip([')', '>', ']', '}'], [1, 4, 2, 3]))
SYMBOL_MAPPING = {"(": ")", "<": ">", "[": "]", "{": "}"}
SYMBOLS_OPEN = set(SYMBOL_MAPPING.keys())
SYMBOLS_CLOSE = set(SYMBOL_MAPPING.values())


def parse_line(line: str) -> Tuple[Any, List[str]]:
    opened_list = []
    for i, s in enumerate(line):
        if s in SYMBOLS_OPEN:
            opened_list.append((i, s))
        elif s in SYMBOLS_CLOSE:
            last_index, last_symbol = opened_list.pop()
            if SYMBOL_MAPPING[last_symbol] != s:
                return s, opened_list

    return None, opened_list


def correct_line(open_list: List[str]) -> Tuple[str, int]:
    to_add = []
    for _ in range(len(open_list)):
        i, s = open_list.pop()
        to_add.append(SYMBOL_MAPPING[s])

    return "".join(to_add), reduce((lambda x, y: 5 * x + y), [CORRECTION_POINTS[i] for i in to_add])


def part_1(commands: List[str]) -> int:
    p_lines = [parse_line(c)[0] for c in commands]
    return sum([ERROR_POINTS[i] for i in p_lines if i])


def part_2(commands: List[str]) -> List[Tuple[str, int]]:
    parsed_lines = [parse_line(c) for c in commands]
    return [correct_line(c[1]) for c in parsed_lines if not c[0]]


if __name__ == '__main__':
    data = open("./input.txt", "r").read().splitlines()

    print(f"Solution of part 1: {part_1(data)}")

    p2 = part_2(data)
    middle = int(len(p2) / 2)
    score = sorted([s[1] for s in p2], reverse=True)[middle]
    print(f"Solution of part 1: {score}")
