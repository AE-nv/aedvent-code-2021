def split_byte_to_bits(bitstring):
    return [int(bit) for bit in bitstring.rstrip("\n")]

def calculate_gamma(dict_0, dict_1):
    nb_bits = len(dict_0)
    gamma = 0
    for i in range(0, nb_bits):
        if dict_1[i] > dict_0[i]:
            gamma += 2**(nb_bits - 1 - i)
    return gamma

def calculate_epsilon(dict_0, dict_1):
    nb_bits = len(dict_0)
    epsilon = 0
    for i in range(0, nb_bits):
        if dict_0[i] > dict_1[i]:
            epsilon += 2**(nb_bits - 1 - i)
    return epsilon

def first_part(data):
    nb_bits = len(data[0])
    bit_1_dict = {}
    bit_0_dict = {}
    for x in range(0,nb_bits):
        bit_1_dict[x] = 0
        bit_0_dict[x] = 0

    for bitlist in data:
        for i in range(0, nb_bits):
            bit = bitlist[i]
            if bit == 1:
                bit_1_dict[i] += 1
            else:
                bit_0_dict[i] += 1
    gamma = calculate_gamma(bit_0_dict, bit_1_dict)
    epsilon = calculate_epsilon(bit_0_dict, bit_1_dict)
    return gamma * epsilon

def find_most_common_bit(data, position):
    nb_0 = 0
    nb_1 = 0
    for bitlist in data:
        if bitlist[position] == 1:
            nb_1 += 1
        else:
            nb_0 += 1
    if nb_0 > nb_1:
        return 0
    else:
        return 1

def find_least_common_bit(data, position):
    nb_0 = 0
    nb_1 = 0
    for bitlist in data:
        if bitlist[position] == 1:
            nb_1 += 1
        else:
            nb_0 += 1
    if nb_0 > nb_1:
        return 1
    else:
        return 0


def filter_data(data, bit, position):
    new_data = []
    for bitlist in data:
        if bitlist[position] == bit:
            new_data.append(bitlist)
    return new_data

def find_oxygen(data):
    nb_bits = len(data[0])
    i = 0
    while i < nb_bits and len(data) > 1:
        most_common_bit = find_most_common_bit(data, i)
        data = filter_data(data, most_common_bit, i)
        i += 1
    return data[0]

def find_co2(data):
    nb_bits = len(data[0])
    j = 0
    while j < nb_bits and len(data) > 1:
        least_common_bit = find_least_common_bit(data, j)
        data = filter_data(data, least_common_bit, j)
        j += 1
    return data[0]

def binair_to_decimal(bitlist):
    nb_bits = len(bitlist)
    decimal = 0
    for i in range(0, nb_bits):
        if bitlist[i] == 1:
            decimal += 2**(nb_bits - 1 - i)
    return decimal

def second_part(data):
    oxygen_binair = find_oxygen(data)
    co2_binair = find_co2(data)
    oxygen = binair_to_decimal(oxygen_binair)
    co2 = binair_to_decimal(co2_binair)
    return oxygen * co2

    

data = [split_byte_to_bits(bitstring) for bitstring  in open('day 03\Martijn - Python\input.txt').readlines()]
first_answer = first_part(data)
second_answer = second_part(data)
print(first_answer, second_answer)




