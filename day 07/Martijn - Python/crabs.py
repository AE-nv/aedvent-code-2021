def subtract(list1, list2):
    difference = []
    zip_object = zip(list1, list2)
    for list1_i, list2_i in zip_object:
        difference.append(abs(list1_i-list2_i))
    return difference

def calculate_cumulative_cost(list):
    cost_list = []
    for x in list:
        cumulative_cost = 0
        for y in range(0, x +1):
            cumulative_cost += y
        cost_list.append(cumulative_cost)
    return sum(cost_list)
    

crab_positions = [int(x) for x in open('./day 07/Martijn - Python/input.txt').readline().split(',')]
max_pos = max(crab_positions)
min_pos = min(crab_positions)
nb_crabs = len(crab_positions)
highest_cost = calculate_cumulative_cost(crab_positions)
target_position = 0
for x in range(min_pos, max_pos):
    target_list = [x] * nb_crabs
    moves = subtract(crab_positions, target_list)
    # part 1
    cost = sum(moves)
    # part 2
    cost = calculate_cumulative_cost(moves)
    if cost < highest_cost:
        highest_cost = cost
        target_position = x

print(target_position, highest_cost)
