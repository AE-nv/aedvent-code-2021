enhancement, raw = open('input.txt').read().split('\n\n')

image = dict()
for x, line in enumerate(raw.split('\n')):
    for y, pixel in enumerate(line):
        image[(x,y)] = pixel

def convert_to_binary(pixels):
    return ''.join(['1' if pixel == '#' else '0' for pixel in pixels])

def step(image, enhancement, default):
    new_image = dict()
    x_values = [key[0] for key in image.keys()]
    min_x = min(x_values)-1
    max_x = max(x_values)+1
    y_values = [key[1] for key in image.keys()]
    min_y = min(y_values)-1
    max_y = max(y_values)+1
    for x in range(min_x, max_x+1):
        for y in range(min_y, max_y+1):
            pixels = [image.get((x+i, y+j), default) for i,j in [(-1,-1), (-1,0), (-1,1), (0,-1), (0,0), (0,1), (1,-1), (1,0), (1,1)]]
            new_image[(x,y)] = enhancement[int(convert_to_binary(pixels),2)]
    return new_image

def part_one(image, enhancement):
    image = step(step(image, enhancement, '.'), enhancement, '#')
    light = sum([1 for pixel in image.values() if pixel == '#'])
    return light

print('Part one:', part_one(image, enhancement))

def part_two(image, enhancement):
    for i in range(50):
        default = '#' if i%2 == 1 else '.'
        image = step(image, enhancement, default)
    light = sum([1 for pixel in image.values() if pixel == '#'])
    return light

print('Part two:', part_two(image, enhancement))