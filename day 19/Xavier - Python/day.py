# code to permutate a beacon copied from stackoverflow
def roll(b): return (b[0],b[2],-b[1])
def turn(b): return (-b[1],b[0],b[2])
def permutate(b):
    '''Returns permutations for a beacon'''
    permutations = []
    for _ in range(2):
        for _ in range(3):
            b = roll(b)
            permutations.append(b)
            for _ in range(3):
                b = turn(b)
                permutations.append(b)
        b = roll(turn(roll(b)))
    return permutations

def are_neighbours(beacons1, beacons2):
    if len(set(beacons1).intersection(set(beacons2))) >= 12:
        return True
    return False

def move_beacons(beacons, x, y, z):
    '''Move to different reference point'''
    return [(b[0]+x,b[1]+y,b[2]+z) for b in beacons]

# parse input
input = [l.split("\n")[1:] for l in open('input.txt').read().split('\n\n')]
lost = dict()
for i, s in enumerate(input):
    relative_positions = []
    for b in s:
        x,y,z = b.split(',')
        relative_positions.append((int(x),int(y),int(z)))
    lost[i] = relative_positions

# assume scanner 0 is "correct" orientation
found = {0: lost.pop(0)}
scanner_positions = set()
scanner_positions.add((0,0,0))

# array containing scanners that need to be checked for neighbours
to_check = [0]

while len(lost) > 0:
    print('Searching', len(lost), 'lost scanners', end='\r')

    new_found = dict()

    for scanner in to_check:
        found_beacons = found[scanner]
        # check if scanner in lost is neighbour
        for s in lost:
            # check all possible permutations of the relative beacon positions
            beacons_permutations = list(zip(*[permutate(beacon) for beacon in lost[s]]))
            for lost_beacons in beacons_permutations:
                # for each permutation check if each beacon might be each other one in the found list
                for lost_beacon in lost_beacons:
                    for found_beacon in found_beacons:
                        scanner_position = (found_beacon[0]-lost_beacon[0],found_beacon[1]-lost_beacon[1],found_beacon[2]-lost_beacon[2])
                        moved_beacons = move_beacons(lost_beacons, *scanner_position)
                        if are_neighbours(found_beacons, moved_beacons):
                            new_found[s] = moved_beacons
                            found[s] = moved_beacons
                            scanner_positions.add(scanner_position)
                            break
                    else:
                        continue
                    break
                else:
                    continue
                break        
    # newly found scanners are the ones to be checked next
    to_check = new_found.keys()
    # remove found ones from the lost dictionary
    for scanner in new_found:
        lost.pop(scanner)

print("Found all scanner & beacon positions")

# merge beacons
beacons = set()
for v in found.values():
    for beacon in v:
        beacons.add(beacon)

print(len(beacons))

# find max distance between scanners
max_distance = 0
positions = list(scanner_positions)
for i, scanner in enumerate(positions):
    for s2 in positions[i+1:]:
        max_distance = max(max_distance, sum([abs(scanner[x]-s2[x]) for x in range(3)]))

print(max_distance)