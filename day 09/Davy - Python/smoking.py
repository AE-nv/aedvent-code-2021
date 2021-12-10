import numpy

height_map = [list(map(int, list(line))) for line in open('input.txt', 'r').read().splitlines()]
rows = len(height_map)
cols = len(height_map[0])
low_points, risk_levels, basins = [], [], []


def get_coordinate(col, row):
    if col < 0 or col > cols - 1 or row < 0 or row > rows - 1:
        return None
    return col, row


def get_neighbours(col, row):
    return list(map(lambda coordinate: height_map[coordinate[1]][coordinate[0]], get_neighbours_coordinates(col, row)))


def get_neighbours_coordinates(col, row):
    return list(filter(lambda v: v,
                       [get_coordinate(col - 1, row), get_coordinate(col + 1, row),
                        get_coordinate(col, row - 1), get_coordinate(col, row + 1)]))


for i in range(cols):
    for j in range(rows):
        if height_map[j][i] < min(get_neighbours(i, j)):
            risk_levels.append(height_map[j][i] + 1)
            low_points.append((i, j))
print("Part 1:", sum(risk_levels))

for low_point in low_points:
    points_to_check = [low_point]
    basin_squares = set()
    while points_to_check:
        to_check = points_to_check.pop()
        basin_squares.add(to_check)
        for unchecked_coord in list(
                filter(lambda c: height_map[c[1]][c[0]] < 9 and c not in basin_squares and c not in points_to_check,
                       get_neighbours_coordinates(to_check[0], to_check[1]))):
            points_to_check.append(unchecked_coord)
    basins.append(len(basin_squares))
print("Part 2:", numpy.prod(sorted(basins)[-3:]))
