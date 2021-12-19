import json
from math import ceil, floor

def split(num):
    '''
    Returns the result and a boolean indicating whether a split occured
    '''
    if type(num) is int:
        if num>=10:
            return [floor(num/2),ceil(num/2)], True
        return num, False
    else:
        left, splitted = split(num[0])
        if splitted:
            return [left, num[1]], True
        right, splitted = split(num[1])
        if splitted:
            return [left, right], True
        return [left, right], False

def add_leftmost(pair, num):
    if type(pair[0]) is int:
        return [pair[0]+num, pair[1]]
    return [add_leftmost(pair[0], num), pair[1]]

def add_rightmost(pair, num):
    if type(pair[1]) is int:
        return [pair[0], pair[1]+num]
    return [pair[0], add_rightmost(pair[1], num)]

def explode(num, depth):
    if type(num) is int:
        return 0, 0, num, False
    else:
        if depth == 4:
            return num[0], num[1], 0, True
        else:
            left_add, right_add, left, exploded = explode(num[0], depth+1)
            if exploded:
                if type(num[1]) is int:
                    return left_add, 0, [left, num[1]+right_add], True
                else:
                    return left_add, 0, [left, add_leftmost(num[1], right_add)], True
            left_add, right_add, right, exploded = explode(num[1], depth+1)
            if exploded:
                if type(num[0]) is int:
                    return 0, right_add, [num[0]+left_add, right], True
                else:
                    return 0, right_add, [add_rightmost(num[0], left_add), right], True
            return 0, 0, num, False

def snail_add(n1, n2):
    result = [n1,n2]
    splitted = True
    while splitted:
        exploded = True
        while exploded:
            _, _, result, exploded = explode(result, 0)
        result, splitted = split(result)
    return result

def magnitude(number):
    if type(number) is list:
        return 3*magnitude(number[0])+2*magnitude(number[1])
    return number

homework = [json.loads(number) for number in open('input.txt').read().splitlines()]
result = homework[0]
for i in range(1,len(homework)):
    result = snail_add(result, homework[i])
print(magnitude(result))

# find max possible magnitude of sum of homework numbers
max_magnitude = 0
for i in range(len(homework)):
    for number in homework[i+1:]:
        max_magnitude = max(magnitude(snail_add(homework[i],number)), max_magnitude)

print(max_magnitude)