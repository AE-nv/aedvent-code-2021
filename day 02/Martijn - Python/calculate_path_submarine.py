# instructions_parsed = [[instruction.split(' ')[0], int(instruction.split(' ')[1])] for instruction in open('day 02\Martijn - Python\input.txt').readlines()]
instructions_parsed = [[instruction, int(nb)] for [instruction,nb] in [line.split() for line in open('day 02\Martijn - Python\input.txt').readlines()]] #slightly more clean solution of Toon

horizontal_pos = 0
depth = 0
aim = 0
for instruction in instructions_parsed:
    command = instruction[0]
    number = instruction[1]
    if command == 'forward':
        horizontal_pos += number
        depth += (aim * number)
    elif command == 'up':
        aim -= number
    elif command == 'down':
        aim += number
    else:
        print('unknown command')
print(horizontal_pos, depth, horizontal_pos * depth)