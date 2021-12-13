from functools import reduce
from itertools import takewhile, dropwhile
from PIL import Image
import numpy as np
import pytesseract


class Paper:

    def __init__(self, values):
        self.data = values
        self.cols, self.rows = values.shape

    def count_dots(self):
        return len(self.data.nonzero()[0])

    def fold(self, axis, pos):
        if axis == 'x':
            side_one = self.data[0:pos, 0:self.rows]
            side_two = np.flipud(self.data[pos + 1:self.cols, 0:self.rows])
        else:
            side_one = self.data[0:self.cols, 0:pos]
            side_two = np.fliplr(self.data[0:self.cols, pos + 1:self.rows])
        return Paper(side_one + side_two)

    def render_img(self):
        bitmap = np.full((self.rows + 20, self.cols + 100, 3), (255, 255, 255), np.uint8)
        for x in range(self.cols):
            for y in range(self.rows):
                bitmap[y + 10, x + 10] = (255, 255, 255) if self.data[x, y] == 0 else (0, 0, 0)
        image = Image.fromarray(bitmap, 'RGB').resize((self.cols * 15, self.rows * 20))
        image.save('folded_paper.png')
        return image

    def get_letters(self):
        return pytesseract.image_to_string(self.render_img(),
                                           config="-c tessedit_char_whitelist=ABCDEFGHJKLMNOPQRSTUVWXYZ --psm 11")

    @classmethod
    def __from_dots__(cls, dots):
        max_x, max_y = max([dot[0] for dot in dots]), max([dot[1] for dot in dots])
        values = np.zeros((max_x + 1, max_y + 1))
        for dot in dots:
            values[dot] = 1
        return Paper(values)


lines = open('input.txt').read().splitlines()
paper = Paper.__from_dots__([(int(line.split(',')[0]), int(line.split(',')[1])) for line in list(takewhile(lambda l: l != '', lines))])
folds = [(line.split('=')[0][-1], int(line.split('=')[1])) for line in list(dropwhile(lambda l: l != '', lines))[1:]]

print("Part 1:", paper.fold(*folds[0]).count_dots())
print("Part 2:", reduce(lambda p, f: p.fold(*f), folds, paper).get_letters())
