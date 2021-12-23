from functools import lru_cache
from copy import deepcopy

costs = {'A': 1, 'B': 10, 'C': 100, 'D': 1000}
order = ['A', 'B', 'C', 'D']
DEBUG = False

def parse(file):
    input = open(file).read().splitlines()
    hallway = list(input[1][1:-1])
    rooms = [[],[],[],[]]
    for line in input[2:-1]:
        for i in range(4):
            rooms[i].append(line[3+2*i])
    return rooms, hallway

def done(rooms):
    '''Return whether rooms are filled with correct things'''
    for char in order:
        room = rooms[order.index(char)]
        if not set(room) == set([char]):
            return False
    return True

def can_enter_room(char, room):
    '''Returns whether entry in the room is possible and at what depth'''
    if not set(room).issubset(set(['.', char])):
        return False, -1
    return True, len(room)-1-list(reversed(room)).index('.')

def hallway_free(hallway, position, goal):
    if position < goal:
        for i in range(position+1, goal+1):
            if not hallway[i] == '.':
                return False, 0
        return True, goal-position
    else:
        for i in range(goal, position):
            if not hallway[i] == '.':
                return False, 0
        return True, position-goal

def should_remove_from_room(index, room):
    # check if only correct chars or .'s
    if set(room).issubset(set([order[index], '.'])):
        return False
    # if still other characters => moving possible
    return True

def move(rooms, hallway, depth):
    # if done no more cost needed
    if done(rooms):
        if DEBUG:
            print(''.join(['\t' for i in range(depth)]),'done')
        return 0
    # check if move from hallway to room is possible (is always optimal)
    for x, h in enumerate(hallway):
        if h == '.':
            continue
        index = order.index(h)
        room = rooms[index]
        # check if possible to move into room
        available, position = can_enter_room(h, room)
        if not available:
            continue
        # check if hallway is free to move
        room_x = 2+index*2
        free, length = hallway_free(hallway, x, room_x)
        if not free:
            continue
        # move
        if DEBUG:
            print(''.join(['\t' for i in range(depth)]),'moving', h, 'from hallway to room', index, ': cost', costs[h]*(position+1+length))
        room[position] = h
        hallway[x] = '.'
        return costs[h]*(position+1+length) + move(rooms, hallway, depth+1)
    # try all moves from rooms to hallways and return minimum final score
    scores = [float('inf')] # (assume impossible)
    for index, room in enumerate(rooms):
        # if removing doesnt make sense dont do it
        if not should_remove_from_room(index, room):
            continue
        # move first out of room
        new_rooms = deepcopy(rooms)
        new_room, position, char = [], -1, ''
        for i in range(len(room)):
            if position < 0 and room[i] != '.':
                position = i
                char = room[i]
                new_room.append('.')
            else:
                new_room.append(room[i])
        new_rooms[index] = new_room
        # put in all possible positions in hallway
        for x in range(len(hallway)):
            # cannot go above a room
            if x in [2, 4, 6, 8]:
                continue
            # check if path is free
            free, length = hallway_free(hallway, 2+index*2, x)
            if free:
                new_hallway = [hallway[i] if i != x else char for i in range(len(hallway))]
                if DEBUG:
                    print(''.join(['\t' for i in range(depth)]),'moving', char, 'from room', index, 'to hallway at position', x, ': cost', costs[char]*(position+1+length))
                score = costs[char]*(position+1+length) + move(deepcopy(new_rooms), new_hallway, depth+1)
                scores.append(score)
    if DEBUG:
        print(''.join(['\t' for i in range(depth)]),scores)
    return min(scores)

assert move([['A', 'A'], ['B','B'], ['C','C'], ['D','D']], ['.','.','.','.','.','.','.','.','.','.','.',], 0) == 0
assert move([['.', '.'], ['.','B'], ['C','C'], ['D','D']], ['A','A','.','.','.','B','.','.','.','.','.',], 0) == 26

rooms, hallway = parse('input.txt')
print(move(rooms, hallway, 0))

rooms, hallway = parse('input2.txt')
print(move(rooms, hallway, 0))