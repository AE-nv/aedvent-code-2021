file = open("day 02/Toon - Python/input", "r")
values = [[x, int(y)] for [x,y] in [line.split() for line in file.readlines()]]
x = 0
y = 0
for value in values:
  if value[0] == "forward":
    x += value[1]
  elif value[0] == "down":
    y += value[1]
  elif value[0] == "up":
    y -= value[1]

print("part1: %s" % (x*y))

x = 0
y = 0
aim = 0
for value in values:
  if value[0] == "forward":
    x += value[1]
    y += aim * value[1]
  elif value[0] == "down":
    aim += value[1]
  elif value[0] == "up":
    aim -= value[1]

print("part1: %s" % (x*y))
