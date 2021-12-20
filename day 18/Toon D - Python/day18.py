
class Pair:
    left = None
    right = None

    def __init__(self, left, right):
        self.left = left
        self.right = right

    def check_split(self):  # -> (Tree, bool)
        (self.left, left_changed) = self.left.check_split()
        if left_changed:
            return (self, True)
        (self.right, right_changed) = self.right.check_split()
        return (self, right_changed)

    def check_explode(self, depth):
        if depth == 4:
            return (True, self.left.value, self.right.value)
        else:
            (changed, left_value, right_value) = self.left.check_explode(depth + 1)
            if changed:
                if depth == 3:
                    self.left = Literal(0)
                self.right.add_left(right_value)
                return (True, left_value, 0)
            (changed, left_value, right_value) = self.right.check_explode(depth + 1)
            if changed:
                self.left.add_right(left_value)
                if depth == 3:
                    self.right = Literal(0)
                return (True, 0, right_value)
            return (False, None, None)

    def add_left(self, value):
        self.left.add_left(value)

    def add_right(self, value):
        self.right.add_right(value)

    def magnitude(self):
        return 3 * self.left.magnitude() + 2 * self.right.magnitude()

    def __repr__(self) -> str:
        return self.__str__()

    def __str__(self) -> str:
        return '[' + self.left.__str__() + ',' + self.right.__str__() + ']'


class Literal:
    value = None

    def __init__(self, value):
        self.value = value

    def check_split(self):  # -> (Tree, bool)
        if self.value >= 10:
            half = self.value // 2
            return (Pair(Literal(half), Literal(self.value - half)), True)
        else:
            return (self, False)

    def check_explode(self, depth):
        return (False, None, None)

    def add_left(self, value):
        self.value += value

    def add_right(self, value):
        self.value += value

    def magnitude(self):
        return self.value

    def __str__(self) -> str:
        return str(self.value)

    def __repr__(self) -> str:
        return self.__str__()


def parse(string):
    if string[0] == '[':
        # +1 corrects string starting at 1
        index = find_middle_index(string[1:-1]) + 1
        return Pair(parse(string[1:index]), parse(string[index + 1: -1]))
    else:
        return Literal(int(string))


def find_middle_index(string):
    open_count = 0
    i = 0
    while True:
        if string[i] == '[':
            open_count += 1
        elif string[i] == ']':
            open_count -= 1
        elif string[i] == ',':
            if open_count == 0:
                return i
        i += 1


def addition(left, right):
    base = Pair(left, right)
    change = True
    while change:
        (change_explosion, _, _) = base.check_explode(0)
        if not change_explosion:
            (_, change) = base.check_split()
        else:
            change = True
    return base


input = open("day 18/Toon D - Python/input", "r").readlines()
numbers = [line.replace('\n', '') for line in input]

result = parse(numbers[0])
for number in numbers[1:]:
    result = addition(result, parse(number))

print('part 1: %i' % result.magnitude())

all_results = [addition(parse(left), parse(right)).magnitude()
               for left in numbers for right in numbers if left is not right]
print('part 2: %i' % sorted(all_results)[-1])
