if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()


    dancers="abcdefghijklmnop"
    instructions = data[0].strip().split(',')
    for instruction in instructions:
        command=instruction[0]
        pars = instruction[1:]
        if command=="s":
            length = int(pars)
            tail = dancers[-length:]
            dancers=tail+dancers[0:-length]
        elif command=="p":
            a1, a2 = pars.split('/')
            i1 = dancers.find(a1)
            i2 = dancers.find(a2)
            dancers = [char for char in dancers]
            dancers[i1]=a2
            dancers[i2]=a1
            dancers = "".join(dancers)
        elif command=="x":
            a1, a2 = [int(x) for x in pars.split('/')]
            print(a1, a2)
            dancers = [char for char in dancers]
            dancers[a1], dancers[a2] = dancers[a2], dancers[a1]
            dancers = "".join(dancers)

    print(dancers)
