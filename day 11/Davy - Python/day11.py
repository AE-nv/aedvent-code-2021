from itertools import takewhile


def get_surrounding_positions(coordinate):
    x, y = coordinate
    return [pos for pos in [(x1, y1) for y1 in range(y-1, y+2) for x1 in range(x-1, x+2)]
            if (pos[0], pos[1]) != (x, y) and (pos[0], pos[1]) in init_octopy.keys()]


def stepper(octopy, steps):
    flash_count = 0
    for step in range(1, steps + 1):
        flashed = []
        octopy = {pos: e + 1 for pos, e in octopy.items()}
        yield octopy.copy(), flash_count, step
        while [e for e in octopy.values() if e > 9]:
            next_flash = [pos for pos, e in octopy.items() if e > 9][0]
            octopy[next_flash] = 0
            flashed.append(next_flash)
            flash_count += 1
            yield octopy.copy(), flash_count, step
            for neighbour in get_surrounding_positions(next_flash):
                if neighbour not in flashed and octopy[neighbour] < 10:
                    octopy[neighbour] = octopy[neighbour] + 1
                    yield octopy.copy(), flash_count, step


init_octopy = {(x, y): int(e) for y, row in enumerate(open('input.txt').read().splitlines()) for x, e in enumerate(row)}
print("Part 1:", [count for octos, count, step in stepper(init_octopy, 100)][-1])

frames = list(takewhile((lambda frame: sum([e for e in frame[0].values()]) > 0), stepper(init_octopy, 1_000_000_000)))
print("Part 2:", frames[-1][2])
