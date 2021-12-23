def overlap(first, second):
    '''Returns region with overlap'''
    overlapping = []
    for i in range(3):
        if second[i][1] >= first[i][0] and second[i][0] <= first[i][1]:
            overlapping.append([max(first[i][0], second[i][0]), min(first[i][1], second[i][1])])
        else:
            return None
    return overlapping

def restrict(ranges):
    '''Returns part thats inside [-50,50]'''
    return overlap(ranges, [[-50,50],[-50,50],[-50,50]])

def reboot(input, init):
    cuboids = []
    for cuboid in input:
        status, ranges = cuboid.split(' ')
        ranges = [[int(x) for x in r[2:].split('..')] for r in ranges.split(',')]
        # if initializing, restrict to [-50,50]
        if init:
            restricted = restrict(ranges)
            if not restricted:
                # out of bounds
                continue
        # turn off the intersection of new cuboid with whats on at the moment
        new_cuboids = []
        for r in cuboids:
            new_cuboids.append(r)
            intersection = overlap(ranges, r[1])
            if intersection:
                if r[0] == '-':
                    new_cuboids.append(['+', intersection])
                else:
                    new_cuboids.append(['-', intersection])
        # if new cuboid is on => turn it on
        if status == 'on':
            new_cuboids.append(['+', ranges])
        cuboids = new_cuboids
    # return cuboids and their status
    return cuboids

def calculate_on(cuboids):
    on = 0
    for cuboid in cuboids:
        # calculate volume
        volume = 1
        for dimension in cuboid[1]:
            volume *= (dimension[1]-dimension[0]+1)
        # add if on, otherwise substract
        if cuboid[0] == '+':
            on += volume
        else:
            on -= volume
    return on

input = open('input.txt').read().splitlines()

print('Part one', calculate_on(reboot(input, True)))
print('Part two', calculate_on(reboot(input, False)))
