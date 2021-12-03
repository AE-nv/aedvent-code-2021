from copy import deepcopy

numbers = [list(x) for x in open('./day 03/Xavier - Python/input.txt').read().splitlines()]

def most_common_bit(l):
    if l.count('1') >= len(l)/2:
        return '1'
    return '0'

transpose = list(zip(*numbers))
gamma = ''.join([most_common_bit(x) for x in transpose])
epsilon = ''.join(['1' if x is '0' else '0' for x in gamma])
print(int(gamma, 2)*int(epsilon, 2))

o2_candidates = deepcopy(numbers)
co2_candidates = deepcopy(numbers)
for i in range(len(gamma)):
    if(len(o2_candidates) > 1):
        o2_transpose = list(zip(*o2_candidates))
        o2_candidates = [x for x in o2_candidates if x[i] == most_common_bit(o2_transpose[i])]
    if(len(co2_candidates) > 1):
        co2_transpose = list(zip(*co2_candidates))
        co2_candidates = [x for x in co2_candidates if not x[i] is most_common_bit(co2_transpose[i])]
print(int(''.join(o2_candidates[0]), 2)*int(''.join(co2_candidates[0]), 2))