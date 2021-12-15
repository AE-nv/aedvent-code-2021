import numpy as np
from skimage.graph import route_through_array

def solve(matrix):
    start=[0,0]
    end=[matrix.shape[0]-1,matrix.shape[1]-1]
    path, score = route_through_array(matrix, start, end, fully_connected=False)
    scoreCheck=sum([matrix[node[0], node[1]] for node in path[1:]])
    print(scoreCheck)

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    matrix = np.array([[int(c) for c in l.strip()] for l in data])
    expandedMatrixShape = [matrix.shape[0]*5,matrix.shape[1]*5]
    expandedMatrix = np.zeros(expandedMatrixShape)
    expandedMatrix[0:100,0:100] = matrix
    for r in range(0,5):
        for c in range(0,5):
            #print(r,c)
            tmp = np.array(matrix)
            for i in range(0,r+c):
                tmp+=1
                tmp = np.where(tmp > 9, 1, tmp)
            #print(tmp)
            expandedMatrix[r*100:(r+1)*100,c*100:(c+1)*100]= tmp

    print("P1:")
    solve(matrix)
    print("P2:")
    solve(expandedMatrix)

