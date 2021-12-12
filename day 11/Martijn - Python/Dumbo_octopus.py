import numpy as np

def phase_one(matrix):
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    for row in range(0,nb_rows):
        for column in range(0, nb_columns):
            matrix[row][column] += 1
    return matrix

def check_inside_matrix(i, j, matrix):
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    return i >=0 and j >= 0 and i < nb_rows and j < nb_columns

def find_neighbours_index(row, column, matrix):
    '''
    (-1,-1)  (-1,0)  (-1,1)
    (0,-1)   (r,c)   (0,1)
    (1, -1)  (1,0)   (1,1)
    '''
    indices_neighbours = [[-1, -1],[-1, 0], [-1,1], [0, -1], [0, 1], [1, -1], [1, 0], [1,1]]
    neighbours = []
    for index_neighbour in indices_neighbours:
        i = row + index_neighbour[0]
        j = column + index_neighbour[1]
        if check_inside_matrix(i, j, matrix):
            neighbours.append((i,j))
    return neighbours

def increase_neighbours(row, column, matrix, flahed_list):
    neigbours = find_neighbours_index(row, column, matrix)
    for (i,j) in neigbours:
        if (i,j) not in flahed_list:
            matrix[i][j] += 1
    return matrix

def phase_two(matrix, flashed_list):
    flashed = False
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    for row in range(0,nb_rows):
        for column in range(0, nb_columns):
            if not (row, column) in flashed_list:
                energy = matrix[row][column]
                if energy > 9:
                    matrix = increase_neighbours(row, column, matrix, flashed_list)
                    flashed = True
                    matrix[row][column] = 0
                    flashed_list.append((row,column))
                    break
    return flashed, matrix, flashed_list

def step(energy_levels):
    energy_levels = phase_one(energy_levels)
    flashed = True
    flashed_list = []
    while flashed:
        flashed, energy_levels, flashed_list = phase_two(energy_levels, flashed_list)
    flashed_list.sort()
    return len(flashed_list), energy_levels

def sum_energy(matrix):
    energy = 0
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    for row in range(0,nb_rows):
        for column in range(0, nb_columns):
            element = matrix[row][column]
            energy += element
    return energy


if __name__ == '__main__':
    input = open('./day 11/Martijn - Python/example_input.txt').readlines()
    energy_levels = [[int(x) for x in line.strip()] for line in input]
    nb_flashed = 0
    simultaneous = False
    nb_steps = 0
    while not simultaneous:
        nb_steps += 1
        nb, energy_levels = step(energy_levels)
        total_energy = sum_energy(energy_levels)
        if total_energy == 0:
            simultaneous = True
            print(nb_steps)
        nb_flashed += nb
    print(nb_flashed)