if __name__ == "__main__":
    with open("input.txt", "r") as f:
        input_list = f.readlines()
        x = 0
        y = 0
        aim = 0
        for input in input_list:
            command, string_val = input.split(" ")
            val = int(string_val)
            if command == "forward":
                x += val
                y += aim*val
            elif command == "down":
                aim += val
            elif command == "up":
                aim -= val
            else:
                raise ValueError(f"{command} is not a valid command")
        print(x * y)