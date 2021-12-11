def parse(file):
    return [[int(oct) for oct in line] for line in open(file).read().splitlines()]

example = parse('./day 11/Xavier - Python/example.txt')
input = parse('./day 11/Xavier - Python/input.txt')

def flash(map, x, y):
    map[x][y] = 0
    for i in [-1, 0, 1]:
        for j in [-1, 0, 1]:
            try:
                if not x+i < 0 and not y+j < 0 and not map[x+i][y+j] == 0:
                    map[x+i][y+j] += 1
            except IndexError:
                continue
    return map

def step(map):
    flashes = 0
    map = [[x+1 for x in line] for line in map]
    locs = [(i,j) for i in range(len(map)) for j in range(len(map)) if map[i][j] > 9]
    while(len(locs)>0):
        flashes += len(locs)
        for l in locs:
            map = flash(map, *l)
        locs = [(i,j) for i in range(len(map)) for j in range(len(map)) if map[i][j] > 9]
    return map, flashes

def p1(map):
    flashes = 0
    for _ in range(100):
        map, f = step(map)
        flashes += f
    return flashes

assert p1(example) == 1656
print(p1(input))

def p2(map):
    i = 0
    while True:
        i += 1
        map, f = step(map)
        if f == len(map)**2:
            return i

assert p2(example) == 195
print(p2(input))
