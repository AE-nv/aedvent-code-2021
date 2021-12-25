if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    easties={}
    downers={}
    for y, line in enumerate(data):
        for x, c in enumerate(line.strip()):
            if c==">":
                easties[(x,y)]=""
            elif c=="v":
                downers[(x,y)]=""

    W=len(data[0].strip())
    H=len(data)

    changing=True
    i=1
    while changing:
        print("step:",i)
        i+=1
        changing=False

        newEasties={}
        for x,y in easties:
            nextPosition=((x+1)%W,y)
            if nextPosition not in easties and nextPosition not in downers:
                newEasties[nextPosition]=""
                changing=True
            else:
                newEasties[(x,y)]=""
        easties=newEasties

        newDowners={}
        for x,y in downers:
            nextPosition=x,(y+1)%H
            if nextPosition not in easties and nextPosition not in downers:
                newDowners[nextPosition]=""
                changing=True
            else:
                newDowners[(x,y)]=""
        downers=newDowners


