caves = {}
for a, b in [line.split('-') for line in open('input.txt').read().splitlines()]:
    caves[a] = caves[a] + [b] if a in caves else [b]
    caves[b] = caves[b] + [a] if b in caves else [a]


def get_paths(current_path, can_visit):
    if current_path[-1] == 'end':
        return [current_path]
    paths = []
    for cave in caves[current_path[-1]]:
        if can_visit(cave, current_path):
            paths += get_paths(current_path + [cave], can_visit)
    return paths


def can_vist_cave(c, p):
    return c != 'start' and (c != c.lower() or c not in p or not [c1 for c1 in p if c1.lower() == c1 and p.count(c1) == 2])


print("Part 1:", len(get_paths(['start'], lambda c, p: c != c.lower() or c not in p)))
print("Part 2:", len(get_paths(['start'], can_vist_cave)))
