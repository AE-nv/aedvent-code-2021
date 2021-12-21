def movePositions(p1, p2, s1, s2, turn, rolls):
    if turn == 1:
        p1 = (p1 + sum(rolls)) % 10
        if p1 == 0:
            s1 += 10
        else:
            s1 += p1
    else:
        p2 = (p2 + sum(rolls)) % 10
        if p2 == 0:
            s2 += 10
        else:
            s2 += p2
    #print(p1, p2, s1, s2)
    return p1, p2, s1, s2, (turn+1)%2

def doTurn(p1,p2,s1,s2,turn, nw1=0, nw2=0, depth=0):
    for k in range(1, 4):
        for l in range(1, 4):
            for m in range(1, 4):
                p1,p2,s1,s2,turn = movePositions(p1,p2,s1,s2,turn, [k,l,m])
                #print(sum([k,l,m]), "P1:", p1, "P2:", p2, "S1:", s1,"S2:",s2, "turn:", turn)
                if s1>=21 or s2>=21:
                    if s1>=21:
                        nw1+=1
                    else:
                        nw2+=1
                    return nw1, nw2
                else:
                    dw1, dw2 = doTurn(p1, p2, s1, s2, turn, nw1, nw2, depth+1)
                    nw1+=dw1
                    nw2+=dw2
                print(nw1, nw2)
    #return nw1, nw2

cache={}
def turn2(p1, p2, s1, s2, turn):
    global cache
    if str([p1,p2,s1,s2,turn]) in cache.keys():
        return cache[str([p1,p2,s1,s2,turn])]
    if s1>=21:
        return 1,0
    elif s2>=21:
        return 0,1
    else:
        nw1=nw2=0
        for k in range(1, 4):
            for l in range(1, 4):
                for m in range(1, 4):
                    #print(depth, [k,l,m])
                    newP1,newP2,newS1,newS2,newTurn = movePositions(p1,p2,s1,s2,turn, [k,l,m])
                    #print(sum([k,l,m]), "P1:", p1, "P2:", p2, "S1:", s1,"S2:",s2, "turn:", turn)
                    dw1, dw2 = turn2(newP1,newP2,newS1,newS2,newTurn)
                    nw1+=dw1
                    nw2+=dw2
        cache[str([p1,p2,s1,s2,turn])]= (nw1, nw2)
        return nw1, nw2

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    p1=int(data[0].strip().split(":")[1])%10
    p2=int(data[1].strip().split(":")[1])%10 #position 0 is actually position 10

    s1=0
    s2=0

    dstart=1
    turn = 1
    nRolls=0
    while s1<1000 and s2<1000:
        rolls=[]
        for i in range(0,3):
            if dstart==0 :
                rolls.append(100)
            else:
                rolls.append(dstart)
            dstart=(dstart+1)%100
            nRolls+=1
        if turn==1:
            p1 = (p1 + sum(rolls)) % 10
            if p1 == 0:
                s1 += 10
            else:
                s1 += p1
        else:
            p2 = (p2 + sum(rolls)) % 10
            if p2 == 0:
                s2 += 10
            else:
                s2 += p2
        turn=(turn+1)%2

    print(nRolls, s1, s2)
    print("P1:",min(s1,s2)*nRolls)

    p1 = int(data[0].strip().split(":")[1]) % 10
    p2 = int(data[1].strip().split(":")[1]) % 10
    print("P2:",max(turn2(p1, p2, 0, 0, turn=1)))
    """
    rolls=[]
    for k in range(1,4):
        for l in range(1,4):
            for m in range(1,4):
                rolls.append(sum([k,l,m]))
    print(Counter(rolls))
    #"""

