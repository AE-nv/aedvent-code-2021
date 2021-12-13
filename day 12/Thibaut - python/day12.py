from collections import Counter

def lowerCanVisit(destination, smallVisits):
    if destination=='start':
        return False
    counts=dict(Counter(smallVisits+[destination]))
    multiVisits=0
    for k,count in counts.items():
        if count>2:
            return False
        elif count>1:
            multiVisits+=1

    return multiVisits<2

def findPathways(connections, start, smallVisits=['start']):
    if start=='end':
        return [['end']]
    directConnections=connections[start]
    paths = []
    for destination in directConnections:
        #print(start, destination, smallVisits)
        futures=[]
        if (destination.islower() and lowerCanVisit(destination, smallVisits)):
            #print("visit small cave:",destination)
            futures = findPathways(connections,destination, smallVisits+[destination])
        elif destination.isupper():
            #print("visit large cave:", destination)
            futures = findPathways(connections, destination, smallVisits)
        for path in futures:
            paths.append([start] + path)
    return paths



if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    connection={}
    for l in data:
        p1, p2 = l.strip().split('-')
        if p1 in connection.keys():
            connection[p1].append(p2)
        else:
            connection[p1]=[p2]
        if p2 in connection.keys():
            connection[p2].append(p1)
        else:
            connection[p2]=[p1]
    print(connection)
    #pathways = findPathways(connection,'start')
    #print(len(pathways))

    cavesI={}
    for i,k in enumerate(connection.keys()):
        cavesI[k]=i
    for k, vs in connection.items():
        print(list(map(lambda x: cavesI[x], vs)))
    print('CI:',cavesI)
