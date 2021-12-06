def count_fish(days, file):
    input = [int(age) for age in open(file).readline().strip().split(',')]
    ages = [input.count(i) for i in range(9)]
    for i in range(days):
        new_ages = [0]*9
        for i in range(9):
            if i == 0:
                new_ages[6] += ages[0]
                new_ages[8] += ages[0]
            else:
                new_ages[i-1] += ages[i]
        ages = new_ages
    return sum(ages)

assert count_fish(80, './day 06/Xavier - Python/example.txt') == 5934

print(count_fish(80, './day 06/Xavier - Python/input.txt'))
print(count_fish(256, './day 06/Xavier - Python/input.txt'))
