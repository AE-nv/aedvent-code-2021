file = open("day 11/Toon D - Python/input", "r")
octopi = [[int(octo) for octo in row.replace('\n', '')]
          for row in file.readlines()]


def add_to_surrounding(octopi, x, y):
    for dx, dy in [(dx, dy) for dx in [-1, 0, 1] for dy in [-1, 0, 1]]:
        new_x = x + dx
        new_y = y + dy
        if new_x >= 0 and new_x < len(octopi[0]) and new_y >= 0 and new_y < len(octopi) and (new_x, new_y) != (x, y):
            if octopi[new_y][new_x] != 0:
                octopi[new_y][new_x] += 1
    return octopi


flashes = 0
synchronized = False


def day(octopi):
    global flashes
    global synchronized
    add_one = [[octo + 1 for octo in row] for row in octopi]
    flashed = True
    while flashed:
        flashed = False
        for y in range(len(add_one)):
            for x in range(len(add_one[0])):
                if add_one[y][x] > 9:
                    add_one[y][x] = 0
                    flashed = True
                    add_one = add_to_surrounding(add_one, x, y)
    flashes_this_day = sum(
        [1 if octo == 0 else 0 for row in add_one for octo in row])
    if flashes_this_day == (len(add_one) * len(add_one[0])):
        synchronized = True
    flashes += flashes_this_day
    return add_one


next_day = octopi
for i in range(0, 100):
    next_day = day(next_day)
print("day 1: %i" % flashes)

counter = 0
while not synchronized:
    counter += 1
    octopi = day(octopi)

print("part 2: %i" % counter)
