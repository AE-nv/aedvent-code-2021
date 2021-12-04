from termcolor import colored


class Board:

    def __init__(self):
        self.numbers = []
        self.played_numbers = []

    def add_row(self, numbers):
        self.numbers.append(numbers)

    def get_row(self, index):
        return self.numbers[index]

    def get_column(self, index):
        return list(map(lambda r: r[index], self.numbers))

    def play(self, number):
        self.played_numbers.append(number)
        if self.is_winner():
            print(colored('Winner, winner, chicken dinner! \n', 'red'))
        print(self)

    def is_winner(self):
        for i in range(5):
            if all(n in self.played_numbers for n in self.get_row(i)):
                return True
            if all(n in self.played_numbers for n in self.get_column(i)):
                return True
        return False

    def get_all_board_numbers(self):
        all_nums = []
        for row in self.numbers:
            all_nums.extend(row)
        return all_nums

    def get_score(self):
        return int(self.played_numbers[-1]) \
               * sum(map(int, [n for n in self.get_all_board_numbers() if n not in self.played_numbers]))

    def __str__(self):
        def format_square(n):
            return n if self.played_numbers.__contains__(n) else colored(n, 'green')
        return '\n'.join(['\t'.join([format_square(n) for n in row]) for row in self.numbers]) + '\n'


class Bingo(Exception):

    def __init__(self, b):
        self.board = b


data = open('input.txt', 'r').read().splitlines()
numbers_to_play = data.pop(0).split(',')
boards = []
board = None

for line in data:
    if line == '':
        board = Board()
        boards.append(board)
    else:
        board.add_row(line.split())

try:
    for n in numbers_to_play:
        print("Playing number", n, "------ \n")
        for board in boards:
            board.play(n)
            if board.is_winner():
                raise Bingo(board)
except Bingo as win:
    print("Part 1:", win.board.get_score())
    pass

completed_boards = []
for n in numbers_to_play:
    remaining_boards = [b for b in boards if not completed_boards.__contains__(b)]
    if len(remaining_boards) == 0:
        break
    print("Playing number", n, "------", len(remaining_boards), "boards remaining..\n")
    for board in remaining_boards:
        board.play(n)
        if board.is_winner():
            completed_boards.append(board)

print("Part 2:", completed_boards[-1].get_score())
