import numpy as np

def parseLine(l):
    command, args = l.strip().split(' ')
    x, y, z = [[int(bound)+50 for bound in arg.split('=')[1].split('..')] for arg in args.split(',')]
    return (command, (x, y, z))

def setCuboid(core, bounds, set):
    for x in range(bounds[0][0], bounds[0][1]+1):
        if x>=0 and x<=100:
            for y in range(bounds[1][0], bounds[1][1] + 1):
                if y >= 0 and y <= 100:
                    for z in range(bounds[2][0], bounds[2][1] + 1):
                        if z >= 0 and z <= 100:
                            core[x,y,z]=set
    return core

def coreInBounds(bounds):
    for lower, upper in bounds:
        if upper<0 or lower>100:
            return False
    return True

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    instructions = [parseLine(l) for l in data]
    print(instructions)

    core = np.zeros((101, 101, 101))
    i=1
    for command, bounds in instructions:
        if coreInBounds(bounds):
            set=0
            if command=='on':
                set=1
            core = setCuboid(core, bounds, set)
        print("completed instruction ",i)
        i+=1
    print(np.sum(core))