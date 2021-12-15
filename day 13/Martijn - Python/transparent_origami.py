import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import LinearSegmentedColormap

def create_matrix(dots):
    max_x = get_maximum_list_tuples(dots, 0) 
    max_y = get_maximum_list_tuples(dots, 1) 
    matrix = np.zeros((max_y+1, max_x+1))
    for (x,y) in dots:
        matrix[y][x] += 1
    return matrix

def get_maximum_list_tuples(list, index):
    max = -1
    for coordinate in list:
        if coordinate[index] > max:
            max = coordinate[index]
    return max

def fold(matrix, instruction):
    axis = instruction.split('=')[0]
    value = int(instruction.split('=')[1])
    if  axis == 'x':
        for row in matrix:
            nb_columns = len(row)
            diff = nb_columns - value
            row[value] = 0

            for x in range(1, diff):
                row[value-x] += row[value+x]
                row[value + x] = 0
        matrix = np.delete(matrix, slice(value,nb_columns-1), axis=1)
    else:
        nb_rows = len(matrix)
        diff = nb_rows - value 
        for x in range(0, diff):
            if x == 0:
                row = matrix[value]
                for c in range(0, len(row)):
                    row[c] = 0
            else:
                row_1 = matrix[value - x]
                row_2 = matrix[value + x]
                for c in range(0, len(row_1)):
                    row_1[c] += row_2[c]
                    row_2[c] = 0
        matrix = np.delete(matrix, slice(value,nb_rows-1), axis=0)

    return matrix

def count_visible_dots(matrix):
    return np.count_nonzero(matrix)

def visualize_matrix(matrix):
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    for y in range(0, nb_rows):
        for x in range(0, nb_columns):
            if matrix[y][x] >= 1:
                matrix[y][x] = 1
    colors = [(0, 0, 1), (0, 1, 1), (0, 1, 0.75), (0, 1, 0), (0.75, 1, 0), (1, 1, 0), (1, 0.8, 0), (1, 0.7, 0), (1, 0, 0)]
    cm = LinearSegmentedColormap.from_list('sample', colors)
    fig, ax = plt.subplots()
    ax.imshow(matrix, cmap=cm, alpha=1)
    plt.show()

if __name__ == '__main__':
    input = open('./day 13/Martijn - Python/input.txt').readlines()
    dots = [(int(line.strip().split(',')[0]), int(line.strip().split(',')[1])) for line in input if len(line.split(',')) == 2]
    matrix = create_matrix(dots)
    instructions = [line.strip().split(' ')[2] for line in input if len(line.split(' ')) == 3]
    for instruction in instructions:
        matrix = fold(matrix, instruction)
    count = count_visible_dots(matrix)
    print(count)
    print(matrix)
    visualize_matrix(matrix)