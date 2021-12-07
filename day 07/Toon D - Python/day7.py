file = open("day 07/Toon D - Python/input", "r")
numbers = [int(x) for x in file.readline().split(',')]
numbers.sort()

def calculate_fuel(numbers, point):
  return sum([abs(x - point) for x in numbers])

fuel = calculate_fuel(numbers, numbers[int(round(len(numbers)/2))])
print("part 1: %i" % fuel)

def calculate_new_fuel(numbers,point):
  return sum([sum(range(0,abs(x-point)+1)) for x in numbers])

new_fuel = calculate_new_fuel(numbers, int(round(sum(numbers)/len(numbers)))-1)
print("part 2: %i" % new_fuel)
