file = open("day 09/Toon D - Python/input", "r")
heightmap = [[int(x) for x in line if x != '\n' ] for line in file.readlines()]

def is_low_point(heightmap, x, y):
  height = heightmap[x][y]
  surrounding = []
  if x > 0:
    surrounding += [heightmap[x-1][y]]
  if x < len(heightmap)-1:
    surrounding += [heightmap[x+1][y]]
  if y > 0:
    surrounding += [heightmap[x][y-1]]
  if y < len(heightmap[0])-1:
    surrounding += [heightmap[x][y+1]]
  
  return False not in [height < x for x in surrounding]

is_low = [heightmap[x][y] + 1 if is_low_point(heightmap, x, y) else 0 for y in range(0, len(heightmap[0])) for x in range(0,len(heightmap))]
print("part 1: %i" % sum(is_low))

def find_basin_points(heightmap, x, y, points):
    point = str([x,y])
    if point in points:
      return points
    points += [point]

    if x > 0 and heightmap[x-1][y] != 9:
      points = find_basin_points(heightmap, x-1, y, points)
    if x < len(heightmap)-1 and heightmap[x+1][y] != 9:
      points = find_basin_points(heightmap, x+1, y, points)
    if y > 0 and heightmap[x][y-1] != 9:
      points = find_basin_points(heightmap, x, y-1, points)
    if y < len(heightmap[0])-1 and heightmap[x][y+1] != 9:
      points = find_basin_points(heightmap, x, y+1, points)
    
    return points

sizes = sorted([len(find_basin_points(heightmap, x, y, []))  for y in range(0, len(heightmap[0])) for x in range(0,len(heightmap)) if is_low_point(heightmap, x, y)])
print("part 2: %i" % (sizes[-1]*sizes[-2]*sizes[-3]))
