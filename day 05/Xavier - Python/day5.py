from collections import defaultdict
danger_points = defaultdict(int)

for line in open('./day 05/Xavier - Python/input.txt').read().splitlines():
    coords = [list(map(int,c.split(','))) for c in line.split(' -> ')]
    x_diff, y_diff = [c2-c1 for c1,c2 in zip(*coords)]
    # horizontal lines
    if x_diff == 0:
        row = coords[0][0]
        start = min(coords[0][1], coords[1][1])
        for i in range(abs(y_diff)+1):
            danger_points[(row, start+i)] += 1
    # vertical lines
    if y_diff == 0:
        column = coords[0][1]
        start = min(coords[0][0], coords[1][0])
        for i in range(abs(x_diff)+1):
            danger_points[(start+i, column)] += 1

overlap = sum(val >= 2 for val in danger_points.values())
print(overlap)

# add diagonal lines
for line in open('./day 05/Xavier - Python/input.txt').read().splitlines():
    coords = [list(map(int,c.split(','))) for c in line.split(' -> ')]
    x_diff, y_diff = [c2-c1 for c1,c2 in zip(*coords)]
    if abs(x_diff) == abs(y_diff):
        for i in range(abs(x_diff)+1):
            danger_points[(coords[0][0]+i*x_diff/abs(x_diff), coords[0][1]+i*y_diff/abs(y_diff))] += 1

overlap = sum(val >= 2 for val in danger_points.values())
print(overlap)