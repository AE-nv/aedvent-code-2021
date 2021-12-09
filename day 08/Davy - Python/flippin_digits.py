count_2_3_4_7 = lambda l: len(list(filter(lambda d: len(d) in (2, 4, 3, 7), l.split(" | ")[1].split(" "))))
print("Part 1:", sum([count_2_3_4_7(line) for line in open('input.txt', 'r').read().splitlines()]))


class Segment:

    def __init__(self, default_wire):
        self.default_wire = default_wire
        self.possible_wires = list('abcdefg')

    def restrict_to_wires(self, wires):
        self.possible_wires = list(filter(lambda w: w in wires, self.possible_wires))

    def remove_wires(self, wires):
        if len(self.possible_wires) > 1:
            self.possible_wires = list(filter(lambda w: w not in wires, self.possible_wires))

    def is_mapped(self):
        return len(self.possible_wires) == 1


class Display:

    def __init__(self, calibration_patterns, output_values):
        self.segments = {c: Segment(c) for c in 'abcdefg'}
        self.calibrate(calibration_patterns.split())
        self.digits = {
            self.get_mapped_wires('abcefg'): '0',
            self.get_mapped_wires('cf'): '1',
            self.get_mapped_wires('acdeg'): '2',
            self.get_mapped_wires('acdfg'): '3',
            self.get_mapped_wires('bcdf'): '4',
            self.get_mapped_wires('abdfg'): '5',
            self.get_mapped_wires('abdefg'): '6',
            self.get_mapped_wires('acf'): '7',
            self.get_mapped_wires('abcdefg'): '8',
            self.get_mapped_wires('abcdfg'): '9',
        }
        self.value = int("".join(map(lambda v: self.digits["".join(sorted(v))], output_values.split())))

    def calibrate(self, calibration_patterns):
        for cp in calibration_patterns:
            if len(cp) == 2:
                self.segments['c'].restrict_to_wires(cp)
                self.segments['f'].restrict_to_wires(cp)
            if len(cp) == 3:
                self.segments['a'].restrict_to_wires(cp)
                self.segments['c'].restrict_to_wires(cp)
                self.segments['f'].restrict_to_wires(cp)
            if len(cp) == 4:
                self.segments['b'].restrict_to_wires(cp)
                self.segments['c'].restrict_to_wires(cp)
                self.segments['d'].restrict_to_wires(cp)
                self.segments['f'].restrict_to_wires(cp)
            if len(cp) == 5:
                self.segments['a'].restrict_to_wires(cp)
                self.segments['d'].restrict_to_wires(cp)
                self.segments['g'].restrict_to_wires(cp)
            if len(cp) == 6:
                self.segments['a'].restrict_to_wires(cp)
                self.segments['b'].restrict_to_wires(cp)
                self.segments['f'].restrict_to_wires(cp)
                self.segments['g'].restrict_to_wires(cp)

        while not all(map(lambda s: s.is_mapped(), self.segments.values())):
            for s1 in self.segments.values():
                if s1.is_mapped():
                    for s2 in self.segments.values():
                        s2.remove_wires(s1.possible_wires)

    def get_mapped_wires(self, signal):
        return "".join(sorted(map(lambda c: self.segments[c].possible_wires[0], list(signal))))

    @classmethod
    def from_string(cls, text):
        calibration_patterns, output_values = text.split(' | ')
        return Display(calibration_patterns, output_values)


print("Part 2:", sum([Display.from_string(line).value for line in open('input.txt', 'r').read().splitlines()]))
