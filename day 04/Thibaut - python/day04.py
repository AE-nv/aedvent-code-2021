def findPos(board, number):
    for k,row in enumerate(board):
        for l,entry in enumerate(row):
            if entry==number:
                return (k,l)
    return (-1,-1)

def crossOf(board,n):
    row, col = findPos(board, n)
    if row<0: return;
    board[row][col]="X"

def bingoH(board):
    for row in board:
        if row.count('X')==5:
            return True
    return False

def bingoV(board):
    transpose = list(zip(*board))
    return bingoH(transpose)

def bingoD(board):
    d1=[];d2=[]
    for i in range(0,5):
        d1.append(board[i][4-i])
        d2.append(board[4-i][i])
    return bingoH(board)

def bingo(board):
    return bingoH(board) or bingoV(board) or bingoD(board)



def playBingo(boards, calledNumbers):
    for i,n in enumerate(calledNumbers):
        for board in boards:
            crossOf(board, n);
            if i>=4: #bingo is possible
                if bingo(board):
                    return (board, n)

def calculateScore(board):
    score=0
    for row in board:
        for entry in row:
            if entry!='X':
                score+=int(entry)
    return score

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    calledNumbers=data[0].split(',');

    boards=[[]]
    for line in data[2:]:
        if len(line.rstrip())==0:
            #empty line start new board
            boards.append([])
        else:
            boards[-1].append(line.rstrip().split())

    winner, number = playBingo(boards, calledNumbers)
    print("score:",int(number)*calculateScore(winner))

