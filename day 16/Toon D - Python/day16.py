from functools import reduce
hex_map = {
    '0': '0000',
    '1': '0001',
    '2': '0010',
    '3': '0011',
    '4': '0100',
    '5': '0101',
    '6': '0110',
    '7': '0111',
    '8': '1000',
    '9': '1001',
    'A': '1010',
    'B': '1011',
    'C': '1100',
    'D': '1101',
    'E': '1110',
    'F': '1111',
}
line = open("day 16/Toon D - Python/input", "r").readline()
input_bits = ''.join(map(lambda x: hex_map[x], line))


class Packet:
    version = 0
    type = 0
    content = []

    def __init__(self, version, type, content):
        self.version = version
        self.type = type
        self.content = content

    def sum_versions(self):
        if self.type == 4:
            return self.version
        else:
            return self.version + sum([x.sum_versions() for x in self.content])

    def get_value(self):
        if self.type == 4:
            return self.content
        result = [x.get_value() for x in self.content]
        if self.type == 0:
            return sum(result)
        elif self.type == 1:
            return reduce(lambda x, y: x*y, result)
        elif self.type == 2:
            return min(result)
        elif self.type == 3:
            return max(result)
        elif self.type == 5:
            return int(result[0] > result[1])
        elif self.type == 6:
            return int(result[0] < result[1])
        elif self.type == 7:
            return int(result[0] == result[1])


class Bits:
    bits = ''

    def __init__(self, bits: str):
        self.bits = bits

    def get(self, amount: int):
        requested = self.bits[0:amount]
        self.bits = self.bits[amount:]
        return requested

    def get_int(self, amount: int):
        return int(self.get(amount), 2)

    def empty(self):
        return len(self.bits) == 0

    def parse_literal(self) -> int:
        end = False
        result = ''
        while not end:
            if self.get(1) == '0':
                end = True
            result += self.get(4)
        return int(result, 2)

    def parse_bit_length(self) -> list:
        bit_length = self.get_int(15)
        sub_packets_bits = Bits(self.get(bit_length))
        content = []
        while not sub_packets_bits.empty():
            content += [sub_packets_bits.parse_packet()]
        return content

    def parse_packet_length(self) -> list:
        packet_amounts = self.get_int(11)
        content = []
        for _ in range(packet_amounts):
            content += [self.parse_packet()]
        return content

    def parse_packet(self) -> Packet:
        version = self.get_int(3)
        type = self.get_int(3)
        if type == 4:
            return Packet(version, type, self.parse_literal())
        else:
            length_type_id = self.get_int(1)
            if length_type_id == 0:
                return Packet(version, type, self.parse_bit_length())
            else:
                return Packet(version, type, self.parse_packet_length())


result = Bits(input_bits).parse_packet()
print('part 1: %i' % result.sum_versions())

print('part 2: %i' % result.get_value())
