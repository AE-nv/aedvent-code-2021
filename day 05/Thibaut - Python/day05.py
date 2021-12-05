import numpy as np


def drawOnGrid(x1,x2,y1,y2,grid):
    #print([(x1, y1), (x2, y2)])
    for x in range(x1,x2+1):
        for y in range(y1,y2+1):
            #print((x,y))
            grid[x,y]+=1

def drawOnGridD(x1,x2,y1,y2, grid):
    if x1<x2:
        xs = range(x1, x2 + 1)
    else:
        xs = list(range(x2, x1 + 1))
        xs.reverse()
    if y1<y2:
        ys = range(y1, y2 + 1)
    else:
        ys = list(range(y2, y1 + 1))
        ys.reverse()
    cs = zip(xs, ys)
    for x,y in list(cs):
        grid[x, y] += 1

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    lines=[]
    grid = np.zeros((1000,1000))
    for l in data:
        p1, p2 = l.rstrip().split('->')
        x1, y1 = list(map(lambda x: int(x),p1.split(',')))
        x2, y2 = list(map(lambda x: int(x),p2.split(',')))

        if x2==x1 or y2==y1:
            if x1>x2 or y1>y2:
                lines.append([(x2, y2), (x1, y1)])
                drawOnGrid(x2, x1, y2, y1,grid)
                #print(np.transpose(grid))
            else:
                lines.append([(x1, y1),(x2, y2)])
                drawOnGrid(x1, x2, y1, y2, grid)
        elif abs(x2-x1)==abs(y2-y1):
            drawOnGridD(x1, x2, y1, y2, grid)


    crossPoints=(grid > 1).sum()
    print(np.transpose(grid))
    print(crossPoints)





