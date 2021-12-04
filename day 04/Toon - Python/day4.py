file = open("day 04/Toon - Python/input", "r")
lines = file.readlines()

numbers = [int(x) for x in lines[0][:-1].split(',')]

boards = [[int(x) for x in line[:-1].split(' ') if x != '' ] for line in lines[2:]]
boards = [boards[i*6:i*6+5] for i in range(100)]

def contains_bingo(board):
  return -5 in [sum(line) for line in board] or -5 in [sum(line) for line in [list(i) for i in zip(*board)]]

found = False
for number in numbers:
  for i in range(len(boards)):
    boards[i] = [[-1 if x == number else x for x in line] for line in boards[i]]
    if contains_bingo(boards[i]):
      result = boards[i]
      result_number = number
      found = True
  if found:
    break

def sum_board(board):
  return sum([sum([0 if x == -1 else x for x in line]) for line in board])

print("part 1: %s" % (sum_board(result) * number))

boards = [[int(x) for x in line[:-1].split(' ') if x != '' ] for line in lines[2:]]
boards = [boards[i*6:i*6+5] for i in range(100)]

def find_last(boards, numbers):
  good_boards = []
  bad_boards = []
  for i in range(len(boards)):
    boards[i] = [[-1 if x == numbers[0] else x for x in line] for line in boards[i]]
    if not contains_bingo(boards[i]):
      good_boards += [boards[i]]
    else:
      bad_boards += [boards[i]]
  
  if len(good_boards) == 0:
    return sum_board(bad_boards[0]) * numbers[0]
  return find_last(good_boards, numbers[1:])


print("part 2: %s" % find_last(boards, numbers))