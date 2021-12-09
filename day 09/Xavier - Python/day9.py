def parse_input(file):
    return [[int(h) for h in l] for l in open(file).read().splitlines()]

input = parse_input('./day 09/Xavier - Python/input.txt')
example = parse_input('./day 09/Xavier - Python/example.txt')

def get_neighbours(map,i,j):
    neighbours = []
    if i > 0:
        neighbours.append((i-1,j))
    if i < len(map)-1:
        neighbours.append((i+1,j))
    if j > 0:
        neighbours.append((i,j-1))
    if j < len(map[i])-1:
        neighbours.append((i,j+1))
    return neighbours

def low_points(map):
    low_points = []
    for i in range(len(map)):
        for j in range(len(map[i])):
            if map[i][j] < min([map[i][j] for i,j in get_neighbours(map, i , j)]):
                low_points.append((i,j))
    return low_points

lp_ex = low_points(example)
lp_in = low_points(input)

assert len(lp_ex)+sum([example[i][j] for i,j in lp_ex]) == 15
print(len(lp_in)+sum([input[i][j] for i,j in lp_in]))

def expand(map, point):
    points = {point}
    neighbours = get_neighbours(map, *point)
    for n in neighbours:
        if not map[n[0]][n[1]] == 9 and map[n[0]][n[1]] > map[point[0]][point[1]]:
            points = points.union(expand(map, n))
    return points

def part_two(map, low_points):
    sizes = []
    for p in low_points:
        basin = expand(map, p)
        sizes.append(len(basin))
    sizes.sort(reverse=True)
    return sizes[0]*sizes[1]*sizes[2]

assert part_two(example, lp_ex) == 1134
print(part_two(input, lp_in))