from functools import reduce
example = open('./day 10/Xavier - Python/example.txt').read().splitlines()
input = open('./day 10/Xavier - Python/input.txt').read().splitlines()

def score(system):
    p1, p2 = 0, []
    for l in system:
        q, corr = [], False
        for c in l:
            if c in ['(','[','<','{']:
                q.append(c)
            elif abs(ord(c)-ord(q[-1]))<=2:
                q.pop()
            else:
                p1 += {')':3,']': 57,'}':1197,'>':25137}[c]
                corr = True
                break
        if not corr:
            q_score = reduce(lambda a,b: a*5 + {'(':1,'[':2,'{':3,'<':4}[b], [0]+list(reversed(q)))
            p2.append(q_score)
    return p1, sorted(p2)[len(p2)//2]

assert score(example) == (26397, 288957)
print(score(input))