import numpy as np

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    jellies = np.array(list(map(lambda x: int(x), data[0].rstrip().split(','))))
    newbiesAges= np.array([])
    newbiesCount= np.array([])
    nDays=256
    for day in range(1,nDays+1):
        newJellies = jellies[np.where(jellies==0)].size
        
        for i, newJelly in enumerate(newbiesAges):
            if newJelly==0:
                newJellies+=newbiesCount[i]
        if newJellies>0:
            jellies[jellies==0]=7
            newbiesAges[newbiesAges==0]=7
            newbiesAges = np.append(newbiesAges,9)
            newbiesCount = np.append(newbiesCount, newJellies)
            #jellies = np.concatenate((jellies, np.full(newJellies,9)))
        jellies=jellies-1
        newbiesAges=newbiesAges-1
        print("day", day)
        print(newJellies)
        #print(newbiesCount, newbiesAges)
    print(len(jellies)+sum(newbiesCount))


