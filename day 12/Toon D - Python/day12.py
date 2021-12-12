from collections import defaultdict

file = open("day 12/Toon D - Python/input", "r")

lines = [line.replace('\n', '').split('-') for line in file.readlines()]

neighbours = defaultdict(list)

for line in lines:
    neighbours[line[0]] += [line[1]]
    neighbours[line[1]] += [line[0]]

for key in neighbours.keys():
    try:
        neighbours[key].remove('start')
    except ValueError:
        pass


def find_routes(current_cave, current_route):
    global neighbours
    route = current_route + [current_cave]
    if current_cave == 'end':
        return [route]
    result = []
    for neighbour in neighbours[current_cave]:
        if neighbour not in route or neighbour.isupper():
            result.extend(find_routes(neighbour, route))
    return result


result = find_routes('start', [])

print("part 1: %i" % len(result))


def find_routes_twice(current_cave, current_route, visited_twice):
    global neighbours
    route = current_route + [current_cave]
    if current_cave == 'end':
        return [route]
    result = []
    for neighbour in neighbours[current_cave]:
        if visited_twice:
            if neighbour not in route or neighbour.isupper():
                result.extend(find_routes_twice(neighbour, route, True))
        else:
            if neighbour in route and neighbour.islower():
                result.extend(find_routes_twice(neighbour, route, True))
            else:
                result.extend(find_routes_twice(neighbour, route, False))
    return result


result = find_routes_twice('start', [], False)

print("part 2: %i" % len(result))
