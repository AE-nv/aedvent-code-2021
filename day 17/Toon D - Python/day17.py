x1, x2, y1, y2 = 150, 193, -86, -136


def passes_through(vx, vy):
    nx, ny = 0, 0
    points = []
    while nx <= x2 and ny >= y2:
        nx, ny = nx + vx, ny + vy
        points += [[nx, ny]]
        if vx > 0:
            vx = vx - 1
        vy = vy - 1

        if x1 <= nx and nx <= x2 and y2 <= ny and ny <= y1:
            return 1
    return 0


def max_y(vy):
    return sum(range(0, vy+1))


y_min, y_max = -200, 140
result = [[passes_through(x, y) for x in range(16, 200)]
          for y in range(y_min, y_max)]
[print(str(y + y_min) + ' ' + str(result[y]))
 for y in range(0, abs(y_min) + y_max)]

print(passes_through(17, 135))
print('part 1: %i' % max_y(135))

print('part 2: %i' % sum([sum(y) for y in result]))
