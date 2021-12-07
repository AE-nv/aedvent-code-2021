example = [int(age) for age in open('./day 07/Xavier - Python/example.txt').readline().strip().split(',')]
input = [int(age) for age in open('./day 07/Xavier - Python/input.txt').readline().strip().split(',')]

def part_one(positions):
    fuels = [sum([abs(i-x) for x in positions]) for i in range(min(positions), max(positions))]
    return min(fuels)

assert part_one(example) == 37
print(part_one(input))

def part_two(positions):
    fuels = [sum([sum(range(1, abs(i-x)+1)) for x in positions]) for i in range(min(positions), max(positions))]
    return min(fuels)

assert part_two(example) == 168
print(part_two(input))

