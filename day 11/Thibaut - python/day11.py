import numpy as np

def canFlash(octos, flashed):
    hyped = octos>9
    can = []
    for ri in range(0,10):
        for ci in range(0,10):
            if hyped[ri, ci] and (ri,ci) not in flashed:
                can.append((ri,ci))
    return can

def inGrid(x,y,lvls):
    return x>-1 and x<10 and y>-1 and y<10

def flash(flasher,lvls):
    (ri, ci)=flasher
    for xm in range(-1, 2):
        for ym in range(-1,2):
            if inGrid(ri+xm,ci+ym, lvls):
                lvls[ri+xm,ci+ym]+=1

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    data = [[int(c) for c in l.strip()] for l in data]
    lvls = np.array(data)
    nSteps=100
    globalFlashers=[]
    for i in range(1,nSteps+1):
        print("STEP",i)
        flashed=[]
        lvls+=1
        while len(canFlash(lvls, flashed))>0:
            flashers = canFlash(lvls, flashed)
            for flasher in flashers:
                flash(flasher, lvls)
                flashed.append(flasher)
        for (ri,ci) in flashed:
            lvls[ri,ci]=0
        globalFlashers.append(flashed)
    print("p1:",sum([len(s) for s in globalFlashers]))
    flns = [len(flashers) for flashers in globalFlashers]
    if 100 in flns:
        print("p2:",flns.index(100))
    else:
        flashed=[]
        while len(flashed)<100:
            i+=1
            print("STEP",i)
            flashed = []
            lvls += 1
            while len(canFlash(lvls, flashed)) > 0:
                flashers = canFlash(lvls, flashed)
                for flasher in flashers:
                    flash(flasher, lvls)
                    flashed.append(flasher)

            for (ri, ci) in flashed:
                lvls[ri, ci] = 0
            print(lvls)


