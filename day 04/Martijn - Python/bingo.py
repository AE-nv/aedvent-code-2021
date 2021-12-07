import numpy as np
class BingoBoard:
    def __init__(self, numbers, dimension_x, dimension_y):
        self.numbers = np.array(numbers, dtype=float)
        self.board = np.reshape(self.numbers, (dimension_x, dimension_y))
        self.last_number = np.NaN
        
    def draw_number(self, number):
        self.last_number = number
        indices_rows = np.where(self.board == number)
        coordinates_rows = list(zip(indices_rows[0], indices_rows[1]))
        for coordinate in coordinates_rows:
            x = coordinate[0]
            y = coordinate[1]
            self.board[x, y] = np.NaN 

    
    def check_bingo(self):
        count_numbers_in_rows = np.count_nonzero(~np.isnan(self.board), axis=1)
        count_numbers_in_columns = np.count_nonzero(~np.isnan(self.board), axis=0)

        bingo = False
        for count_row in count_numbers_in_rows:
            if count_row == 0:
                bingo = True
        for count_column in count_numbers_in_columns:
            if count_column == 0:
                bingo = True
        return bingo

    def calculate_score(self):
        sum = np.nansum(self.board)
        return sum * self.last_number
        
    

def parse_input_to_boards(input):
    board = []
    board_dict = {}
    dim_x = 0
    dim_y = 0
    for index,line in enumerate(boards_raw):
        row = [int(nb) for nb in line.split()]
        if len(row) > 0:
            dim_x = len(row)
            dim_y += 1
            board.extend(row)
        else:
            board_dict[index] = BingoBoard(board, dim_x, dim_y)
            board = []
            dim_y = 0
    return board_dict

def play_bingo(numbers, boards):
    winner_keys = set()
    for number in numbers:
        for (key, board) in boards.items():
            if not key in winner_keys:
                board.draw_number(number)
                if board.check_bingo():
                    if len(winner_keys) == 0:
                        print("the winner score is: " + str(board.calculate_score())+ " with " + str(board.last_number))
                    if len(boards) - len(winner_keys) > 1:
                        winner_keys.add(key)
                    else:
                        print("the loser score is: " + str(board.calculate_score())+ " with " + str(board.last_number))
                        winner_keys.add(key)
                
    

if __name__ == '__main__':
    input = open('./day 04/Martijn - Python/input.txt').read().splitlines()

    bingo_numbers = [int(nb) for nb in input[0].split(',')]
    boards_raw = input[2:]
    boards_dict = parse_input_to_boards(boards_raw)
    play_bingo(bingo_numbers, boards_dict)
    

        

    # bb = BingoBoard([1,2,3,2],2,2)
    # print(bb.check_bingo())
    # bb.draw_number(2)
    # print(bb.check_bingo())
    # print(bb.calculate_score())

