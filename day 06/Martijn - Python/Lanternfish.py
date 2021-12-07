
def pass_day_dict(yesterday):
    today = {}
    for x in range(0,8):
        today[x] = yesterday[x+1]
    today[8] = yesterday[0]
    today[6] += yesterday[0]
    return today


def print_nb_fishes(state_dict):
    sum = 0
    for nb_days in state_dict:
        sum += int(state_dict[nb_days])
    print(sum)


def pass_day(fish_list):
    new = [x-1 for x in fish_list]
    for i in range(0,len(fish_list)):
        if new[i] == -1:
            new[i] = 6
            new.append(8)
    return new

if __name__ == '__main__':
    state_list = [int(x) for x in open('./day 06/Martijn - Python/input.txt').readline().split(',')]
    # 'brute unscalable'force
    # for x in range(0, 11):
    #     state_list = pass_day(state_list)
    # print(len(state_list))

    state_dict = {}
    for nb_days in range(0,9):
        state_dict[nb_days] = 0

    
    for state in state_list:
        state_dict[state] += 1

    
    for x in range(0, 256):
        state_dict = pass_day_dict(state_dict)
        print_nb_fishes(state_dict)
    

