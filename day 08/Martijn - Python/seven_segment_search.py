def part_one(input):
    nb_unique = 0
    for line in input:
        line = line.strip()
        observation_output = line.split('|')[1]
        nb_line = count_unique_line(observation_output)
        nb_unique += nb_line
    print(nb_unique)

def count_unique_line(line):
    nb = 0
    unique_list = [2, 4, 3, 7]
    for obs in line.split(' '):
        if len(obs) in unique_list:
            nb += 1
    return nb

def find_3(bit_length_dict):
    # in len 2 en 2 keer in len 6
    len_6 = bit_length_dict[6]
    len_2 = bit_length_dict[2]
    result = -1
    for bit in len_2:
        if len_6.count(bit) == 2:
            result = bit
    return result

def find_6(dict_signals):
    len_6 = dict_signals[6]
    len_2 = dict_signals[2]
    result = -1
    for bit in len_2:
        if len_6.count(bit) == 3:
            result = bit
    return result

def find_7(bit_length_dict):
    len_3 = bit_length_dict[3]
    len_5 = bit_length_dict[5]
    len_6 = bit_length_dict[6]
    result = -1
    for bit in len_5:
        if len_5.count(bit) == 3 and len_3.count(bit) == 0 and len_6.count(bit) == 3:
            result = bit
    return result

def find_1(bit_length_dict):
    len_2 = bit_length_dict[2]
    len_3 = bit_length_dict[3]
    result = -1
    for bit in len_3:
        if len_2.count(bit) == 0:
            result = bit
    return result



def find_2(bit_length_dict):
    len_2 = bit_length_dict[2]
    len_4 = bit_length_dict[4]
    len_6 = bit_length_dict[6]
    result = -1
    for bit in len_4:
        if len_6.count(bit) == 3 and len_2.count(bit) == 0:
            result = bit
    return result

def find_4(bit_length_dict):
    len_2 = bit_length_dict[2]
    len_4 = bit_length_dict[4]
    len_6 = bit_length_dict[6]
    result = -1
    for bit in len_4:
        if len_6.count(bit) == 2 and len_2.count(bit) == 0:
            result = bit
    return result

def find_5(bit_length_dict):
    len_5 = bit_length_dict[5]
    len_6 = bit_length_dict[6]
    result = -1
    for bit in len_5:
        if len_5.count(bit) == 1 and len_6.count(bit) == 2:
            result = bit
    return result




def decode_signals(signal_patterns):
    '''
    Find which letter corresponds to number of segmented led display
    1111
    2  3
    2  3
    4444
    5  6
    5  6
    7777    
    '''
    signals = [x for x in signal_patterns.strip().split(' ')]
    bit_length_dict = {
        2: [],
        3: [],
        4: [],
        5: [],
        6: [],
        7: [],
    }
    decoding = {
        '1': -1,
        '2': -1,
        '3': -1,
        '4': -1,
        '5': -1,
        '6': -1,
        '7': -1
    }
    for signal in signals:
        length = len(signal)
        bit_length_dict[length] += signal
    decoding['1'] = find_1(bit_length_dict)
    decoding['2'] = find_2(bit_length_dict)
    decoding['3'] = find_3(bit_length_dict)
    decoding['4'] = find_4(bit_length_dict)
    decoding['5'] = find_5(bit_length_dict)
    decoding['6'] = find_6(bit_length_dict)
    decoding['7'] = find_7(bit_length_dict)
    return decoding

def decode_byte(byte, decoded_signals):
    '''
    Translate led display to value
    '''
    inv_decoded = {value: key for key, value in decoded_signals.items()}
    decoder = {
        '123567': 0,
        '36': 1,
        '13457': 2,
        '13467': 3,
        '2346': 4,
        '12467': 5,
        '124567': 6,
        '136': 7,
        '1234567': 8,
        '123467': 9,  
    }
    decoded_byte = ""
    for bit in byte:
        decoded_bit = inv_decoded[bit]
        decoded_byte += decoded_bit
    sorted_byte = "".join(sorted(decoded_byte))
    value = decoder[sorted_byte]
    return value



if __name__ == '__main__':
    input = open('./day 08/Martijn - Python/input.txt').readlines()
    part_one(input)
    total = 0
    for i in range(0, len(input)):
        line = input[i]
        signals = line.split('|')[0]
        decoded_signals = decode_signals(signals)
        bytes = line.split('|')[1]
        bytes = bytes.strip().split(' ')
        number = 0
        for x in range(0, len(bytes)):
            byte = bytes[x]
            value = decode_byte(byte, decoded_signals)
            number += value * (10 ** (len(bytes) - x - 1))
        total += number
    print(total)

    
    