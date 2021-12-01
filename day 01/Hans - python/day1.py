from typing import List, Tuple

sensor_input = [int(x) for x in open('./input.txt').readlines()]


def calculate_increase(measures: list) -> List[Tuple[int, int]]:
    return [(i + 1, x) for i, x in enumerate(measures[1:]) if x > measures[i]]


def calculate_increase_part_2(measure: list, window) -> List[Tuple[int, int]]:
    agg_measures = [sum(measure[i:i + window]) for i in range(len(measure) - window)]

    return calculate_increase(agg_measures)


def calculate_increase_part_2_efficient(measure: list, window) -> List[Tuple[int, int]]:
    increases = []

    previous_sum = sum(measure[:window])

    for i in range(1, len(measure) - window):
        current_sum = sum(measure[i:i + window])

        increases.append((i, current_sum)) if current_sum > previous_sum else None

        previous_sum = current_sum

    return increases


if __name__ == '__main__':
    increases_part_1 = calculate_increase(sensor_input)
    print(f"Solution of part 1: {len(increases_part_1)}")

    increases_part_2 = calculate_increase_part_2_efficient(sensor_input, window=3)
    print(f"Solution of part 2: {len(increases_part_2)}")
