import sys

def solve(lines):
  horizontal_position = 0
  depth = 0

  for line in lines:
    split = line.split()
    direction = split[0]
    how_much = int(split[1])
	
    if direction == "forward":
        horizontal_position += how_much
    elif direction == "down":
        depth += how_much
    elif direction == "up":
        depth -= how_much
	
  return horizontal_position * depth

with open('input.txt') as file:
  result = solve(file.readlines())
  print(result)
