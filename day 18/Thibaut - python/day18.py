import ast
import math
import numpy as np

def add(p1, p2):
    return [p1,p2]

def canReduce(p):
    return checkExplode(p) or checkSplit(p)

def checkExplode(p, depth=0):
    #print(p, depth)
    if isinstance(p, list):
        if depth>=4:
            return True
        else:
            return checkExplode(p[0], depth+1) or checkExplode(p[1], depth+1)
    else:
        return False

def checkSplit(p):
    if isinstance(p,int):
        return p>=10
    return checkSplit(p[0]) or checkSplit(p[1])

def getExplodingPair(p, depth=0, path=[]):
    if isinstance(p, list):
        if depth>=4 and not any(isinstance(i, list) for i in p):
            return (p, depth, path)
        else:
            if checkExplode(p[0], depth+1):
                path.append(0)
                return getExplodingPair(p[0], depth+1, path)
            else:
                path.append(1)
                return getExplodingPair(p[1], depth+1,path)

def getNearestNeighbour(path, splitIndex):
    #Get the nearest neighbour with the given split index closest to the node on the path
    for i, direction in enumerate(reversed(path)):
        if direction==splitIndex:
            return path[0:len(path)-i]
    return []

def literalIn(p):
    return isinstance(p,int) or literalIn(p[0]) or literalIn(p[1])

def getLeftMostLiteral(p, path=[]):
    if isinstance(p, int):
        return (p, path)
    else:
        if literalIn(p[0]):
            path.append(0)
            return getLeftMostLiteral(p[0],path)
        else:
            path.append(1)
            return getLeftMostLiteral(p[1], path)

def getRightMostLiteral(p, path=[]):
    if isinstance(p, int):
        return (p, path)
    else:
        if literalIn(p[1]):
            path.append(1)
            return getRightMostLiteral(p[1],path)
        else:
            path.append(0)
            return getRightMostLiteral(p[0], path)

def getSubTree(p, path):
    #print(p)
    if len(path)==0:
        return p
    else:
        return getSubTree(p[path[0]], path[1:])

def changeValue(p,newValue,path):
    #print(path, p)
    if len(path)==0:
        return newValue
    else:
        if path[0]==0:
            return [changeValue(p[0],newValue, path[1:]), p[1]]
        else:
            return [p[0],changeValue(p[1],newValue, path[1:])]

def explode(p):
    result=p
    exploding, depth, path = getExplodingPair(p,0,[])
    leftPath = getNearestNeighbour(path, 0) #Return the path to the deepest right split on the road to the exploding pair
    if len(leftPath)!=0: #Found a right split
        leftPath[-1] = 1 #Change the split to a right branch
        #leftPath is now the path that point to the subtree where we need to look for the left most literal
        subTree=getSubTree(p,leftPath)
        v, subPath = getLeftMostLiteral(subTree,[])
        fullPath=leftPath+subPath
        result = changeValue(result,v+exploding[1],fullPath)

    rightPath = getNearestNeighbour(path, 1)
    if len(rightPath)!=0:
        rightPath[-1]=0
        subTree=getSubTree(result,rightPath)
        v, subPath = getRightMostLiteral(subTree,[])
        fullPath=rightPath+subPath
        result = changeValue(result, v+exploding[0], fullPath)
    result = changeValue(result,0,path)
    return result

def split(p):
    if isinstance(p, int):
        if p>=10:
            return [math.floor(p/2), math.ceil(p/2)]
    if checkSplit(p[0]):
        return [split(p[0]),p[1]]
    else:
        return [p[0],split(p[1])]

def reduce(p):
    if checkExplode(p):
        return explode(p)
    elif checkSplit(p):
        return split(p)

def getMagnitude(p):
    if isinstance(p, int):
        return p
    else:
        return 3*getMagnitude(p[0])+2*getMagnitude(p[1])

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    pair = ast.literal_eval(data[0])
    #"""
    step=1
    for line in data[1:]:
        pair2 = ast.literal_eval(line.strip())
        pair = add(pair, pair2)
        while canReduce(pair):
            pair=reduce(pair)
        step+=1
    #"""
    print("p1:",getMagnitude(pair))

    results=np.zeros((len(data), len(data)))
    for i,l1 in enumerate(data):
        for j,l2 in enumerate(data):
            pair=add(ast.literal_eval(l1.strip()), ast.literal_eval(l2.strip()))
            while canReduce(pair):
                pair = reduce(pair)
            results[i,j]=getMagnitude(pair)
    print("p2:",np.amax(results))