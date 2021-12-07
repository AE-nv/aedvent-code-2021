import pandas as pd
import numpy as np

def parse_row(row):
    split = row.split()
    x1 = int(split[0].split(',')[0])
    y1 = int(split[0].split(',')[1])
    x2 = int(split[2].split(',')[0])
    y2 = int(split[2].split(',')[1])
    return [x1, y1, x2, y2]

def find_dimensions(df):
    df_x = df[['x1', 'x2']]
    max_x_list = df_x.max(axis=0)
    df_y = df[['y1', 'y2']]
    max_y_list = df_y.max(axis=0)
    max_x = max(max_x_list)
    max_y = max(max_y_list)
    return [max_x, max_y]

def enpoints_to_line(row):
    # TODO find cleaner solution diagonal lines
    x1 = row['x1']
    x2 = row['x2']
    y1 = row['y1']
    y2 = row['y2']
    list_co = []
    print(x1, y1, x2, y2)
    # horizontal line
    if x1 == x2:
        ymin = min(y1, y2)
        ymax = max(y1, y2)
        current =  ymin
        while current <= ymax:
            list_co.append([x1,current])
            current += 1
    # vertical line
    elif y1 == y2:
        xmin = min(x1, x2)
        xmax = max(x1, x2)
        current =  xmin
        while current <= xmax:
            list_co.append([current,y1])
            current += 1  
    # right up
    elif x1 < x2 and y1 < y2:
        current_x = x1
        current_y = y1
        while current_x <= x2:
            list_co.append([current_x, current_y])
            current_x += 1  
            current_y += 1
    # right down
    elif x1 < x2 and y1 > y2:
        current_x = x1
        current_y = y1
        while current_x <= x2:
            list_co.append([current_x, current_y])
            current_x += 1  
            current_y -= 1
    # left up
    elif x1 > x2 and y1 < y2:
        current_x = x1
        current_y = y1
        while current_x >= x2:
            list_co.append([current_x, current_y])
            current_x -= 1  
            current_y += 1
    # left down
    elif x1 > x2 and y1 > y2:
        current_x = x1
        current_y = y1
        while current_x >= x2:
            list_co.append([current_x, current_y])
            current_x -= 1  
            current_y -= 1
    
    return list_co

def append_lines(df, line):
    for coordinate in line:
        x = coordinate[0]
        y = coordinate[1]
        df[x][y] += 1
    return df





if __name__ == '__main__':
    input = open('./day 05/Martijn - Python/input.txt').read().splitlines()
    parsed = []
    for row in input:
        parsed.append(parse_row(row))
    df = pd.DataFrame(parsed, columns= ['x1', 'y1', 'x2', 'y2'])
    [max_x, max_y] = find_dimensions(df)
    df_ocean = pd.DataFrame(np.zeros((max_x+1, max_y+1)))
    list_of_lines = df.apply(enpoints_to_line, axis=1)
    for index, line in list_of_lines.items():
        df_ocean = append_lines(df_ocean, line)
    nb = df_ocean[df_ocean >= 2 ].count().sum()
    print(nb)
    
    

