from collections import Counter
from typing import List

NUMBERS = {0: "abcefg", 1: "cf", 2: "acdeg", 3: "acdfg", 4: "bcdf", 5: "abdfg", 6: "abdefg", 7: "acf", 8: "abcdefg",
           9: "abcdfg"}

LETTER_MAPPING = Counter("".join(NUMBERS.values()))
SUM_MAPPING = {sum([LETTER_MAPPING[i] for i in n]): str(k) for k, n in NUMBERS.items()}


def get_number_of_string(input_string, letter_mapping, sum_mapping):
    return sum_mapping[sum([letter_mapping[i] for i in input_string])]


def part_1(output_values: List[str]) -> int:
    count = 0
    for i in output_values:
        for j in i:
            if len(j) in [2, 3, 4, 7]:
                count += 1
    return count


def part_2(input_values: List[str], output_values: List[List[str]]) -> int:
    result = 0
    for i, v in enumerate(input_values):
        value = ""
        letter_mapping_current = Counter(v)

        for j in output_values[i]:
            value += get_number_of_string(j, letter_mapping_current, SUM_MAPPING)

        result += int(value)
    return result


if __name__ == '__main__':
    data = [l.split("|") for i, l in enumerate(open("./input.txt").read().splitlines())]
    input_ = [i[0].strip() for i in data]
    output_ = [i[1].strip().split() for i in data]

    print(f"Solution of part 1: {part_1(output_)}")
    print(f"Solution of part 2: {part_2(input_, output_)}")
