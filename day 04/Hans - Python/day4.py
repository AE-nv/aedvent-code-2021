from collections import namedtuple
from typing import List, Dict, Tuple, Set

BoardField = namedtuple("BoardField", "number marked")
PlayedNumber = namedtuple("PlayedNumber", "number row column")
BingoWinner = namedtuple("BingoWinner", "board_number score")

BOARD_SIZE = (5, 5)


class Board:
    def __init__(self, nr_rows: int, nr_columns: int):
        self.numbers = {}
        self.nr_of_rows = nr_rows
        self.nr_of_cols = nr_columns
        self.played_numbers = []
        self.board: List[List[BoardField]] = []

    def add_row(self, row_numbers: List[int]):
        self.board.append([BoardField(i, False) for i in row_numbers])

        self.numbers.update({nr: (len(self.board) - 1, i) for i, nr in enumerate(row_numbers)})

    def get_column(self, column_nr: int, marked: bool = False) -> Dict[int, int]:
        return {bf[column_nr].number: row_number for row_number, bf in enumerate(self.board) if
                bf[column_nr].marked == marked}

    def get_row(self, row_nr: int, marked: bool = False) -> Dict[int, int]:
        return {bf.number: column_number for column_number, bf in enumerate(self.board[row_nr]) if bf.marked == marked}

    def mark_number(self, row: int, column: int):
        bf = self.board[row][column]
        self.board[row][column] = BoardField(bf.number, True)

    def play_number(self, number: int):
        row, column = self.numbers.get(number, (-1, -1))

        if row == -1:
            return

        self.mark_number(row, column)
        self.played_numbers.append(PlayedNumber(number, row, column))

    def is_won(self) -> bool:
        pn = self.played_numbers[-1] if self.played_numbers else PlayedNumber(0, -1, -1)

        if pn.row == -1:
            return False

        marked_in_row = self.get_row(pn.row, marked=True)
        marked_in_column = self.get_column(pn.column, marked=True)

        return (len(marked_in_row) == self.nr_of_rows) or (len(marked_in_column) == self.nr_of_cols)

    def get_score(self) -> int:
        if not self.is_won():
            raise BaseException("This board is not the winner, score cannot be calculated")

        score = 0
        for r in range(self.nr_of_rows):
            score += sum(self.get_row(r, marked=False).keys())

        return score * self.played_numbers[-1].number

    def reset(self):
        self.played_numbers = []

        for i in range(self.nr_of_rows):
            for j in range(self.nr_of_cols):
                current_value = self.board[i][j]
                self.board[i][j] = BoardField(current_value.number, False)


class Bingo:

    def __init__(self):
        self.boards: Dict[int, Board] = {}
        self.nr_of_boards = 0
        self.winners: List[BingoWinner] = []
        self.boards_out: Set[int] = set()

    def add_board(self, board: Board):
        self.boards[self.nr_of_boards + 1] = board

        self.nr_of_boards += 1

    def play(self, number: int) -> List[Board]:
        if len(self.winners) == self.nr_of_boards:
            raise GameEndedException(
                f"Bingo game has been played! The winner is board - {self.winners[0].board_number}")

        for b_number in self.boards.keys():
            if b_number in self.boards_out:
                continue

            current_board = self.boards[b_number]

            current_board.play_number(number)

            if current_board.is_won():
                self.winners.append(BingoWinner(b_number, current_board.get_score()))
                self.boards_out.add(b_number)

    def reset(self):
        self.winners = []
        self.boards_out.clear()
        for b in self.boards.values():
            b.reset()


def parse_input(input_lines: List[str]) -> Tuple[Bingo, List[int]]:
    splits = input_lines.splitlines()
    input_numbers = list(map(int, splits[0].split(',')))

    bingo = Bingo()
    boards = {}
    nr_boards = 0

    for line in splits[1:]:
        if not line:
            nr_boards += 1
            boards[nr_boards] = Board(*BOARD_SIZE)

            continue

        boards[nr_boards].add_row(list(map(int, line.split())))

    for board in boards.values():
        bingo.add_board(board)

    return bingo, input_numbers


class GameEndedException(Exception):
    pass


def part_1(bingo: Bingo, numbers: List[int]):
    for n in numbers:
        bingo.play(n)

        if bingo.winners:
            break

    return bingo.winners[0]


def part_2(bingo: Bingo, numbers: List[int]):
    for n in numbers:
        try:
            bingo.play(n)
        except GameEndedException as e:
            break

    return bingo.winners[-1]


if __name__ == '__main__':
    bingo, numbers = parse_input(open("./input.txt", "r").read())
    print(f"Solution of part 1: {part_1(bingo, numbers)}")

    bingo.reset()
    print(f"Solution of part 2: {part_2(bingo, numbers)}")
