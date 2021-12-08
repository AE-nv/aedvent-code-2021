def reverseMapping(mapping):
    result = {}
    for i, segmentString in enumerate(mapping):
        result["".join(sorted(segmentString))]=i
    #print(result)
    return result

def decode(p1):
    digitString = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    sixSegment = []
    fiveSegment = []
    for digit in p1:
        dg = len(digit)
        if dg == 2:
            digitString[1] = digit
        elif dg == 3:
            digitString[7] = digit
        elif dg == 4:
            digitString[4] = digit
        elif dg == 5:
            fiveSegment.append(digit)
        elif dg == 6:
            sixSegment.append(digit)
        elif dg == 7:
            digitString[8] = digit

    #find the two common segments in 7 and 1
    cs = ""
    for c in digitString[1]:
        if c in digitString[7]:
            cs+=c

    #use right vertical segments to find 3 in 5 segment digits
    remainingFiveSegment = []
    for option in fiveSegment:
        if cs[0] in option and cs[1] in option:
            digitString[3] = option
        else:
            remainingFiveSegment.append(option)

    #find middle segment by looking for common segment between 3 and 4
    middleSegment=""
    for c in digitString[3]:
        if c in digitString[4] and c not in cs:
            middleSegment=c

    #use middleSegment to extract the 0; then use the right vertical segments to distinguish 9 and 6
    for option in sixSegment:
        if middleSegment not in option:
            digitString[0]=option
        elif cs[0] in option and cs[1] in option:
            digitString[9]=option
        else:
            digitString[6]=option

    #find right lower segment
    lowerRight=""
    for c in digitString[6]:
        if c in cs:
            lowerRight=c

    #if lower right segment present in fiveSegment digit, it must be 5
    for option in remainingFiveSegment:
        if lowerRight in option:
            digitString[5]=option
        else:
            digitString[2]=option

    return reverseMapping(digitString)

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    p1Values=[]
    p2Values=[]
    for line in data:
        p1, p2 = line.rstrip().split('|')
        mapping = decode(p1.strip().split())
        digits = p2.strip().split()
        value=0
        for i,digit in enumerate(reversed(digits)):
            number = mapping["".join(sorted(digit))]
            value += pow(10,i)*number
            if len(digit) in [2,3,4,7]:
                p1Values.append(number)
        p2Values.append(value)

    print("p1:",len(p1Values))
    print("p2:",sum(p2Values))
