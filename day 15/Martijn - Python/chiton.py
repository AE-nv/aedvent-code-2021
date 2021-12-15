import numpy as np
from dijkstar import Graph, find_path

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
            neighbours.append([matrix[i][j], str(i), str(j)])
    return neighbours

def create_graph(matrix):
    graph = Graph()
    nb_rows = len(matrix)
    nb_columns = len(matrix[0])
    for i in range(0, nb_rows):
        for j in range(0, nb_columns):
            current = str(i) +'_' + str(j)
            neighbours = find_neighbours(i, j, matrix)
            for neighbour in neighbours:
                graph.add_edge(current, neighbour[1] + '_' + neighbour[2], neighbour[0])
    return graph


def parse_line(line, row_index, matrix):
    nb_chitons = len(line)
    
    for x in range(0, nb_chitons):
        matrix[row_index][x] = int(line[x])
    return matrix

def add_to_tile(tile, extra):
    new = tile + extra
    nb_rows = len(tile)
    nb_columns = len(tile[0])
    for i in range(0, nb_rows):
        for j in range(0, nb_columns):
            if new[i][j] >= 10:
                new[i][j] = (new[i][j]) % 10 + 1
    return new

def create_full_map(tile):
    add_matrix = [[0,1,2,3,4],[1,2,3,4,5],[2,3,4,5,6],[3,4,5,6,7],[4,5,6,7,8] ]
    map = []
    for row in add_matrix:
        new_row_list = []
        for x in row:
            new_tile = add_to_tile(tile, x)
            new_row_list.append(new_tile)
        new_row = np.hstack(new_row_list)
        map.append(new_row)
    map = np.vstack(map)
    return map





if __name__ == '__main__':
    input = open('./day 15/Martijn - Python/input.txt').readlines()
    nb_lines = len(input)
    nb_columns = len(input[0].strip())
    matrix = np.zeros((nb_lines, nb_columns))
    full_matrix = np.zeros((nb_lines * 5, nb_columns * 5))
    for x in range(0,len(input)):
        matrix = parse_line(input[x].strip(), x, matrix)
    graph = create_graph(matrix)
    start = '0_0'
    end = str(nb_lines -1) + '_' + str(nb_columns -1)
    path_1 = find_path(graph, start, end)
    print(path_1)
    map = create_full_map(matrix)
    full_graph = create_graph(map)
    start = '0_0'
    end = str(nb_lines * 5 -1) + '_' + str(nb_columns * 5 -1)
    path_2 = find_path(full_graph, start, end)
    print(path_2)
