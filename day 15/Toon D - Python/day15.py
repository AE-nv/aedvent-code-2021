from queue import PriorityQueue

lines = open("day 15/Toon D - Python/input", "r").readlines()
grid = [[int(x) for x in line.replace('\n','')] for line in lines]

def is_in_field_1(x,y):
  return x >= 0 and \
    x < len(grid) and \
    y >= 0 and \
    y < len(grid[0])

def is_in_field_2(x,y):
  return x >= 0 and \
    x < len(grid) * 5 and \
    y >= 0 and \
    y < len(grid[0]) * 5

def get_value_1(x,y):
  return grid[x][y]

def get_value_2(x,y):
  diff_x, original_x = divmod(x, len(grid))
  diff_y, original_y = divmod(y, len(grid))

  distance = (grid[original_x][original_y] + diff_x + diff_y)
  if distance > 9:
    return 1 + distance % 10
  else:
    return distance

def dijkstra(len_x, len_y, get_distance, is_in_field):
  shortest_paths = {str([x,y]): 999999999 for x in range(len_x) for y in range(len_y)}
  visited = set()

  queue = PriorityQueue()
  queue.put((0, [0,0]))
  while not queue.empty():
    (distance, (a,b)) = queue.get()

    for (da,db) in [(0,-1),(0,1),(-1,0),(1,0)]:
      x = a + da
      y = b + db
      next_key = str([x,y])
      
      if is_in_field(x,y):
        if next_key in visited:
          continue

        d = distance + get_distance(x,y)
        previous_d = shortest_paths[next_key]
        if d < previous_d:
          shortest_paths[next_key] = d
          queue.put((d,[x,y]))
  return shortest_paths

len_x_1 = len(grid)
len_y_1 = len(grid[0])
print('part 1: %i' % dijkstra(len_x_1, len_y_1, get_value_1, is_in_field_1)[str([len_x_1-1,len_y_1-1])])

len_x_2 = len_x_1*5
len_y_2 = len_y_1*5
print('part 2: %i' % dijkstra(len_x_2, len_y_2, get_value_2, is_in_field_2)[str([len_x_2-1,len_y_2-1])])
      

