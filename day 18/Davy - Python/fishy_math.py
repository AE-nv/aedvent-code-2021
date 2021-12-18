import itertools
import re
from functools import reduce


def find_pair_to_explode(l, depth=0):
    for i, v in enumerate(l):
        if type(v) is list:
            if depth >= 3 and type(v[0]) is int and type(v[1]) is int:
                l[i] = -69
                return v
            else:
                pair = find_pair_to_explode(v, depth + 1)
                if pair:
                    return pair
    return []


def explode(l):
    pair_to_explode = find_pair_to_explode(l, 0)
    if pair_to_explode:
        left, right = str(l).split("-69", maxsplit=1)
        left_nums = re.findall(r'\d+', left)
        if left_nums:
            l0, l1 = left.rsplit(left_nums[-1], maxsplit=1)
            left = l0 + str(int(left_nums[-1]) + pair_to_explode[0]) + l1
        right_nums = re.findall(r'\d+', right)
        if right_nums:
            r0, r1 = right.split(right_nums[0], maxsplit=1)
            right = r0 + str(int(right_nums[0]) + pair_to_explode[1]) + r1
        return eval(left + "0" + right)
    return l


def split(l):
    for i, v in enumerate(l):
        if type(v) is int and v > 9:
            l[i] = [int(v / 2), int(v / 2) + v % 2]
            return True, l
        if type(v) is list:
            was_split, result = split(v)
            if was_split:
                return True, l
    return False, l


def add(a, b):
    l = [eval(str(a)), eval(str(b))]
    while True:
        e = explode(l)
        if e != l:
            l = e
            continue
        is_split, s = split(l.copy())
        if is_split:
            l = s
            continue
        break
    return l


def calc_magnitude(l):
    a = l[0] if type(l[0]) is int else calc_magnitude(l[0])
    b = l[1] if type(l[1]) is int else calc_magnitude(l[1])
    return 3 * a + 2 * b


numbers = [line for line in open('input.txt').read().splitlines()]
print("Part 1:", calc_magnitude(reduce(lambda a, b: add(a, b), numbers)))
print("Part 2:", max([calc_magnitude(add(*n)) for n in itertools.product(numbers, numbers) if n[0] != n[1]]))

assert explode([[3, [2, [1, [7, 3]]]], [6, [5, [4, [3, 2]]]]]) == [[3, [2, [8, 0]]], [9, [5, [4, [3, 2]]]]]
assert split([[[[0, 7], 4], [15, [0, 13]]], [1, 1]])[1] == [[[[0, 7], 4], [[7, 8], [0, 13]]], [1, 1]]
assert add([[[[4, 3], 4], 4], [7, [[8, 4], 9]]], [1, 1]) == [[[[0, 7], 4], [[7, 8], [6, 0]]], [8, 1]]
assert calc_magnitude([[[[8, 7], [7, 7]], [[8, 6], [7, 7]]], [[[0, 7], [6, 6]], [8, 7]]]) == 3488
