if __name__ == "__main__":
    with open("input.txt", "r") as f:
        input_list = f.readlines()
        x = 0
        y = 0
        for input in input_list:
            command, string_val = input.split(" ")
            val = int(string_val)
            if command == "forward":
                x += val
            elif command == "down":
                y += val
            elif command == "up":
                y -= val
            else:
                raise ValueError(f"{command} is not a valid command")
        print(x * y)
