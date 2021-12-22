import numpy as np
import functools

def parseLine(l):
    command, args = l.strip().split(' ')
    x, y, z = [[int(bound) for bound in arg.split('=')[1].split('..')] for arg in args.split(',')]
    return (command, (x, y, z))

def inBounds(pt, bounds):
    for i,c in enumerate(pt):
        bound=bounds[i]
        if c> bound[1] or c<bound[0]:
            return False
    return True

def noOverlap(bound1, bound2):
    return bound2[0][0]>bound1[0][1] or bound1[0][0]>bound2[0][1] \
           or bound2[1][0]>bound1[1][1] or bound1[1][0]>bound2[1][1] \
           or bound2[2][0]>bound1[2][1] or bound1[2][0]>bound2[2][1]

def overlap(bound1, bound2):
    return not noOverlap(bound1, bound2)

def bruteForce(instructions):
    xLower = min([i[1][0][0] for i in instructions])
    xUpper = max([i[1][0][1] for i in instructions])

    yLower = min([i[1][1][0] for i in instructions])
    yUpper = max([i[1][1][1] for i in instructions])

    zLower = min([i[1][2][0] for i in instructions])
    zUpper = max([i[1][2][1] for i in instructions])

    count=0
    pixels=abs(xUpper-xLower)*abs(yUpper-yLower)*abs(zUpper-zLower)
    finished=0
    for x in range(xLower, xUpper+1):
        for y in range(yLower, yUpper+1):
            for z in range(zLower, zUpper+1):
                on=False
                for command, bounds in instructions:
                    if inBounds([x,y,z], bounds):
                        if command=='on':
                            on=True
                        else:
                            on=False
                if on:
                    count+=1
                finished+=1
                print(finished, "/", pixels)
    return count

def setCuboid(core, bounds, set):
    for x in range(bounds[0][0], bounds[0][1]+1):
        for y in range(bounds[1][0], bounds[1][1] + 1):
            for z in range(bounds[2][0], bounds[2][1] + 1):
                core[str((x,y,z))]=set
    return core

def countUsingDict(instructions):
    core={}
    i = 1
    for command, bounds in instructions:
        set = 0
        if command == 'on':
            set = 1
        core = setCuboid(core, bounds, set)
        print("completed instruction ", i)
        i += 1
    return core

def sizeOfCuboid(instruction):
    return abs(instruction[1][0][0]-instruction[1][0][1])*abs(instruction[1][1][0]-instruction[1][1][1])*abs(instruction[1][2][0]-instruction[1][2][1])

def getOverlap(b1, b2):
    if b1[0]>b2[0]: return getOverlap(b2,b1)
    return b2[1]-b1[1]+1 #include both edges

def countUsingNp(instructions):
    xLower = min([i[1][0][0] for i in instructions])
    xUpper = max([i[1][0][1] for i in instructions])

    yLower = min([i[1][1][0] for i in instructions])
    yUpper = max([i[1][1][1] for i in instructions])

    zLower = min([i[1][2][0] for i in instructions])
    zUpper = max([i[1][2][1] for i in instructions])

    subCore=np.full((xUpper-xLower+1, yUpper-yLower+1, zUpper-zLower+1), False)
    offsetInstructions=[]
    for command, bounds in instructions:
        offsetInstructions.append((command,(bounds[0][0]-xLower, bounds[0][1]-xLower), (bounds[1][0]-yLower, bounds[1][1]-yLower), (bounds[2][0]-zLower, bounds[2][1]-zLower)))

    for command, bx, by, bz in offsetInstructions:
        filler=False
        if command=='on':
            filler=True
        new=np.full((bx[1]-bx[0]+1,by[1]-by[0]+1,bz[1]-bz[0]+1), filler)
        subCore[bx[0]:bx[1]+1,by[0]:by[1]+1,bz[0]:bz[1]+1]=new
    return subCore

def cuboidVolume(bx, by, bz):
    return abs(bx[1]-bx[0]+1)*abs(by[1]-by[0]+1)*abs(bz[1]-bz[0]+1)

def getOverlappingCuboid(cuboid1,cuboid2):
    lowerX, upperX = max(cuboid1[0][0], cuboid2[0][0]), min(cuboid1[0][1], cuboid2[0][1])
    lowerY, upperY = max(cuboid1[1][0], cuboid2[1][0]), min(cuboid1[1][1], cuboid2[1][1])
    lowerZ, upperZ = max(cuboid1[2][0], cuboid2[2][0]), min(cuboid1[2][1], cuboid2[2][1])
    if upperX>=lowerX and upperY>=lowerY and upperZ>=lowerZ:
        return True, (lowerX, upperX),(lowerY, upperY),(lowerZ, upperZ)
    else:
        return False, 0, 0, 0

def getOverlappingCuboidVolumeWithGroup(cuboid, cuboids):
    volume=0
    for i,c in enumerate(cuboids):
        hasOverlap, bx, by, bz = getOverlappingCuboid(cuboid, c)
        if hasOverlap:
            volume+=cuboidVolume(bx, by, bz) - getOverlappingCuboidVolumeWithGroup((bx, by, bz), cuboids[i+1:])
    return volume

def countByOverlap(instructions):
    processed = []
    volume = 0
    for command, bounds in reversed(instructions):
        if command == 'on':
            volume += cuboidVolume(bounds[0], bounds[1], bounds[2]) - getOverlappingCuboidVolumeWithGroup(bounds, processed)
            # also subtracting the volume of the off cubes that occur after an on cube in the set of instructions should discount the volume of cubes that are turned off
            # The order of instructions matters in that the off cube only turns of the cubes that were turned on before it
            # Reversing the order of instructions means I can ignore the off cubes adn just subtract their volume from all the cubes that come before it
            # This feels like alot of assumptions, but it makes sense
            # This would probably break if the instructions ended on off
        processed.append(bounds)
    return volume

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    instructions = [parseLine(l) for l in data]
    print(instructions)

    print(countByOverlap(instructions))

    overlaps=[[instructions[0]]]
    for instruction in instructions[1:]:
        found = False
        for i, group in enumerate(overlaps):
            for command, bound in group:
                if overlap(bound, instruction[1]):
                    found=True
                    break
            if found:
                break
        if found:
            overlaps[i].append(instruction)
        else:
            overlaps.append([instruction])

    counts=[]
    for i, overlappingCuboids in enumerate(overlaps):
        print(overlappingCuboids)
        if len(overlappingCuboids)==1 :
            if overlappingCuboids[0][0]=='on':
                counts.append(sizeOfCuboid(overlappingCuboids[0]))
        else:
            counts.append(countByOverlap(overlappingCuboids))
            #counts.append(sum(countUsingDict(overlappingCuboids).values()))
            #counts.append(bruteForce(overlappingCuboids))
        print("finished overlaps: ",i,"/",len(overlaps))
    print(sum(counts)) #TODO: fix this for optimization sake


