def get_boundaries(grid):
    min_x = min([k[0] for k in grid.keys()])
    max_x = max([k[0] for k in grid.keys()])
    min_y = min([k[1] for k in grid.keys()])
    max_y = max([k[1] for k in grid.keys()])
    return min_x, max_x, min_y, max_y


def enhance_pixel(grid, x, y, edge_color):
    algo_key = int("".join(str(n) for n in
                           [grid.get((x - 1, y - 1), edge_color), grid.get((x, y - 1), edge_color),
                            grid.get((x + 1, y - 1), edge_color), grid.get((x - 1, y), edge_color),
                            grid.get((x, y), edge_color), grid.get((x + 1, y), edge_color),
                            grid.get((x - 1, y + 1), edge_color), grid.get((x, y + 1), edge_color),
                            grid.get((x + 1, y + 1), edge_color)]), 2)
    return 1 if algo[algo_key] == '#' else 0


def enhance_image(grid, count, edge_color=0):
    min_x, max_x, min_y, max_y = get_boundaries(grid)
    updated_img = {(x, y): enhance_pixel(grid, x, y, edge_color) for x in range(min_x, max_x + 1) for y in range(min_y, max_y + 1)}
    new_edge_color = enhance_pixel({}, 1, 1, edge_color)
    return enhance_image(updated_img, count - 1, new_edge_color) if count > 1 else updated_img


def add_padding(grid, padding):
    min_x, max_x, min_y, max_y = get_boundaries(grid)
    return {(x, y): grid.get((x, y), 0) for x in range(min_x - padding, max_x + padding) for y in range(min_y - padding, max_y + padding)}


algo, image = open('input.txt').read().split('\n\n')
pixels = add_padding({(x, y): int(p == '#') for y, line in enumerate(image.splitlines()) for x, p in enumerate(line)}, 100)
print("Part 1:", sum(enhance_image(pixels.copy(), 2).values()))
print("Part 2:", sum(enhance_image(pixels.copy(), 50).values()))
