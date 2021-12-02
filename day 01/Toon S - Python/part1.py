if __name__ == "__main__":
    with open("input.txt", "r") as f:
        input_list = f.readlines()
        num_increases = 0
        for i in range(1, len(input_list)):
            if int(input_list[i]) > int(input_list[i - 1]):
                num_increases += 1
        print(num_increases)
