file = open("day 01/Toon - python/input.txt", "r")
values = [int(x) for x in file.readlines()]
previous = None
increased = 0

for value in values:
    if previous == None:
        previous = value
    elif value > previous:
        increased += 1
    previous = value

print("Part1: increases = %s" % increased)

increased = 0

for i in range(3,len(values)):
  previous = values[i-3] + values[i-2] + values[i-1]
  current = values[i-2] + values[i-1] + values[i]
  if current > previous:
    increased+=1

print("Part2: increases = %s" % increased)

print("Part1: increases = %s" % sum([values[i] < values[i+1] for i in range(0,len(values)-1)]))

print("Part2: increases = %s" % sum([sum(values[i:i+3]) < sum(values[i+1:i+4]) for i in range(0, len(values)-3)]))
