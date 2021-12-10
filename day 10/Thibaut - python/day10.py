import statistics

def getLostCharacters(line):
    print("Line:", line)
    opening=[]
    for i, bracket in enumerate(line.rstrip()):
        if bracket in ['(', '[', '{', '<']:
            opening.append(bracket)
        else:
            print(opening)
            opening.pop()

    missing=[]
    closers={'(':')', '[':']', '{':'}', '<':'>'}
    for bracket in reversed(opening):
        missing.append(closers[bracket])

    print("missing:",missing)
    return missing

def getScoreForMissing(missing):
    score=0
    scores = {')':1, ']':2, '}':3, '>':4}
    for b in missing:
        score=score*5
        score+=scores[b]
    return score

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    scores=[]
    missingScores=[]
    for line in data:
        opening=[]
        corrupted = False
        for i,bracket in enumerate(line):
            if bracket in ['(','[','{','<']:
                opening.append(bracket)
            else:
                prev=opening.pop()
                if bracket==')' and prev!='(':
                    scores.append(3)
                    corrupted=True
                    break
                elif bracket==']' and prev!='[':
                    scores.append(57)
                    corrupted=True
                    break
                elif bracket=='}' and prev!='{':
                    scores.append(1197)
                    corrupted = True
                    break
                elif bracket=='>' and prev!='<':
                    scores.append(25137)
                    corrupted = True
                    break
        if not corrupted:
            missing=getLostCharacters(line)
            missingScores.append(getScoreForMissing(missing))

    print("p1:",sum(scores))
    print("p2:",statistics.median(missingScores))
