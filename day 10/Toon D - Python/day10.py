from os import truncate


file = open("day 10/Toon D - Python/input", "r")
lines = [line.replace('\n', '') for line in file.readlines()]

pairs = {'(': ')', '[': ']', '{': '}', '<': '>'}
points = {')': 3, ']': 57, '}': 1197, '>': 25137}

incomplete_lines = []

total_points = 0
for line in lines:
    stack = []
    corrupt = False
    for char in line:
        if char in pairs.keys():
            stack.append(char)
        else:
            if pairs[stack.pop()] != char:
                total_points += points[char]
                corrupt = True
                break

    if not corrupt:
        incomplete_lines += [line]

print("part 1: %i" % total_points)

complete_points = {')': 1, ']': 2, '}': 3, '>': 4}

points = []
for line in incomplete_lines:
    stack = []
    for char in line:
        if char in pairs.keys():
            stack.append(char)
        else:
            stack.pop()

    score = 0
    while len(stack) > 0:
        score = score * 5 + complete_points[pairs[stack.pop()]]
    points += [score]

points = sorted(points)

print("part 2: %i" % points[len(points)//2])
