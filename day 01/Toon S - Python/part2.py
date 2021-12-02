if __name__ == "__main__":
    with open("input.txt", "r") as f:
        input_list = list(map(int, f.readlines()))
        num_increases = 0
        for i in range(3, len(input_list)):
            window_1 = input_list[i - 3:i]
            window_2 = input_list[i - 2:i + 1]
            if sum(window_2) > sum(window_1):
                num_increases += 1
    print(num_increases)
