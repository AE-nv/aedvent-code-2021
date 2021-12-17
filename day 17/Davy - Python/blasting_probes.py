from typing import NamedTuple

x1, x2, y1, y2 = 57, 116, -198, -148


class Probe(NamedTuple):
    x: int
    y: int
    v_x: int
    v_y: int

    def move(self):
        return Probe(self.x + self.v_x,
                     self.y + self.v_y,
                     self.v_x - 1 if self.v_x > 0 else self.v_x + 1 if self.v_x < 0 else 0,
                     self.v_y - 1)


def fire(v_x, v_y):
    p = Probe(0, 0, v_x, v_y)
    while p.x <= x2 and p.y >= y1 and (p.x < x1 or p.x > x2 or p.y < y1 or p.y > y2):
        p = p.move()
        yield p


def blast(a, b):
    for x in range(a):
        for y in range(-b, b):
            probes = list(fire(x, y))
            hits = [pos for pos in probes if x1 <= pos.x <= x2 and y1 <= pos.y <= y2]
            if hits:
                yield max(pos.y for pos in probes)


print("Part 1:", max(blast(500, 500)))
print("Part 2:", len(list(blast(500, 500))))
