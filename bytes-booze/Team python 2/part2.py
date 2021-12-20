def spin(dancers, pars):
    length = int(pars)
    tail = dancers[-length:]
    return tail + dancers[0:-length]


def partner(dancers, pars):
    a1, a2 = pars.split('/')
    i1 = dancers.find(a1)
    i2 = dancers.find(a2)
    dancers = [char for char in dancers]
    dancers[i1] = a2
    dancers[i2] = a1
    return "".join(dancers)


def exchange(dancers, pars):
    a1, a2 = [int(x) for x in pars.split('/')]
    dancers = [char for char in dancers]
    dancers[a1], dancers[a2] = dancers[a2], dancers[a1]
    return "".join(dancers)

def parseSequence(sequence):
    start = sequence[0][0]
    end = sequence[-1][-1]
    enumerate(start)
    start = dict([(c, i) for i, c in enumerate(start)])
    end = dict([(c, i) for i, c in enumerate(end)])
    permutation={}
    for c in "abcdefghijklmnop":
        permutation[start[c]]=end[c]
    return permutation

def swap(dancers, i1, i2):
    dancers = [char for char in dancers]
    dancers[i1], dancers[i2] = dancers[i2], dancers[i1]
    return "".join(dancers)

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    dancers = "abcdefghijklmnop"
    print(dancers)
    instructions = data[0].strip().split(',')
    for i in range(0, 34):
        for instruction in instructions:
            command = instruction[0]
            pars = instruction[1:]
            if command == "s":
                length = int(pars)
                tail = dancers[-length:]
                dancers = tail + dancers[0:-length]
            elif command == "p":
                a1, a2 = pars.split('/')
                i1 = dancers.find(a1)
                i2 = dancers.find(a2)
                dancers = [char for char in dancers]
                dancers[i1] = a2
                dancers[i2] = a1
                dancers = "".join(dancers)
            elif command == "x":
                a1, a2 = [int(x) for x in pars.split('/')]
                dancers = [char for char in dancers]
                dancers[a1], dancers[a2] = dancers[a2], dancers[a1]
                dancers = "".join(dancers)
        if dancers == "abcdefghijklmnop":
            print("found it")
            break;

    print(dancers)