import numpy as np

file = open("Input.txt", "r")
splitRows = map(lambda line: filter(lambda char: char != "\n", list(line)), file.readlines())
matrix = np.array(map(lambda splitRow: map(int,splitRow),splitRows))

width = len(matrix[0])
height = len(matrix)

gammaRate = 0
epsilonRate = 0

for i in range(0,width):
    nbOfOnes = sum(matrix[:,i])
    valueToAdd = 2**(width-1-i)

    if (nbOfOnes > height/2):
        gammaRate += valueToAdd
    else:
        epsilonRate += valueToAdd

print("Gamma rate: {}, Epsilon rate: {}, Result: {}".format(gammaRate, epsilonRate, gammaRate*epsilonRate))
