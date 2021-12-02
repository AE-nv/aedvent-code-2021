
if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    mznData=[]
    for p in data:
        mznData.append(p.rstrip())
    print(len(mznData),mznData)

    x=0
    y=0
    aim=0
    for line in data:
        #print(line.split(" "))
        [command, n] = line.split(" ")
        if command=="up":
            aim-=int(n)
        elif command=="down":
            aim+=int(n)
        elif command=="forward":
            x+=int(n)
            y+=int(n)*aim
        else:
            print(line)

    print("x:",x,"y:",y,"x*y:",x*y)

