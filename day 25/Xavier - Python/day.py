lines = open('input.txt').read().splitlines()

gridsize = (len(lines), len(lines[0]))

east = set()
south = set()

for x, line in enumerate(lines):
    for y, position in enumerate(line):
        if position == '>':
            east.add((x,y))
        elif position == 'v':
            south.add((x,y))

steps = 0
moved = True
while moved == True:
    moved = False
    steps += 1

    new_east = set()
    new_south = set()

    for cucumber in east:
        next_position = (cucumber[0], (cucumber[1]+1)%gridsize[1])
        if next_position in east or next_position in south:
            new_east.add(cucumber)
        else:
            moved = True
            new_east.add(next_position)
    
    east = new_east

    for cucumber in south:
        next_position = ((cucumber[0]+1)%gridsize[0], cucumber[1])
        if next_position in east or next_position in south:
            new_south.add(cucumber)
        else:
            moved = True
            new_south.add(next_position)

    south = new_south

print(steps)
    