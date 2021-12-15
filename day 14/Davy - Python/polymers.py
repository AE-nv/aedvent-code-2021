from collections import defaultdict, Counter

template, _, *rule_input = open('input.txt').read().splitlines()
rules = {r[0]: r[1] for r in [rule.split(' -> ') for rule in rule_input]}


def expand_pairs(pairs, depth):
    new_pairs = defaultdict(int)
    for a, c, b, parent_count in [(pair[0], rules[pair], pair[1], pairs[pair]) for pair in pairs.keys()]:
        new_pairs[a + c] += parent_count
        new_pairs[c + b] += parent_count
        yield c, parent_count
    if depth > 1:
        yield from expand_pairs(new_pairs, depth - 1)


def expand(polymer, depth):
    for e in polymer:
        yield e, 1
    pairs = Counter([polymer[i] + polymer[i + 1] for i in range(len(polymer) - 1)])
    yield from expand_pairs(pairs, depth)


def count_letters(polymer, depth):
    counts = defaultdict(int)
    for letter, count in expand(polymer, depth):
        counts[letter] += count
    return max(counts.values()) - min(counts.values())


print("Part 1:", count_letters(template, 10))
print("Part 2:", count_letters(template, 40))
