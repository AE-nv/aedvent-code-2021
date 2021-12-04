def parse_file_as_char_list():
    return list(map(list, open('input.txt', 'r').read().splitlines()))


def most_common_bit(bits):
    return '0' if bits.count('0') > bits.count('1') else '1'


def least_common_bit(bits):
    return '1' if bits.count('1') < bits.count('0') else '0'


def calculate_power_consumption(bit_criteria, values):
    return int(''.join(list(map(bit_criteria, list(zip(*values))))), 2)


def calculate_life_support_rating(bit_criteria, values, pos):
    if len(values) == 1:
        return int(''.join(values[0]), 2)
    bits_to_check = list(map(lambda bits: bits[pos], values))
    filtered_values = list(filter(lambda v: v[pos] == bit_criteria(bits_to_check), values))
    return calculate_life_support_rating(bit_criteria, filtered_values, pos + 1)


input_values = parse_file_as_char_list()
print("Part 1:", calculate_power_consumption(most_common_bit, input_values) *
      calculate_power_consumption(least_common_bit, input_values))
print("Part 2:",
      calculate_life_support_rating(most_common_bit, input_values, 0) *
      calculate_life_support_rating(least_common_bit, input_values, 0))
