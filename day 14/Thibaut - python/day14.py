from collections import Counter
import numpy as np

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    start=data[0].strip()
    rules={}
    for l in data[2:]:
        left, right = l.strip().replace(" ","").split("->")
        rules[left]=right

    pairCounts={}
    for i,c in enumerate(start[:-1]):
        pair=c+start[i+1]
        if pair not in pairCounts.keys():
            pairCounts[pair]=1
        else:
            pairCounts[pair]+=1

    singleCounts={}
    for c in start:
        if c not in singleCounts.keys():
            singleCounts[c]=1
        else:
            singleCounts[c]+=1


    nSteps=40
    for step in range(0,nSteps):
        print("STEP ",step)
        print(pairCounts)

        additions=[]
        for pair in pairCounts.keys():
            injection = rules[pair]
            additions.append((pair,rules[pair]))

        newPairCounts={}
        for k,v in additions:
            pair1=k[0]+v
            pair2=v+k[1]
            count=pairCounts[k]
            if pair1 not in newPairCounts.keys():
                newPairCounts[pair1] = count
            else:
                newPairCounts[pair1] += count
            if pair2 not in newPairCounts.keys():
                newPairCounts[pair2] = count
            else:
                newPairCounts[pair2] += count
            if v not in singleCounts.keys():
                singleCounts[v] = count
            else:
                singleCounts[v] += count
        pairCounts=newPairCounts

    print(singleCounts)
    values= singleCounts.values()
    print("p1:",max(values)-min(values))