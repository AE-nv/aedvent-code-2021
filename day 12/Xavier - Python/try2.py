from collections import defaultdict, Counter

def parse(file):
    options = defaultdict(list)
    for c in [l.split('-') for l in open(file).read().splitlines()]:
        if not c[1] == 'start':
            options[c[0]].append(c[1])
        if not c[0] == 'start':
            options[c[1]].append(c[0])
    for v in options.values():
        v.sort()
    return options

def possible_paths(options, start, finish, max_small_occurence):
    found = []
    paths = [(start,o) for o in options[start]]
    while len(paths)> 0:
        new_paths = []
        for p in paths:
            if p[-1] == finish:
                found.append(p)
            else:
                possibilities = [(*p, o) for o in options[p[-1]]]
                for path in possibilities:
                    # check if no small cave occurs more than allowed
                    c = Counter([x for x in path if not x.isupper()])
                    # only one small can occur max_small_occurences times
                    if max(c.values())<=max_small_occurence and len([x for x in c.keys() if c[x]>1])<=1:
                        new_paths.append(path)
        paths = new_paths
    return found

print(len(possible_paths(parse('./day 12/Xavier - Python/input.txt'), 'start', 'end', 1)))
print(len(possible_paths(parse('./day 12/Xavier - Python/input.txt'), 'start', 'end', 2)))