import itertools
from dataclasses import dataclass
from functools import reduce
from time import perf_counter

rotations = [
    lambda a, b, c: (+a, +b, +c),
    lambda a, b, c: (+b, +c, +a),
    lambda a, b, c: (+c, +a, +b),
    lambda a, b, c: (+c, +b, -a),
    lambda a, b, c: (+b, +a, -c),
    lambda a, b, c: (+a, +c, -b),
    lambda a, b, c: (+a, -b, -c),
    lambda a, b, c: (+b, -c, -a),
    lambda a, b, c: (+c, -a, -b),
    lambda a, b, c: (+c, -b, +a),
    lambda a, b, c: (+b, -a, +c),
    lambda a, b, c: (+a, -c, +b),
    lambda a, b, c: (-a, +b, -c),
    lambda a, b, c: (-b, +c, -a),
    lambda a, b, c: (-c, +a, -b),
    lambda a, b, c: (-c, +b, +a),
    lambda a, b, c: (-b, +a, +c),
    lambda a, b, c: (-a, +c, +b),
    lambda a, b, c: (-a, -b, +c),
    lambda a, b, c: (-b, -c, +a),
    lambda a, b, c: (-c, -a, +b),
    lambda a, b, c: (-c, -b, -a),
    lambda a, b, c: (-b, -a, -c),
    lambda a, b, c: (-a, -c, -b)
]


def find_shared_offset(a1, b1, c1, a2, b2, c2):
    point_combinations = [
        ((a1, a2), (b1, b2), (c1, c2)),
        ((a1, a2), (b1, c2), (c1, b2)),
        ((a1, b2), (b1, a2), (c1, c2)),
        ((a1, b2), (b1, c2), (c1, a2)),
        ((a1, c2), (b1, a2), (c1, b2)),
        ((a1, c2), (b1, b2), (c1, a2)),
    ]
    for p1, p2, p3 in point_combinations:
        if p1[0].get_offset(p1[1]) == p2[0].get_offset(p2[1]) == p3[0].get_offset(p3[1]):
            return p1[0].get_offset(p1[1])
    return 0, 0, 0


@dataclass(frozen=True)
class Beacon:
    x: int
    y: int
    z: int

    def get_distance(self, other):
        return abs(self.x - other.x) + abs(self.y - other.y) + abs(self.z - other.z)

    def get_offset(self, other):
        return self.x - other.x, self.y - other.y, self.z - other.z

    def to_abs_position(self, x, y, z):
        return Beacon(self.x - x, self.y - y, self.z - z)

    def rotate(self, rotation):
        return Beacon(*rotation(self.x, self.y, self.z))


@dataclass
class Scanner:
    id: int
    beacons: []
    all_3_point_distances: []
    position: (int, int, int)

    def __init__(self, id, beacons):
        self.id = id
        self.beacons = beacons
        self.position = (0, 0, 0)
        self.all_3_point_distance_combos = self.calc_3_point_distance_combos()
        self.all_3_point_distances = self.calc_3_point_distances()

    def calc_3_point_distances(self):
        return set([combo[0].get_distance(combo[1]) *
                    combo[0].get_distance(combo[2]) *
                    combo[1].get_distance(combo[2])
                    for combo in itertools.combinations(self.beacons, 3)])

    def calc_3_point_distance_combos(self):
        return [(combo, (combo[0].get_distance(combo[1]) *
                         combo[0].get_distance(combo[2]) *
                         combo[1].get_distance(combo[2])))
                for combo in itertools.combinations(self.beacons, 3)]

    def rotate(self, rotation):
        return Scanner(self.id, [b.rotate(rotation) for b in self.beacons])

    def has_12_matching_points(self, other):
        return len(set(self.get_absolute_beacons()).intersection(set(other.beacons))) >= 12

    def finalize_position(self):
        print("Finalizing position of scanner", self.id, "to", self.position)
        self.beacons = self.get_absolute_beacons()
        self.all_3_point_distances = self.calc_3_point_distances()
        self.all_3_point_distance_combos = self.calc_3_point_distance_combos()

    def has_overlap(self, other):
        overlap_in_distances = len(self.all_3_point_distances.intersection(other.all_3_point_distances))
        if overlap_in_distances < 200:
            return False
        for combo, distance in self.all_3_point_distance_combos:
            for other_combo, other_distance in other.all_3_point_distance_combos:
                if distance == other_distance:
                    self.position = find_shared_offset(*combo, *other_combo)
                    if self.position != (0, 0, 0):
                        if self.has_12_matching_points(other):
                            self.finalize_position()
                            return True
        return False

    def get_absolute_beacons(self):
        return [beacon.to_abs_position(*self.position) for beacon in self.beacons]


scanners = [Scanner(scanner_id, [Beacon(*[int(n) for n in beacon_pos.split(',')])
                                 for beacon_pos in scanner_beacons.splitlines()[1:]])
            for scanner_id, scanner_beacons in enumerate(open('input.txt').read().split('\n\n'))]
resolved_scanners = scanners[:1]
unresolved_scanners = scanners[1:]
no_dice_combos = []


def rotate_until_matches(scanner, other_scanner):
    for r in rotations:
        rotated_scanner = scanner.rotate(r)
        if rotated_scanner.has_overlap(other_scanner):
            return rotated_scanner
    return None


t1_start = perf_counter()
while unresolved_scanners:
    for us, rs in itertools.product(unresolved_scanners, resolved_scanners):
        if (us.id, rs.id) in no_dice_combos:
            continue
        print("Comparing", us.id, rs.id)
        result = rotate_until_matches(us, rs)
        if result:
            unresolved_scanners.remove(us)
            resolved_scanners.append(result)
            break
        else:
            no_dice_combos.append((us.id, rs.id))

print("Part 1:", len(set(reduce(lambda acc, s: itertools.chain(acc, s.beacons), resolved_scanners, []))))
print("Part 2:", max([(abs(a[0] - b[0]) + abs(a[1] - b[1]) + abs(a[2] - b[2])) for a, b in
                      itertools.combinations([scanner.position for scanner in resolved_scanners], 2)]))

t1_stop = perf_counter()
print("Duration:", round(t1_stop - t1_start))
