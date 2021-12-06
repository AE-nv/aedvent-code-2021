def count_fish(days):
    school = [0] * 9
    for fish in open('input.txt', 'r').read().split(','):
        school[int(fish)] += 1
    for i in range(days):
        fish_about_to_pop = school.pop(0)
        school[6] += fish_about_to_pop
        school.append(fish_about_to_pop)
    return sum(school)


print(f"Part 1: {count_fish(80)}")
print(f"Part 2: {count_fish(256)}")
