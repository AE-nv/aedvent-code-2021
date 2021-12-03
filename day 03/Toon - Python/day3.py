file = open("day 03/Toon - Python/input", "r")
values = [[x == '1' for x in list(line)[:-1]] for line in file.readlines()]

def list_to_int(bools):
  return int(''.join(map(str, map(int, bools))), 2)

gamma_list = [[value[i] for value in values] for i in range(len(values[0]))]
gamma_binary = [1 if sum(row) > len(row)/2 else 0 for row in gamma_list]
gamma = int("".join(str(x) for x in gamma_binary), 2)

epsilon = int("".join("1" if x == 0 else "0" for x in gamma_binary), 2)

power_consumption = gamma * epsilon
print("Part 1: %s" % power_consumption)

def filter(values, position, oxygen):
  if len(values) == 1:
    return values[0]
  if sum([value[position] for value in values]) >= len(values)/2:
    if oxygen:
      return filter([value for value in values if value[position]],position+1, oxygen)
    else: 
      return filter([value for value in values if not(value[position])],position+1, oxygen)
  else:
    if oxygen:
      return filter([value for value in values if not(value[position])],position+1, oxygen)
    else: 
      return filter([value for value in values if value[position]],position+1, oxygen)


oxygen=list_to_int(filter(values, 0, True))
scrubber=list_to_int(filter(values, 0, False))
life = oxygen * scrubber
print("Part 2: %s" % life)
