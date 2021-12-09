from typing import List, Tuple
from functools import reduce


def get_neighbours(matrix: List[List[int]], i: int, j: int, max_r: int, max_c: int) -> List[Tuple[int, int, int]]:
    hor = [(k, j, matrix[k][j]) for k in range(max(0, i - 1), min(i + 2, max_r)) if k != i]
    ver = [(i, l, matrix[i][l]) for l in range(max(0, j - 1), min(j + 2, max_c)) if l != j]

    return hor + ver


def get_lowest_points(matrix: List[List[int]]) -> List[Tuple[int, int, int]]:
    lowest_points = []
    nr_rows, nr_cols = len(matrix), len(matrix[0])
    for i in range(nr_rows):
        for j in range(nr_cols):
            neighbours = get_neighbours(matrix, i, j, nr_rows, nr_cols)

            if matrix[i][j] < min([n[2] for n in neighbours]):
                lowest_points.append((i, j, matrix[i][j]))

    return lowest_points


def get_basin(lower_point: Tuple[int, int, int], matrix: List[List[int]]) -> List[Tuple[int, int, int]]:
    to_check = [lower_point]
    done = set()
    next_up = []
    basin = []
    while to_check:
        for t in to_check:
            i, j, number = t
            key = f"{i}-{j}"

            if (key not in done) and (number != 9):
                basin.append(t)
                next_up += get_neighbours(matrix, i, j, len(matrix), len(matrix[0]))

            done.add(key)

        to_check = [n for n in next_up if f"{n[0]}-{n[1]}" not in done]

    return basin


if __name__ == '__main__':
    matrix = [list(map(int, i)) for i in open("./input.txt", "r").read().splitlines()]
    lowest_points = get_lowest_points(matrix)

    risk = sum([1 + l[2] for l in lowest_points])
    print(f"Solution of part 1: {risk}")

    largest_basins = sorted([len(get_basin(l, matrix)) for l in lowest_points])[-3:]
    size = reduce((lambda x, y: x * y), largest_basins)
    print(f"Solution of part 2: {size}")
