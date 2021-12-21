import functools
from collections import Counter

def movePositions(p1, p2, s1, s2, turn, roll):
    if turn == 1:
        p1 = (p1 + roll) % 10
        if p1 == 0:
            s1 += 10
        else:
            s1 += p1
    else:
        p2 = (p2 + roll) % 10
        if p2 == 0:
            s2 += 10
        else:
            s2 += p2
    #print(p1, p2, s1, s2)
    return p1, p2, s1, s2, (turn+1)%2

@functools.cache
def performTurn(p1, p2, s1, s2, turn):
    if s1>=21:
        return [1,0]
    elif s2>=21:
        return [0,1]
    else:
        nw1=nw2=0
        for k, v in {6: 7, 5: 6, 7: 6, 4: 3, 8: 3, 3: 1, 9: 1}.items():
            newP1,newP2,newS1,newS2,newTurn = movePositions(p1,p2,s1,s2,turn, k)
            nw1, nw2 = [i*v+j for i,j in zip(performTurn(newP1, newP2, newS1, newS2, newTurn),[nw1,nw2])]
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
    print("P2:",max(performTurn(p1, p2, 0, 0, 1)))
    """
    rolls=[]
    for k in range(1,4):
        for l in range(1,4):
            for m in range(1,4):
                rolls.append(sum([k,l,m]))
    print(Counter(rolls))
    #"""

