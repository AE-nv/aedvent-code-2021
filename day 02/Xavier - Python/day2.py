instructions = [(i.split(' ')[0], int(i.split(' ')[1])) for i in open('input.txt').readlines()]

from collections import defaultdict
totals = defaultdict(int)
for i in instructions:
    totals[i[0]] += i[1]
print(totals['forward']*(totals['down']-totals['up']))

forward = down = aim = 0
for i in instructions:
    if i[0] == 'forward':
        forward += i[1]
        down += aim*i[1]
    elif i[0] == 'down':
        aim += i[1]
    else:
        aim -= i[1]
print(forward * down)