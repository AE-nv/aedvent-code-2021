import sys

def bits_to_int(bitlist):
    out = 0
    for bit in bitlist:
        out = (out << 1) | bit
        
    return out

def solve(lines):
    length = len(lines[0])
    total_lines = len(lines)
    gamma = []
    epsilon = []
    
    for i in range(length):
        amount_of_1s = 0
        amount_of_0s = 0
    
        for j in range(total_lines):
            bit = lines[j][i]
            
            if int(bit) == 1:
                amount_of_1s += 1
            else:
                amount_of_0s += 1
        
        if amount_of_1s > amount_of_0s:
            gamma.append(1)
            epsilon.append(0)
        else:
            gamma.append(0)
            epsilon.append(1)
            
    return bits_to_int(gamma) * bits_to_int(epsilon)
            

with open('input.txt') as file:
    result = solve(file.read().splitlines())
    print(result)