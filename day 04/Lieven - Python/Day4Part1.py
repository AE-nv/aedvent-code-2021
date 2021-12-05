# Python2.7
# O(n1*n2) where n1 = number of drawn numbers, n2 = number of playing boards

import numpy as np

# Class representing a playing board
class Board:
    def __init__(self, boardId, nbRows, nbCols):
        self.boardId = boardId
        self.rows = [0 for _ in range(nbRows)]
        self.columns = [0 for _ in range(nbCols)]
        self.boardCoordinates = []
    
    def markCoordinate(self, row, col):
        self.rows[row] += 1
        self.columns[col] += 1
        if (self.rows[row] == len(self.columns) or self.columns[col] == len(self.rows)):
            return True
        return False
    
    def addBoardCoordinate(self, boardCoordinate):
        self.boardCoordinates.append(boardCoordinate)

# Class representing a position on a Board
class BoardCoordinate:
    def __init__(self, row, col, val, board):
        self.row = row
        self.col = col
        self.value = val
        self.board = board
        self.marked = False

    def markNum(self):
        self.marked = True
        return self.board.markCoordinate(self.row, self.col)

def filterNewlines(str):
    return filter(lambda char: char != "\n", str)

def filterEmptyStr(arr):
    return filter(lambda el: el != "", arr)

# Given an array of lines read from the input file returns an array
# of matrices representing the playing boards from the input
def getBoards(lines):
    boards = []
    currBoard = []
    for line in lines:
        if(line == "\n"):
            boards.append(np.array(currBoard))
            currBoard = []
        else:
            currBoard.append(map(int, filterEmptyStr(filterNewlines(line).split(" "))))

    boards.append(np.array(currBoard))
    return boards

# Given a list of matrices (playing boards) returns a hashmap that
# maps numbers (that can be drawn during the game) to boardCoordinate
# objects that represent the coordinates of a playing board that has
# the given number as value on the location represented by the coordinates
# Returns: HashMap<int,BoardCoordinate[]>
def initializeBoardCoordinates(boards):
    numberToBoardCoordinates = dict()
    for idx,board in enumerate(boards):
        boardObject = Board(idx,len(board[0]),len(board))
        for rowIdx,row in enumerate(board):
            for colIdx,val in enumerate(row):
                boardCoordinate = BoardCoordinate(rowIdx,colIdx,val,boardObject)
                boardObject.addBoardCoordinate(boardCoordinate)
                if(val in numberToBoardCoordinates.keys()):
                    numberToBoardCoordinates[val].append(boardCoordinate)
                else:
                    numberToBoardCoordinates[val] = [boardCoordinate]
    
    return numberToBoardCoordinates
                
# Calculates the winner's score after bingo has been found for a board
def calculateScore(lastNumberDrawn, winningBoard):
    score = 0
    for boardCoordinate in winningBoard.boardCoordinates:
        if (not boardCoordinate.marked):
            score += boardCoordinate.value
    
    return score*lastNumberDrawn

# =============================================================

file = open("Input.txt", "r")
lines = file.readlines()
drawnNumbers = map(int, filterNewlines(lines[0]).split(","))
boards = getBoards(lines[2:])
# HashMap<int,BoardCoordinate[]>
numberToBoardCoordinates = initializeBoardCoordinates(boards)
winnerBoard = None
lastNumberDrawn = -1
winnerScore = -1

for drawnNumber in drawnNumbers:
    if (winnerBoard != None):
        break
    
    lastNumberDrawn = drawnNumber
    for boardCoordinate in numberToBoardCoordinates[drawnNumber]:
        if (boardCoordinate.markNum()):
            winnerBoard = boardCoordinate.board
            break

winnerScore = calculateScore(lastNumberDrawn,winnerBoard)
print("Winning board is board number {} with a score of {}".format(winnerBoard.boardId, winnerScore))






