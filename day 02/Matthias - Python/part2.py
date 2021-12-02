import sys

def day1(lines):
  horizontal_position = 0
  depth = 0
  aim = 0

  for line in lines:
    split = line.split()
    direction = split[0]
    how_much = int(split[1])
	
    if direction == "forward":
        horizontal_position += how_much
        depth += aim * how_much
    elif direction == "down":
        aim += how_much
    elif direction == "up":
        aim -= how_much
	
  return horizontal_position * depth

with open('input.txt') as file:
  result = day1(file.readlines())
  print(result)
