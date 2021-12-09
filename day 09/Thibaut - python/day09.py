def inGrid(ri, ci, grid):
    return ri>=0 and ci>=0 and ri<len(grid) and ci<len(grid[0])

def findBasin(ri, ci, grid):
    result=[(ri, ci)]
    for xm, ym in [(-1,0),(1,0),(0,-1),(0,1)]:
        if inGrid(ri+xm, ci+ym, grid):
            p = grid[ri + xm][ci + ym]
            if p<9 and p > grid[ri][ci]:
                result.append((ri+xm,ci+ym))
                extras=findBasin(ri+xm,ci+ym,grid)
                result+=extras
    return result


if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()
    grid=[]
    for l in data:
        grid.append(list(map(lambda x: int(x),l.strip())))
    lowPoints=[]
    basins=[]
    for ri,row in enumerate(grid):
        for ci,column in enumerate(row):
            isLowPoint=True #innocent till proven guilty
            for xm, ym in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                if inGrid(ri + xm, ci + ym, grid):
                    if grid[ri+xm][ci+ym] <= grid[ri][ci]:
                        isLowPoint=False
            if isLowPoint:
                lowPoints.append(column)
                basins.append(len(set(findBasin(ri,ci,grid))))
    print("p1:",sum(lowPoints)+len(lowPoints))
    top3=sorted(basins, reverse=True)[:3]
    print("p2:",top3[0]*top3[1]*top3[2])