input = open("day 20/Toon D - Python/input", "r").readlines()

iea = input[0].replace('\n','')

image = set()
for y in range(0, len(input)-2):
  for x in range(0, len(input[2])-1):
    if input[y+2][x] == '#':
      image.add(str([x,y]))

def get_number(x,y, image):
  result = ''
  for dx, dy in [(a,b) for b in range(-1,2) for a in range(-1,2)]:
    if str([x+dx, y+dy]) in image:
      result += '1'
    else:
      result += '0'
  return int(result, 2)

min_x, max_x = 0, len(input[2])-1
min_y, max_y = 0, len(input)-2
new_image = image

for i in range(50):
  old_image = new_image

  if i % 2 == 1:
    for x in range(min_x-2, max_x+2):
      old_image.add(str([x,min_y-2]))
      old_image.add(str([x,min_y-1]))
      old_image.add(str([x,max_y]))
      old_image.add(str([x,max_y+1]))
    for y in range(min_y-2, max_y+2):
      old_image.add(str([min_x-2,y]))
      old_image.add(str([min_x-1,y]))
      old_image.add(str([max_x,y]))
      old_image.add(str([max_x+1,y]))

  new_image = set()
  min_x, max_x = min_x - 1, max_x + 1
  min_y, max_y = min_y - 1, max_y + 1
  for x in range(min_x, max_x):
    for y in range(min_y, max_y):
      if iea[get_number(x,y, old_image)] == '#':
        new_image.add(str([x,y]))
  
  if i == 1:
    print("part 1: %i" % len(new_image))

print("part 2: %i" % len(new_image))
