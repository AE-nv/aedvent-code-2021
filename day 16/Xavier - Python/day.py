from functools import reduce
from operator import eq, lt, gt, add, mul

binary = ''.join([bin(int(char, 16))[2:].zfill(4) for char in open('input.txt').readline()])
   
def parse_packet(packet):
    versions = [int(packet[:3], 2)]
    type_id = int(packet[3:6], 2)
    remaining = packet[6:]
    values = []
    if type_id == 4:
        # parse literal
        has_next_part = True
        while has_next_part:
            if remaining[0] == '0':
                has_next_part = False
            values.append(remaining[1:5])
            remaining = remaining[5:]
    else:
        # parse subpackets
        subpacket_number = subpackets_length = float('inf')
        parsed_packets = parsed_length = 0
        if remaining[0] == '1':
            subpacket_number = int(remaining[1:12], 2)
            remaining = remaining[12:]
        else:
            subpackets_length = int(remaining[1:16], 2)
            remaining = remaining[16:]
        while not parsed_packets == subpacket_number and not parsed_length == subpackets_length:
            version,length,value = parse_packet(remaining)
            parsed_length += length
            parsed_packets += 1
            versions.append(version)
            values.append(value)
            remaining = remaining[length:]
    # calculate packet value
    operators = [add, mul, min, max, add, gt, lt, eq]
    value = reduce(lambda x,y: operators[type_id](x,y), values)
    if type_id == 4:
        value = int(value, 2)
    return sum(versions), len(packet)-len(remaining), value            

print(parse_packet(binary))
