def in_target(x, y, area):
    return x in range(area[0][0], area[0][1]+1) and y in range(area[1][0], area[1][1]+1)

def overshot(x, y, area):
    return x>area[0][1] or y<area[1][0]

def move(x,y, s_x, s_y):
    return x+s_x, y+s_y, max(s_x-1,0), s_y-1

target_area = [[235,259],[-118,-62]]

max_y = 0
initial_velocities = set()
for i in range(target_area[0][1]+1):
    for j in range(target_area[1][0],300):
        x,y, s_x,s_y, highest = 0,0, i,j, 0
        while not overshot(x,y, target_area):
            x,y,s_x,s_y = move(x,y,s_x,s_y)
            highest = max(highest, y)
            if in_target(x,y, target_area):
                max_y = max(max_y, highest)
                initial_velocities.add((i,j))
print(max_y, len(initial_velocities))
