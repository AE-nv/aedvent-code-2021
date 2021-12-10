import numpy as np

def parse_input(heights):
    nb_rows = len(heights)
    nb_columns = len(heights[0])
    heights_array = []
    for line in heights:
        for height in line:
            heights_array.append(int(height))
    heights_array = np.array(heights_array)
    matrix = heights_array.reshape((nb_rows, nb_columns))
    return matrix

def check_inside_matrix(i, j, matrix):
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    return i >=0 and j >= 0 and i < nb_rows and j < nb_columns

def find_neighbours(row, column, matrix):
    indices_neighbours = [[-1, 0], [0, -1], [0, 1], [1, 0]]
    neighbours = []
    for index_neighbour in indices_neighbours:
        i = row + index_neighbour[0]
        j = column + index_neighbour[1]
        if check_inside_matrix(i, j, matrix):
            neighbours.append(matrix[i][j])
    return neighbours

def find_neighbours_index(row, column, matrix):
    indices_neighbours = [[-1, 0], [0, -1], [0, 1], [1, 0]]
    neighbours = []
    for index_neighbour in indices_neighbours:
        i = row + index_neighbour[0]
        j = column + index_neighbour[1]
        if check_inside_matrix(i, j, matrix):
            neighbours.append((i,j))
    return neighbours

def find_low_points(matrix):
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    low_points = []
    indices = []
    for i in range(0, nb_rows):
        for j in range(0, nb_columns):
            current = matrix[i][j]
            neighbours = find_neighbours(i, j, matrix)
            smallest_neighbour = min(neighbours)
            if current < smallest_neighbour:
                low_points.append(current)
                indices.append((i,j))
    return low_points, indices

def find_bassin_rec(row, column, matrix):
    result = [(row, column)]
    neighbours = find_neighbours_index(row, column, matrix)
    current = matrix[row][column]
    for neighbour_index in neighbours:
        neighbour = matrix[neighbour_index[0]][neighbour_index[1]]
        if neighbour > current and neighbour != 9:
            result.append((neighbour_index[0], neighbour_index[1]))
            result += find_bassin_rec(neighbour_index[0], neighbour_index[1], matrix)
        
    return result

if __name__ == '__main__':
    heights = [x.strip() for x in open('./day 09/Martijn - Python/input.txt').readlines()]
    heights_map = parse_input(heights)
    low_points, indices_low_points = find_low_points(heights_map)
    values = [ x+1 for x in low_points]
    print(sum(values))
    basins = []
    for low_point in indices_low_points:
        basin = find_bassin_rec(low_point[0], low_point[1], heights_map)
        basins.append(len(set(basin)))
    basins.sort(reverse=True)
    print(basins)
    print(basins[0] * basins[1] * basins[2])