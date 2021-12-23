import math
from collections import Counter
import functools

def getStateCopy(state):
    newState = ["E", "E", [], "E", [], "E", [], "E", [], "E", "E"]
    for i in [0,1,3,5,7,9,10]:
        newState[i]=str(state[i])
    for i in [2,4,6,8]:
        tmp=[]
        for el in state[i]:
            tmp.append(str(el))
        newState[i] = tmp
    return newState

def isValidForParking(letter, room, roomSize):
    counts = Counter(room)
    return counts[letter]+counts["E"] == roomSize

def canReach(state, startIndex, destinationIndex):
    if isinstance(state[destinationIndex], str) and state[destinationIndex]!="E": return False #destination is occupied hallwaylocation
    for i, element in enumerate(state):
        if isinstance(element,str) and element!="E" \
                and ((startIndex < i <= destinationIndex) or (destinationIndex <= i < startIndex)):
            return False
    return True

def putInRoom(state, letter, roomIndex):
    j = state[roomIndex].index("E")
    state[roomIndex][j]=letter
    return state

def getMovementCost(letter, nMoves):
    return {"A":1, "B":10,"C":100, "D":1000}[letter]*nMoves

def isEmptyRoom(room, roomSize):
    return Counter(room)["E"]==roomSize

def canLeave(letter, room, roomSize):
    #Return true if there is any other letter in the room apart from empty spaces and the given letter
    counts = Counter(room)
    return counts[letter] + counts["E"] < roomSize

def getNextStates(state, roomSize):
    result=[]
    roomIndices = {"A": 2, "B": 4, "C": 6, "D": 8}
    for index, element in enumerate(state):
        if isinstance(element, str) and element != "E":
            # hallway case
            destinationIndex = roomIndices[element]
            if isValidForParking(element, state[destinationIndex], roomSize) and canReach(state, index, destinationIndex):
                # If we are allowed to park the element in it's proper destination and we can reach that room
                # then place it there
                newState = putInRoom(getStateCopy(state), element, destinationIndex)
                newState[index] = "E"
                nMoves = abs(destinationIndex - index) + Counter(state[destinationIndex])["E"]
                result.append((newState,getMovementCost(element, nMoves)))
        elif isinstance(element, list) and not isEmptyRoom(element, roomSize):
            # room case
            # get the top element
            top = next((x for x in element if x != "E"), -1)
            if top != -1:
                topIndex = element.index(top)
                destinationIndex = roomIndices[top]
                if index != destinationIndex or canLeave(top,state[destinationIndex], roomSize):
                    if isValidForParking(top, state[destinationIndex], roomSize) and canReach(state, index,
                                                                                                                  destinationIndex):
                        # If we are not yet in the right room and we are allowed to park the element in it's proper room and we can reach that room
                        # then place it there
                        newState = putInRoom(getStateCopy(state), top, destinationIndex)
                        newState[index][topIndex] = "E"
                        nMoves = abs(destinationIndex - index) + Counter(state[destinationIndex])["E"] + (topIndex + 1)
                        result.append((newState,getMovementCost(top, nMoves)))
                    else:
                        for option in [0, 1, 3, 5, 7, 9, 10]:
                            if canReach(state, index, option):
                                newState = getStateCopy(state)
                                newState[option] = top
                                newState[index][topIndex] = "E"
                                nMoves = abs(option - index) + (topIndex + 1)
                                result.append((newState,getMovementCost(top, nMoves)))
    return result

def toTuple(lst):
    result=[]
    for el in lst:
        if isinstance(el, list):
            result.append(toTuple(el))
        else:
            result.append(el)
    return tuple(result)

def toList(tpl):
    result=[]
    for e in tpl:
        if isinstance(e, tuple):
            result.append(toList(e))
        else:
            result.append(e)
    return result

@functools.cache
def getMinimalCost(start, end, roomSize):
    #print(start)
    if start==end: return 0
    costs=[math.inf]
    start=toList(start)
    for state, cost in getNextStates(start, roomSize):
        costs.append(cost+getMinimalCost(toTuple(state), end, roomSize))
    return min(costs)


def dijkstraSolver(start, end, roomSize):
    #Keep list of states that need to be explored
    toBeExplored = [(start,0)] # state includes the positions of the archis + score for that state so far
    finished=[]
    roomIndices={"A":2, "B":4,"C":6, "D":8}
    bestCostSoFar=math.inf
    while len(toBeExplored)>0:
        print(len(toBeExplored), len(finished), bestCostSoFar)
        state, cost = toBeExplored.pop()


        #Check the hallway for any nonEmpty positions:
        for index, element in enumerate(state):
            if isinstance(element, str) and element!="E":
                #hallway case
                destinationIndex = roomIndices[element]
                if isValidForParking(element,state[destinationIndex], roomSize) and canReach(state, index, destinationIndex):
                    #If we are allowed to park the element in it's proper destination and we can reach that room
                    #then place it there
                    newState=putInRoom(getStateCopy(state), element, destinationIndex)
                    newState[index]="E"
                    nMoves=abs(destinationIndex-index)+Counter(state[destinationIndex])["E"]
                    newCost=cost+getMovementCost(element, nMoves)
                    if newCost<=bestCostSoFar:
                        if newState==end:
                            bestCostSoFar=newCost
                            finished.append((newState, newCost))
                        else:
                            toBeExplored.append((newState, newCost))
            elif isinstance(element, list) and not isEmptyRoom(element, roomSize):
                #room case
                #get the top element
                top = next((x for x in element if x!="E"),-1)
                if top!=-1:
                    topIndex=element.index(top)
                    destinationIndex= roomIndices[top]
                    if index!=destinationIndex or canLeave(top,state[destinationIndex], roomSize):
                        if isValidForParking(top, state[destinationIndex], roomSize) and canReach(state, index, destinationIndex):
                            # If we are not yet in the right room and we are allowed to park the element in it's proper room and we can reach that room
                            # then place it there
                            newState = putInRoom(getStateCopy(state), top, destinationIndex)
                            newState[index][topIndex] = "E"
                            nMoves = abs(destinationIndex - index) + Counter(state[destinationIndex])["E"] + (topIndex + 1)
                            newCost = cost + getMovementCost(top, nMoves)
                            if newCost <= bestCostSoFar:
                                if newState == end:
                                    bestCostSoFar = newCost
                                    finished.append((newState, newCost))
                                else:
                                    toBeExplored.append((newState, newCost))
                        else:
                            for option in [0,1,3,5,7,9,10]:
                                if canReach(state,index,option):
                                    newState=getStateCopy(state)
                                    newState[option]=top
                                    newState[index][topIndex]="E"
                                    nMoves = abs(option - index) + (topIndex+1)
                                    newCost = cost + getMovementCost(top, nMoves)
                                    if newCost < bestCostSoFar:
                                        #only need to add to toBeExplored, as this move cannot result in the final state
                                        toBeExplored.append((newState, newCost))
    return bestCostSoFar





if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    #archis=[archipod(2,1,"B"), archipod(2,0,"A"), archipod(4,1,"C"), archipod(4,0,"D"),archipod(6,1,"B"), archipod(6,0,"C"), archipod(8,1,"D"), archipod(8,0,"A")]
    start=["E","E",["C","B"],"E",["D","A"],"E",["D","B"],"E",["A","C"],"E","E"]
    end=["E","E",["A","A"],"E",["B","B"],"E",["C","C"],"E",["D","D"],"E","E"]
    #print(dijkstraSolver(start, end, 2))
    print("p1",getMinimalCost(toTuple(start), toTuple(end), 2))

    start2 = ["E", "E", ["C", "D", "D","B"], "E", ["D","C","B", "A"], "E", ["D", "B", "A", "B"], "E", ["A", "A", "C", "C"], "E", "E"]
    end2 = ["E", "E", ["A", "A", "A", "A"], "E", ["B", "B", "B", "B"], "E", ["C", "C", "C", "C"], "E", ["D", "D", "D", "D"], "E", "E"]
    exampleStart = ["E", "E", ["B", "D", "D","A"], "E", ["C","C","B", "D"], "E", ["B", "B", "A", "C"], "E", ["D", "A", "C", "A"], "E", "E"]
    #print(dijkstraSolver(start2, end2,4))
    print("p2",getMinimalCost(toTuple(start2), toTuple(end2), 4))