import numpy as np


def read_bingo_input(file):
    with open(file, "r") as f:
        numbers_drawn = list(map(int, f.readline().split(",")))

        boards = []
        while f.readline():
            board_numbers = np.zeros((5, 5), dtype="int")
            board_crosses = np.zeros((5, 5), dtype="int")

            for i in range(5):
                board_numbers[i] = np.array(f.readline().split())

            boards.append((board_numbers, board_crosses))

        return numbers_drawn, boards


def cross_off(number, board):
    board_numbers, board_crosses = board
    for i in range(5):
        for j in range(5):
            if board_numbers[i, j] == number:
                board_crosses[i, j] = 1
    return board


def has_bingo(board):
    _, board_crosses = board
    bingo_on_columns = 5 in sum(board_crosses)
    bingo_on_rows = 5 in sum(board_crosses.transpose())
    return bingo_on_rows or bingo_on_columns


def get_score(number, board):
    board_numbers, board_crosses = board
    board_remaining = -1 * board_numbers * (board_crosses - 1)
    return sum(sum(board_remaining)) * number


def play_bingo_1(numbers_drawn, boards):
    for number in numbers_drawn:
        for board in boards:
            board = cross_off(number, board)
            if has_bingo(board):
                return get_score(number, board)


def play_bingo_2(numbers_drawn, boards):
    board_ids_with_bingo = []
    for number in numbers_drawn:
        for board_id, board in enumerate(boards):
            if board_id not in board_ids_with_bingo:
                board = cross_off(number, board)
                if has_bingo(board):
                    board_ids_with_bingo.append(board_id)
                    if len(board_ids_with_bingo) == len(boards):
                        return get_score(number, board)


if __name__ == "__main__":
    nums, boards = read_bingo_input("input.txt")
    score_1 = play_bingo_1(nums, boards)
    score_2 = play_bingo_2(nums, boards)
    print(f"Score part 1: {score_1}")
    print(f"Score part 2: {score_2}")
