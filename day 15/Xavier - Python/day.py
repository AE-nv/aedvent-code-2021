grid = [[int(x) for x in line] for line in open('input.txt').read().splitlines()]

def dijkstra(grid, start, stop):
    distances, temp = {}, {start: 0}
    while len(temp)>0:
        # set closest point as definite
        x,y = min(temp, key=temp.get)
        distance = temp.pop((x,y))
        distances[(x,y)] = distance
        # check if its neighbours are now accessible or closer than before
        nbs = [(x+i,y+j) for (i,j) in [(-1,0),(0,-1),(1,0),(0,1)] if 0<=x+i<len(grid) and 0<=y+j<len(grid[0])]
        for nb in nbs:
            d = distance + grid[nb[0]][nb[1]]
            if distances.get(nb, -1)<0 and d<temp.get(nb,float('inf')):
                temp[nb] = d
    return distances.get(stop)

print(dijkstra(grid, (0,0), (len(grid)-1,len(grid)-1)))

# set up big grid
p2 = [[0]*len(grid)*5 for _ in range(len(grid)*5)]
for x in range(len(p2)):
    for y in range(len(p2)):
        grid_distance = x//len(grid)+y//len(grid)
        value = grid[x%len(grid)][y%len(grid)]
        p2[x][y] = value+grid_distance if value+grid_distance < 10 else (value+grid_distance)%9

print(dijkstra(p2, (0,0), (len(p2)-1,len(p2)-1)))