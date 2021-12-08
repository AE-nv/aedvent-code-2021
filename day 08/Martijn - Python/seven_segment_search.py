def count_unique_line(line):
    nb = 0
    unique_list = [2, 4, 3, 7]
    for obs in line.split(' '):
        if len(obs) in unique_list:
            nb += 1
    return nb

if __name__ == '__main__':
    input = open('./day 08/Martijn - Python/input.txt').readlines()
    nb_unique = 0
    for line in input:
        line = line.strip()
        observation_output = line.split('|')[1]
        nb_line = count_unique_line(observation_output)
        nb_unique += nb_line
        print(nb_line)
    print(nb_unique)