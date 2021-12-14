from functools import reduce
coords, instructions = [part.splitlines() for part in open('example.txt').read().split('\n\n')]
points = [[int(i) for i in c.split(',')] for c in coords]

def fold_point(x, y, instructions):
    f_x = reduce(lambda x, fold: fold-(x-fold) if x>fold else x, [int(i.split('=')[-1]) for i in instructions if 'x' in i], x)
    f_y = reduce(lambda y, fold: fold-(y-fold) if y>fold else y, [int(i.split('=')[-1]) for i in instructions if 'y' in i], y)
    return (f_x,f_y)

def fold(points, instructions):
    return set([fold_point(*point, instructions) for point in points])

def visualize(points):
    grid_size = (max([p[0] for p in points]), max([p[1] for p in points]))
    for line in [['*' if (x,y) in points else ' ' for x in range(grid_size[0]+1)] for y in range(grid_size[1]+1)]:
        print(''.join(line))

print('Part 1:', len(fold(points, instructions[0:1])))
visualize(fold(points, instructions))