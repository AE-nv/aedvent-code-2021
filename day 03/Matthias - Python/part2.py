import sys

def bits_to_int(bitlist):
    out = 0
    for bit in bitlist:
        out = (out << 1) | int(bit)
        
    return out
    
def keep(list, index, bit): 
    new_list = []
    
    for line in list:
        if int(line[index]) == int(bit):
            new_list.append(line)
            
    return new_list

def solve(original_list):

    oxygen_generator_rating = 0
    co2_scrubber_rating = 0
    
    oxygen_lines = [x for x in original_list]
    scrubber_lines = [x for x in original_list]
    
    length = len(oxygen_lines[0])
    total_oxygen_lines = len(oxygen_lines)
    total_scrubber_lines = len(scrubber_lines)
    
    for index in range(length):
        amount_of_1s_for_oxygen = 0
        amount_of_0s_for_oxygen = 0
        
        amount_of_1s_for_scrubber = 0
        amount_of_0s_for_scrubber = 0
    
        for j in range(total_oxygen_lines):
            bit = oxygen_lines[j][index]
            
            if int(bit) == 1:
                amount_of_1s_for_oxygen += 1
            else:
                amount_of_0s_for_oxygen += 1
                
        for j in range(total_scrubber_lines):
            bit = scrubber_lines[j][index]
            
            if int(bit) == 1:
                amount_of_1s_for_scrubber += 1
            else:
                amount_of_0s_for_scrubber += 1
                
        if total_oxygen_lines > 1:
            if amount_of_1s_for_oxygen >= amount_of_0s_for_oxygen:
                oxygen_lines = keep(oxygen_lines, index, 1)
            else:
                oxygen_lines = keep(oxygen_lines, index, 0)
            
        if total_scrubber_lines > 1:
            if amount_of_0s_for_scrubber <= amount_of_1s_for_scrubber:
                scrubber_lines = keep(scrubber_lines, index, 0)
            else:
                scrubber_lines = keep(scrubber_lines, index, 1)
                
        total_oxygen_lines = len(oxygen_lines)
        total_scrubber_lines = len(scrubber_lines)
        
    oxygen_generator_rating = bits_to_int(oxygen_lines[0])
    co2_scrubber_rating = bits_to_int(scrubber_lines[0])
    
    return oxygen_generator_rating * co2_scrubber_rating

with open('input.txt') as file:
    result = solve(file.read().splitlines())
    print(result)