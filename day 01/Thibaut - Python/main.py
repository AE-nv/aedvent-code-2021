# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    with open("day01.txt", 'r') as f:
        data=f.readlines()
    prev = int(data[0])
    counter = 0
    for data_point in data[1:]:
        if prev<int(data_point):
            counter+=1
        prev=int(data_point)
    print("result",counter)

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
