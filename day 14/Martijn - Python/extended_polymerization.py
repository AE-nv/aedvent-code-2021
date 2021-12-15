import time
import math

def parse_rules(rules):
    rule_dict = {}
    for rule in rules:
        split = rule.strip().split('->')
        pattern = split[0].strip()
        insert = split[1].strip()
        rule_dict[pattern] = insert
    return rule_dict

def parse_polymer(polymer):
    pair_dict = {}
    for i in range(0, len(polymer) -1):
        pair = polymer[i] + polymer[i+1]
        if pair in pair_dict.keys():
            pair_dict[pair] += 1
        else:
            pair_dict[pair] = 1
    return pair_dict

def find_element_at_index(index, polymer_dict):
    result = -1
    for element in polymer_dict:
        if index in polymer_dict[element]:
            result =  element
            break
    return result

def insert_indeces_element(element, indices, insert_element, inserst_index ):
    new = []
    for index in indices:
            if index >= inserst_index:
                new.append(index + 1)
            else:
                new.append(index)
    if insert_element == element:
        new.append(inserst_index)
        
    return new
        

def insert_elements(polymer_dict, insert_dict):
    for key,value in insert_dict.items():
        for element in polymer_dict:
            polymer_dict[element] = insert_indeces_element(element, polymer_dict[element], value, key )
        if not value in polymer_dict:
            polymer_dict[value] = [key]
    return polymer_dict
        
            

def apply_rules(pair_dict, rule_dict):
    new_pair_dict = {}
    for pair in pair_dict:
        insert_element = rule_dict[pair]
        element_1 = pair[0]
        element_2 = pair[1]
        new_pair_1 = element_1 + insert_element
        new_pair_2 = insert_element + element_2
        new_pairs = [new_pair_1, new_pair_2]
        for new_pair in new_pairs:
            if new_pair in new_pair_dict.keys():
                new_pair_dict[new_pair] = pair_dict[pair] + new_pair_dict[new_pair]
            else:
                new_pair_dict[new_pair] = pair_dict[pair]
    return new_pair_dict
    

def calculate_diff_most_least_common_element(pair_dict, first, last):
    count_element_dict = {}
    for pair in pair_dict:
        for x in range(0,2):
            element = pair[x]
            if element in count_element_dict.keys():
                count_element_dict[element] += pair_dict[pair]
            else:
                count_element_dict[element] = pair_dict[pair]
    
    min_count = float('inf')
    max_count = float('-inf')
    for element in count_element_dict:
        count = count_element_dict[element] // 2
        if element == first or element == last:
            count += 1
        if count > max_count:
            max_count = count
        if count < min_count:
            min_count = count
    print(max_count - min_count)

if __name__ == '__main__':
    input = open('./day 14/Martijn - Python/input.txt').readlines()
    start_polymer = [c for c in input[0].strip()]
    rules = [rule for rule in input if len(rule.split('->')) == 2]
    rule_dict = parse_rules(rules)
    pair_dict = parse_polymer(start_polymer)
    tic = time.perf_counter()
    for step in range(0, 40):
        print(step)
        pair_dict = apply_rules(pair_dict, rule_dict)
        toc = time.perf_counter()
        print(f"Apply step in {toc - tic:0.4f} seconds")
    calculate_diff_most_least_common_element(pair_dict, start_polymer[0], start_polymer[-1])

