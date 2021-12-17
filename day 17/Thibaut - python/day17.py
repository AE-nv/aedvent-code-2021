def preArea(x,y,area):
    return x<=area[0][1] and y>=area[1][1]

def calculateTrajectory(vx, vy, area):
    x, y = 0, 0
    path= [(x,y)]
    while preArea(x+vx,y+vy,area):
        x+=vx
        if vx>0:
            vx-=1
        y+=vy
        vy-=1
        path.append((x,y))
    return path

def inArea(x,y,area):
    return preArea(x,y, area) and x>=area[0][0] and y<=area[1][0]

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    area=((57,116),(-148,-198))
    #area = ((20, 30), (-5, -10))
    #tmp = calculateTrajectory(7,97,area)
    #print(tmp[-1], inArea(tmp[-1][0], tmp[-1][1], area))
    distinctSpeeds=[]
    for vy in range(-500,500):
        for vx in range(1,400):
            arch = calculateTrajectory(vx,vy,area)
            if inArea(arch[-1][0],arch[-1][1],area):
                #print(arch[-1])
                maxY=max(arch, key=lambda x: x[1])[1]
                print(vx,vy,maxY)
                if str((vx, vy)) not in distinctSpeeds:
                    distinctSpeeds.append(str((vx,vy)))
    print(len(distinctSpeeds))

