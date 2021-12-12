# PART 1, WORKS FOR ALL EXAMPLES, NOT FOR INPUT...

from collections import defaultdict

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

def find_paths(options, path, node):
    # check if visiting same small cave twice
    if not node=='start' and not node.isupper() and node in path:
        return []
    if not node=='start':
        path += ','
    path += node
    # check if end
    if node == 'end':
        return [path]
    # continue finding paths
    paths = []
    for o in options[node]:
        paths.extend(find_paths(options, path, o))
    return paths

assert len(find_paths(parse('./day 12/Xavier - Python/example.txt'), '', 'start')) == 10
assert len(find_paths(parse('./day 12/Xavier - Python/example2.txt'), '', 'start')) == 19
assert len(find_paths(parse('./day 12/Xavier - Python/example3.txt'), '', 'start')) == 226
print(len(find_paths(parse('./day 12/Xavier - Python/input.txt'), '', 'start')))