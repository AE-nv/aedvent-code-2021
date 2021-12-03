from typing import List, Tuple


def map_input(bit_string: str) -> List[List[str]]:
    bit_string = bit_string.split("\n")[0]
    return list(list(map(int, bit_string)))


def bit_list_to_int(bit_list: List[int]) -> int:
    return int("".join(list(map(str, bit_list))), 2)


def get_common_bit(bit_list: List[int], type_cb: str = "MOST") -> int:
    mcb = 1 if sum(bit_list) >= len(bit_list) / 2 else 0

    if type_cb == "MOST":
        return mcb
    elif type_cb == "LEAST":
        return 1 - mcb
    else:
        raise ValueError


def part_1(bit_lists: List[List[int]]) -> Tuple[List[int], List[int]]:
    transpose = list(map(list, zip(*bit_lists)))

    gamma_bits = [1 if get_common_bit(bit_list) else 0 for bit_list in transpose]
    epsilon_bits = [1 - bit for bit in gamma_bits]

    return gamma_bits, epsilon_bits


def solution_recursion(bit_list: List[List[int]], index: int = 0, type_cb: str = "MOST") -> List[int]:
    if len(bit_list) == 1:
        return bit_list[0]

    mcb = get_common_bit([i[index] for i in bit_list], type_cb=type_cb)
    next_list = list(filter(lambda x: x[index] == mcb, bit_list))

    return solution_recursion(next_list, index=index + 1, type_cb=type_cb)


def solution_non_recursion(bit_list: List[List[int]], type_cb: str = "MOST"):
    current_list = bit_list
    current_index = 0
    while len(current_list) > 1:
        mcb = get_common_bit([i[current_index] for i in current_list], type_cb=type_cb)
        current_list = list(filter(lambda x: x[current_index] == mcb, current_list))
        current_index += 1

    return current_list[0]


def part_2(bit_lists: List[List[int]]) -> Tuple[List[int], List[int]]:
    return solution_non_recursion(bit_lists, type_cb="MOST"), solution_non_recursion(bit_lists, type_cb="LEAST")


def part_2_recursion(bit_lists: List[List[int]]) -> Tuple[List[int], List[int]]:
    return solution_recursion(bit_lists, type_cb="MOST"), solution_recursion(bit_lists, type_cb="LEAST")


if __name__ == '__main__':
    bit_strings = list(map(map_input, open('./input.txt').readlines()))

    gamma, epsilon = part_1(bit_strings)
    gamma = bit_list_to_int(gamma)
    epsilon = bit_list_to_int(epsilon)
    print(f"Solution of part 1: {gamma * epsilon}")

    oxygen_generator_rating, co2_scrubber_rating = part_2(bit_strings)
    oxygen_generator_rating = bit_list_to_int(oxygen_generator_rating)
    co2_scrubber_rating = bit_list_to_int(co2_scrubber_rating)
    print(f"Solution of part 2: {oxygen_generator_rating * co2_scrubber_rating}")
