from functools import reduce


def count_increments(values):
    return reduce((lambda acc, x: (x, (acc[1] + 1) if x > acc[0] else acc[1])), values, (0, -1))[1]


inputs = list(map(lambda s: int(s), open('input.txt', 'r').readlines()))
print("Part 1: ", count_increments(inputs))


zipped_inputs = list(zip(inputs, inputs[1:], inputs[2:]))
summed_inputs = list(map(lambda i: sum(i), zipped_inputs))
print("Part 2: ", count_increments(summed_inputs))
