file = open("Input.txt", "r")
commands = map(lambda splitLine: [splitLine[0], int(splitLine[1])], map(lambda line: line.split(" "), file.readlines()))

depth = 0
distance = 0
aim = 0

for (cmd,nb) in commands:
    if (cmd == 'forward'):
        distance += nb
        depth += nb*aim
    elif (cmd == 'down'):
        aim += nb
    else:
        aim -= nb

print("Distance: {}, Depth: {}, Result: {}".format(distance, depth, distance*depth))
