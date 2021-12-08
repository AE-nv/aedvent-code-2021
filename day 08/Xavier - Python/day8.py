def parse_input(file):
    return [[i.split() for i in x.split('|')] for x in open(file).read().splitlines()]

example = parse_input('./day 08/Xavier - Python/example.txt')
input = parse_input('./day 08/Xavier - Python/input.txt')

def part_one(input):
    return sum([sum([1 for pattern in line[1] if len(pattern) in [2,3,4,7]]) for line in input])

assert part_one(example) == 26
print(part_one(input))

def find_value(line):
    patterns, output = line
    patterns = sorted(patterns, key=len)
    mapping = {'1': set(patterns[0]), '7': set(patterns[1]), '4': set(patterns[2]), '8': set(patterns[-1])}
    # digits with 5 segments
    for p in patterns[3:6]:
        if len(mapping['1']-set(p)) == 0:
            mapping['3'] = set(p)
        elif len(set(p)-mapping['4']) == 2:
            mapping['5'] = set(p)
        else:
            mapping['2'] = set(p)
    # digits with 6 segments
    for p in patterns[6:9]:
        if len(mapping['7']-set(p)) == 1:
            mapping['6'] = set(p)
        elif len(mapping['4']-set(p)) == 0:
            mapping['9'] = set(p)
        else:
            mapping['0'] = set(p)
    value = ''
    for digit in output:
        value += [key for key in mapping if mapping[key] == set(digit)][0]
    return int(value)
    

def part_two(input):
    values = [find_value(line) for line in input]
    return sum(values)

assert part_two(example) == 61229
print(part_two(input))
