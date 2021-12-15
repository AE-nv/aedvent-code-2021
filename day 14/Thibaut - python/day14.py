from collections import defaultdict

def breathFirstPolymerisation(singleCounts, pairCounts, rules, nSteps):
    for step in range(0, nSteps):
        #print("STEP ", step)

        additions = [(pair, rules[pair]) for pair in pairCounts.keys()]

        newPairCounts = defaultdict(lambda: 0)
        for k, v in additions:
            count = pairCounts[k]
            newPairCounts[k[0] + v] += count
            newPairCounts[v + k[1]] += count
            singleCounts[v] += count
        pairCounts = newPairCounts
    return singleCounts, pairCounts

def depthFirstPolymerisation(singleCounts, pairCounts, rules, nSteps):
    if nSteps==0:
        print(singleCounts)
        return singleCounts, pairCounts
    #cache store differences in single and pair counts per levels difference
    for pair in pairCounts:
        sc, pc = breathFirstPolymerisation(singleCounts,{pair:pairCounts[pair]}, rules, 1)
        depthFirstPolymerisation(sc, pc, rules, nSteps-1)
    return singleCounts, pairCounts

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    start=data[0].strip()
    rules={}
    for l in data[2:]:
        left, right = l.strip().replace(" ","").split("->")
        rules[left]=right

    pairCounts = defaultdict(lambda: 0)
    for pair in [start[i:i + 2] for i in range(0, len(start) - 1)]:
        pairCounts[pair] += 1

    singleCounts = defaultdict(lambda: 0)
    for c in start:
        singleCounts[c] += 1

    #singleCounts, pairCounts = breathFirstPolymerisation(singleCounts, pairCounts, rules, 40)
    singleCounts, pairCounts = depthFirstPolymerisation(singleCounts, pairCounts, rules, 10)

    values= singleCounts.values()
    print("p1:",max(values)-min(values))