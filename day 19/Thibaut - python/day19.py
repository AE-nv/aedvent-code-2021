import numpy as np

def calculateDistances(scanner):
    result = np.zeros((len(scanner), len(scanner)))
    for i, p1 in enumerate(scanner):
        for j, p2 in enumerate(scanner):
            result[i,j]=sum(abs(val1-val2) for val1, val2 in zip(p1,p2))
    return result

def getDistances(c1, c2):
    result = np.zeros((len(c1), len(c2)))
    for i, p1 in enumerate(c1):
        for j, p2 in enumerate(c2):
            result[i,j]=sum(abs(val1-val2) for val1, val2 in zip(p1,p2))
    return result

def orient(pt, orientation):
    #Credit for this goes to reddit
    a, b, c = pt
    return (
        (+a,+b,+c), (+b,+c,+a), (+c,+a,+b), (+c,+b,-a), (+b,+a,-c), (+a,+c,-b),
        (+a,-b,-c), (+b,-c,-a), (+c,-a,-b), (+c,-b,+a), (+b,-a,+c), (+a,-c,+b),
        (-a,+b,-c), (-b,+c,-a), (-c,+a,-b), (-c,+b,+a), (-b,+a,+c), (-a,+c,+b),
        (-a,-b,+c), (-b,-c,+a), (-c,-a,+b), (-c,-b,-a), (-b,-a,-c), (-a,-c,-b)
    )[orientation]

def rotate(coords, r):
    result=[]
    for point in coords:
        result.append(orient(point,r))
    return result

def getTranslation(c1, c2):
    result = np.zeros((len(c1), len(c2)))
    for i, p1 in enumerate(c1):
        for j, p2 in enumerate(c2):
            result[i, j] = sum(abs(val1 - val2) for val1, val2 in zip(p1, p2))
    unique, counts = np.unique(result, return_counts=True)
    for i, c in enumerate(counts):
        if c>=12:
            indices=np.where(result==unique[i])
            c1i,c2i=indices[0][0],indices[1][0]
            return [val1 - val2 for val1, val2 in zip(c1[c1i],c2[c2i])]


def translate(coords, translation):
    result=[]
    for c in coords:
        result.append([val1 + val2 for val1, val2 in zip(c, translation)])
    return result

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    coords=[]
    for l in data:
        if l[0:3]=="---":
            coords.append([])
        elif len(l.strip())>0:
            coords[-1].append(list(map(lambda x:int(x),l.strip().split(","))))

    mapping=[(0,0,0)] #0 has at least twelve matching beacons with 0 by using orientation 0
    while len(mapping)<len(coords):
        try:
            for i in filter(lambda x: x not in [m[0] for m in mapping], range(1,len(coords))):
                #Select a set of coords that still needs to be matched
                for r in range(0,24):
                    orientatedCoords = rotate(coords[i],r)
                    for j in [m[0] for m in mapping]:
                        #print(i,j,r)
                        distances = getDistances(sorted(coords[j]), sorted(orientatedCoords))
                        unique, counts = np.unique(distances, return_counts=True)
                        #print(counts)
                        if any(count>=12 for count in counts):
                            translation = getTranslation(sorted(coords[j]), sorted(orientatedCoords))
                            mapping.append((i,j,r, translation))
                            raise Exception("found match for "+str(i)+" => "+str((i,j,r)))
        except Exception as e:
            print(e)
    print(mapping)

    mapDict={}
    for m in mapping:
        mapDict[m[0]]=m[1:4]

    probes=[]
    for m in mapping:
        end=start=m[0]
        tmp=coords[start]
        while end!=0:
            end, rotationId, translation = mapDict[start]
            tmp = rotate(tmp, rotationId)
            tmp = translate(tmp, translation)
            start=end
        for c in tmp:
            if c not in probes:
                probes.append(c)
    print("p1:",len(probes))

    origins=[[[0,0,0]] for m in mapping]
    fixedOrigins=[]
    for m in mapping:
        end=start=m[0]
        tmp=origins[start]
        while end!=0:
            end, rotationId, translation = mapDict[start]
            tmp = rotate(tmp, rotationId)
            tmp = translate(tmp, translation)
            start=end
        for c in tmp:
            if c not in probes:
                fixedOrigins.append(c)
    print(fixedOrigins)
    print(np.amax(calculateDistances(fixedOrigins)))



    """
    distances=[]
    for scanner in coords:
        tmp=calculateDistances(scanner)
        #print(tmp)
        distances.append(tmp)

    #CORE ASSUMPTION: THE MANHATTEN DISTANCES BETWEEN TWO SAME BEACONS IS THE SAME NO MATTER THE SCANNER ORIENTATION

    matches=[]
    for i, referencePoint in enumerate(distances):
        for j, potentialMatch in enumerate(distances):
            if j>i:
                commonDistances=[]
                drit = np.nditer(referencePoint, flags=['multi_index'])
                for dr in drit:
                    dpit = np.nditer(potentialMatch, flags=['multi_index'])
                    for dp in dpit:
                        if dr==dp and dr!=0:
                            commonDistances.append((drit.multi_index, dpit.multi_index))
                if len(commonDistances)>=288:
                    matches.append((i,j,commonDistances))
    
    for i, j, distanceMatches in matches:
        print("sensor 1:",i)
        print("sensor 2:",j)
        print("distanceMatches:", distanceMatches)
        chain = [distanceMatches[0]]
        #for i in range
            #next(obj for obj in objs if obj.val == 5)
    # """