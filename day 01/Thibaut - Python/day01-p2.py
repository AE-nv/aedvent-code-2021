
if __name__ == '__main__':
    with open("day01.txt", 'r') as f:
        data=f.readlines()
    counter = 0
    for index in range(0, len(data)-3):
        prev=int(data[index])
        print(index)
        print("n1:", prev, "n2", int(data[index + 3]))
        if prev<int(data[index+3]):
            counter+=1
    print("result",counter)