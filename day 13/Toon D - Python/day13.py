lines = open("day 13/Toon D - Python/input", "r").readlines()
coordinates = [[int(coor) for coor in line.replace('\n', '').split(',')]
               for line in lines if "," in line]
folds = [line[11:].replace('\n', '').split('=')
         for line in lines if '=' in line]
for element in folds:
    element[1] = int(element[1])


def fold(coordinates, direction, position):
    new_coordinates = []
    for coordinate in coordinates:
        if direction == 'x':
            if coordinate[0] > position:
                new_coordinates.append(
                    [(position * 2) - coordinate[0], coordinate[1]])
            else:
                new_coordinates.append(coordinate)
        if direction == 'y':
            if coordinate[1] > position:
                new_coordinates.append(
                    [coordinate[0], (position * 2) - coordinate[1]])
            else:
                new_coordinates.append(coordinate)

    present = set()
    result = []
    for coordinate in new_coordinates:
        if str(coordinate) not in present:
            present.add(str(coordinate))
            result.append(coordinate)
    return result


print("part 1: %i" % len(fold(coordinates, folds[0][0], folds[0][1])))

result = coordinates
for fold_element in folds:
    result = fold(result, fold_element[0], fold_element[1])

x_max = max([coordinate[0] for coordinate in result])
y_max = max([coordinate[1] for coordinate in result])

present = set([str(coordinate) for coordinate in result])

for y in range(y_max+1):
    row = ''
    for x in range(x_max+1):
        if str([x, y]) in present:
            row += '#'
        else:
            row += ' '
    print(row)
