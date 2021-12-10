def get_scores(line):
    to_check, syntax_score, completion_score = [], 0, 0
    for c in line:
        if c in '<{[(':
            to_check.append(c)
        else:
            a = to_check.pop()
            if a + c not in ['{}', '[]', '<>', '()']:
                syntax_score += {')': 3, ']': 57, '}': 1197, '>': 25137}[c]
    if syntax_score == 0 and to_check:
        to_check.reverse()
        for c in to_check:
            completion_score = (completion_score * 5) + {'(': 1, '[': 2, '{': 3, '<': 4}[c]
    return syntax_score, completion_score


all_scores = [get_scores(line) for line in open('input.txt', 'r').read().splitlines()]
print("Part 1:", sum([score[0] for score in all_scores]))

completion_scores = sorted(list(filter(lambda s: s > 0, [score[1] for score in all_scores])))
print("Part 2:", completion_scores[int(len(completion_scores) / 2)])
