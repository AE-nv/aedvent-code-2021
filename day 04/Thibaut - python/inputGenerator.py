if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    calledNumbers = list(map(lambda x: int(x), data[0].split(',')))
    print("nNumbers=",len(calledNumbers))
    print("calledNumbers=",calledNumbers)

    rawBoards = []
    boardCount = 1
    boardSize = len(data[2].rstrip().split())
    for line in data[2:]:
        if len(line.rstrip()) != 0:
            rawBoards = rawBoards + list(map(lambda x: int(x), line.rstrip().split()))
        else:
            boardCount += 1
    print("nBoards=",boardCount)
    print("boardSize=",boardSize)
    print("rawBoards=",rawBoards)



