from collections import defaultdict

template, _, *rules = open('input.txt').read().splitlines()

rulebook = {}
for rule in rules:
    key, val = rule.split(' -> ')
    rulebook[key] = [key[0]+val, val+key[1]]

def step(pairs, rules):
    new_pairs = defaultdict(int)
    for p in pairs:
        for np in rules[p]:
            new_pairs[np] += pairs[p]
    return new_pairs

def solve(template, rules, steps):
    pairs = defaultdict(int)
    for i in range(len(template)-1):
        pairs[template[i:i+2]] += 1
    for _ in range(steps):
        pairs = step(pairs, rules)
    letters = defaultdict(int)
    for p in pairs:
        for l in p:
            letters[l]+=pairs[p]
    letters[template[0]]+=1
    letters[template[-1]]+=1
    for l in letters:
        letters[l] = letters[l]//2
    return max(letters.values()) - min(letters.values())

print('Part one:', solve(template, rulebook, 10))
print('Part two:', solve(template, rulebook, 40))