def find_syntax_error(line):
    correct = True
    incorrect_char = None
    complete = True
    closing_chars = []
    to_close = []
    chunk_characters = {
        '(': ')',
        '{': '}', 
        '[': ']', 
        '<': '>'
        }

    for char in line:
        if char in chunk_characters.keys():
            to_close.append(char)
        else:
            if chunk_characters[to_close[-1]] == char:
                to_close.pop()
            else:
                incorrect_char = char
                correct = False
                break
    if correct and len(to_close) > 0:
        complete = False
        to_close = reversed(to_close)
        for char in to_close:
            closing_chars.append(chunk_characters[char])

    return correct, incorrect_char, complete, closing_chars

def calculate_value_incorrect(incorrect_chars):
    scores_dict = {
        ')': 3,
        ']': 57,
        '}': 1197,
        '>': 25137
    }
    score = 0
    for char in incorrect_chars:
        score += scores_dict[char]
    return score

def calculate_value_incomplete(closing_chars):
    scores_dict = {
        ')': 1,
        ']': 2,
        '}': 3,
        '>': 4,
    }
    total_score = 0
    for char in closing_chars:
        total_score = 5 * total_score
        total_score += scores_dict[char]
    print(closing_chars, total_score)
    return total_score



if __name__ == '__main__':
    input = open('./day 10/Martijn - Python/input.txt').readlines()
    illegal_chars = []
    incomplete_scores = []
    for line in input:
        line = line.strip()
        correct, incorrect_char, complete, closing_chars = find_syntax_error(line)
        if not correct:
            illegal_chars.append(incorrect_char)
        if not complete:
            score_closing_chars = calculate_value_incomplete(closing_chars)
            incomplete_scores.append(score_closing_chars)
    score_incorrect = calculate_value_incorrect(illegal_chars)
    print(score_incorrect)
    incomplete_scores.sort()
    middle_index = len(incomplete_scores) // 2
    incomplete_score = incomplete_scores[middle_index]
    print(incomplete_score)


    