import networkx as nx


def calc_shortest_path(cave_map):
    end_pos = list(cave_map.keys())[-1]
    g = nx.DiGraph()
    for p in cave_map.keys():
        for to_pos in [(p[0] + 1, p[1]), (p[0] - 1, p[1]), (p[0], p[1] + 1), (p[0], p[1] - 1)]:
            if to_pos in cave_map:
                g.add_edge(p, to_pos, weight=cave_map[to_pos] / 10)
    path = nx.dijkstra_path(g, (0, 0), end_pos)
    return sum(cave_map[pos] for pos in path[1:])


def enlarge_map(cave_map):
    step = list(cave_map.keys())[-1][0] + 1
    large_map = {}
    for (x, y), risk in cave_map.items():
        for i in range(0, 5 * step, step):
            for j in range(0, 5 * step, step):
                large_map[(x+i, y+j)] = (risk + i + j) % 9 if (risk + i + j) % 9 != 0 else 9
    return large_map


cave = {(x, y): int(c) for y, row in enumerate(open('input.txt').read().splitlines()) for x, c in enumerate(row)}
print("Part 1:", calc_shortest_path(cave))
print("Part 2:", calc_shortest_path(enlarge_map(cave)))
