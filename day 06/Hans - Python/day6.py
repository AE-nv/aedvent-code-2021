from typing import List


def count_fish(days_ahead: int, fish_pipeline: List[int]) -> int:
    pipeline = [i for i in fish_pipeline]
    for _ in range(days_ahead):
        new_borns = pipeline.pop()
        pipeline[1] += new_borns

        pipeline.insert(0, new_borns)

    return sum(pipeline)


if __name__ == '__main__':
    PIPELINE = [0] * 9
    start = list(map(int, open("./input.txt", "r").read().split(',')))

    for s in start:
        PIPELINE[8 - s] += 1

    print(f"Solution of part 1: {count_fish(80, PIPELINE)}")
    print(f"Solution of part 2: {count_fish(256, PIPELINE)}")
