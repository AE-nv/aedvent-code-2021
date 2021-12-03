def filterToSingularCommon(bits):
    bitArr = bits
    BitDict=[[],[]]
    bitIndex=0
    while True:
        countSet=0;
        for line in bitArr:
            if line[bitIndex]==1:
                countSet+=1
                BitDict[1].append(line)
            else:
                BitDict[0].append(line)
        if countSet>=len(bitArr)-countSet:
            #one is the prominent bit
            bitArr=BitDict[1]
        else:
            #0 is the prominent bit
            bitArr=BitDict[0]
        if len(bitArr)==1:
            return bitArr[0]
        bitIndex+=1
        BitDict=[[],[]]

def filterToSingularUncommon(bits):
    bitArr = bits
    BitDict=[[],[]]
    bitIndex=0
    while True:
        countSet=0;
        for line in bitArr:
            if line[bitIndex]==1:
                countSet+=1
                BitDict[1].append(line)
            else:
                BitDict[0].append(line)
        if countSet>=len(bitArr)-countSet:
            #one is the prominent bit
            bitArr=BitDict[0]
        else:
            #0 is the prominent bit
            bitArr=BitDict[1]
        if len(bitArr)==1:
            return bitArr[0]
        bitIndex+=1
        BitDict=[[],[]]



if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    bits = []
    for line in data:
        bits.append([int(char) for char in line.rstrip()])


    counts= [0 for pos in bits[0]]
    for line in bits:
        for bi, bit in enumerate(line):
            counts[bi]+=bit

    gam = []
    eps = []
    for bitCount in counts:
        if bitCount > 500:
            gam.append(1)
            eps.append(0)
        elif bitCount==500:
            print("edge!")
        else:
            gam.append(0)
            eps.append(1)

    print(gam)
    print(eps)
    gamma = int("".join(str(x) for x in gam), 2)
    epsilon = int("".join(str(x) for x in eps), 2)
    print("gamma:",gamma,"epsilon:",epsilon)
    print("product",gamma*epsilon)

    #P2:
    with open("input.txt", 'r') as f:
        data = f.readlines()

    bits = []
    for line in data:
        bits.append([int(char) for char in line.rstrip()])

    oxygenArr = filterToSingularCommon(bits)
    oxygenRate=int("".join(str(x) for x in oxygenArr), 2)

    CoArr = filterToSingularUncommon(bits)
    coRate = int("".join(str(x) for x in CoArr), 2)
    print("oxygen generator rating:",oxygenRate,"CO2 scrubber rating:",coRate)
    print("product:",coRate*oxygenRate)

    #MiniZinc input generator:
    with open("input.txt", 'r') as f:
        data = f.readlines()

    bits = []
    for line in data:
        for char in line.rstrip():
            bits.append(int(char))
    print(bits);
    print("byteSize:",len(data[0]))
    print("bytes:",len(data))



