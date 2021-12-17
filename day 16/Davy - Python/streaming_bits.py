from functools import reduce


class Packet:

    def __init__(self, version, packet_type):
        self.version = version
        self.packet_type = packet_type
        self.value = None
        self.packets = []

    def get_value(self):
        child_values = [p.get_value() for p in self.packets]
        if self.packet_type == 0:
            return sum(child_values)
        if self.packet_type == 1:
            return reduce(lambda a, b: a * b, child_values)
        if self.packet_type == 2:
            return min(child_values)
        if self.packet_type == 3:
            return max(child_values)
        if self.packet_type == 4:
            return self.value
        if self.packet_type == 5:
            return 1 if child_values[0] > child_values[1] else 0
        if self.packet_type == 6:
            return 1 if child_values[0] < child_values[1] else 0
        if self.packet_type == 7:
            return 1 if child_values[0] == child_values[1] else 0


def take_char(bits, count):
    return "".join([next(bits) for i in range(count)])


def take_num(bits, count):
    return int(take_char(bits, count), 2)


def read_packet(bits):
    packet = Packet(take_num(bits, 3), take_num(bits, 3))
    if packet.packet_type == 4:
        flag, result = "", ""
        while flag != "0":
            flag = take_char(bits, 1)
            result += take_char(bits, 4)
        packet.value = int(result, 2)
    else:
        length_type = take_char(bits, 1)
        if length_type == "0":
            sub_packets_length = take_num(bits, 15)
            sub_packets_bits = (c for c in take_char(bits, sub_packets_length))
            while True:
                try:
                    packet.packets.append(read_packet(sub_packets_bits))
                except StopIteration:
                    break
        else:
            sub_packet_count = take_num(bits, 11)
            for i in range(sub_packet_count):
                packet.packets.append(read_packet(bits))
    return packet


def get_versions(packet):
    yield packet.version
    for p in packet.packets:
        yield from get_versions(p)


root_packet = read_packet((bit for bit in list("".join(["{0:04b}".format(int(c, 16)) for c in open('input.txt').read()]))))
print("Part 1:", sum([v for v in get_versions(root_packet)]))
print("Part 2:", root_packet.get_value())
