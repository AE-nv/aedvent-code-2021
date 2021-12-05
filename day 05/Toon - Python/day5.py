from os import DirEntry
import unittest
file = open("day 05/Toon - Python/input", "r")


def split_line_left_right(line):
    return line.replace('\r\n', '').split(' -> ')


def split_coordinates(line):
    return [int(a) for tup in [element.split(',') for element in line] for a in tup]


def unpack_list(list):
    return [a for tup in list for a in tup]


def lines_to_list_of_end_coordinates(lines):
    return [split_coordinates(split_line_left_right(line)) for line in lines]


def is_not_diagonal(line):
    return line[0] == line[2] or line[1] == line[3]


def get_non_diagonals(lines):
    return [line for line in lines if is_not_diagonal(line)]

def get_diagonals(lines):
  return [line for line in lines if not is_not_diagonal(line)]

def get_coordinates_on(line):
    if line[0] < line[2]:
        x1 = line[0]
        x2 = line[2]
    else:
        x1 = line[2]
        x2 = line[0]
    if line[1] < line[3]:
        y1 = line[1]
        y2 = line[3]
    else:
        y1 = line[3]
        y2 = line[1]

    return [[x, y] for x in range(x1, x2+1) for y in range(y1, y2+1)]

def get_coordinates_on_diagonal(line):
    if line[0] < line[2]:
      x = line[0]
      y = line[1]
      length = line[2]-line[0]
      if line[1] < line[3]:
        direction = 1
      else:
        direction = -1

    else:
      x = line[2]
      y = line[3]
      length = line[0]-line[2]
      if line[1] < line[3]:
        direction = -1
      else:
        direction = 1
    
    return [[x+i, y+ (direction * i)] for i in range(length+1)]


def add_to_dictionary(dictionary, coordinate):
    coordinate_string = str(coordinate)
    if coordinate_string in dictionary:
        dictionary[coordinate_string] += 1
    else:
        dictionary[coordinate_string] = 1

def filter_ones(dictionary):
    new_dictionary = dict()
    for key in dictionary.keys():
      if dictionary[key] != 1:
        new_dictionary[key] = dictionary[key]
    return new_dictionary


ends_list = lines_to_list_of_end_coordinates(file.readlines())

lines = get_non_diagonals(ends_list)
coordinates = unpack_list([get_coordinates_on(line) for line in lines])
dictionary = dict()
[add_to_dictionary(dictionary, coordinate) for coordinate in coordinates]
new_dictionary = filter_ones(dictionary)
count = len(new_dictionary.keys())

print("part 1: %s" % str(count))

diagonal_lines = get_diagonals(ends_list)
diagonal_coordinates = unpack_list([get_coordinates_on_diagonal(line) for line in diagonal_lines])
all_coordinates= diagonal_coordinates + coordinates
diagonal_dictionary = dict()
[add_to_dictionary(diagonal_dictionary, coordinate) for coordinate in all_coordinates]
new_diagonal_dictionary = filter_ones(diagonal_dictionary)
diagonal_count = len(new_diagonal_dictionary.keys())

print("part 2: %s" % str(diagonal_count))

class Tests(unittest.TestCase):

    def test_split_line_left_right(self):
        self.assertEqual(split_line_left_right(
            '232,604 -> 232,843\r\n'), ['232,604', '232,843'])

    def test_split_coordinates(self):
        self.assertEqual(split_coordinates(
            ['232,604', '232,843']), [232, 604, 232, 843])

    def test_lines_to_list_of_coordinates(self):
        self.assertEqual(lines_to_list_of_end_coordinates(
            ['232,604 -> 232,843\r\n', '232,604 -> 232,843\r\n']), [[232, 604, 232, 843], [232, 604, 232, 843]])

    def test_is_not_diagonal(self):
        self.assertTrue(is_not_diagonal([232, 604, 232, 843]))
        self.assertTrue(is_not_diagonal([504, 604, 232, 604]))
        self.assertFalse(is_not_diagonal([504, 604, 232, 843]))

    def test_get_non_diagonals(self):
        self.assertEqual(get_non_diagonals([[232, 604, 232, 843], [504, 604, 232, 604], [504, 604, 232, 843]]), [[232, 604, 232, 843], [504, 604, 232, 604]])

    def test_get_coordinates_on(self):
        self.assertEqual(get_coordinates_on([0, 0, 0, 3]), [[0, 0], [0, 1], [0, 2], [0, 3]])
        self.assertEqual(get_coordinates_on([0, 0, 3, 0]), [[0, 0], [1, 0], [2, 0], [3, 0]])
        self.assertEqual(get_coordinates_on([0, 3, 0, 0]), [[0, 0], [0, 1], [0, 2], [0, 3]])
        self.assertEqual(get_coordinates_on([3, 0, 0, 0]), [[0, 0], [1, 0], [2, 0], [3, 0]])

    def test_unpack_list(self):
        self.assertEqual(unpack_list([['a'], ['b', 'c'], [['d']], []]), ['a', 'b', 'c', ['d']])

    def test_add_to_dictionary(self):
      dictionary = {'[0, 0]': 1, '[0, 1]': 1}
      add_to_dictionary(dictionary, [0, 0])
      self.assertEqual(dictionary, {'[0, 0]': 2, '[0, 1]': 1})

    def test_filter_ones(self):
      dictionary = {'[0, 0]': 1,'[0, 1]': 2}
      dictionary = filter_ones(dictionary)
      self.assertEqual(dictionary, {'[0, 1]': 2})

    def test_get_coordinates_on_diagonal(self):
      self.assertEqual(get_coordinates_on_diagonal([0, 0, 2, 2]), [[0, 0], [1, 1], [2, 2]])
      self.assertEqual(get_coordinates_on_diagonal([2, 2, 0, 0]), [[0, 0], [1, 1], [2, 2]])
      self.assertEqual(get_coordinates_on_diagonal([6, 10, 9, 7]), [[6, 10], [7, 9], [8, 8], [9, 7]])
      self.assertEqual(get_coordinates_on_diagonal([9, 7, 6, 10]), [[6, 10], [7, 9], [8, 8], [9, 7]])




unittest.main()
