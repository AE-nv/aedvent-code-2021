import math

def parseLine(l):
    command=l[0:3]
    args = l.strip().split(' ')[1:]
    return (command, args)

def performOperation(variables, command, args):
    if any(char.isdigit() for char in [args[1]]):
        b=int(args[1])
        if command=="mul":
            variables[args[0]]*=b
        elif command=="div":
            variables[args[0]]=math.floor(variables[args[0]]/b)
        elif command=="add":
            variables[args[0]] += b
        elif command=="mod":
            variables[args[0]] = variables[args[0]] % b
        elif command=="eql":
            variables[args[0]] = int(variables[args[0]] == b)
    else:
        if command=="mul":
            variables[args[0]]*=variables[args[1]]
        elif command=="div":
            variables[args[0]]=math.floor(variables[args[0]]/variables[args[1]])
        elif command=="add":
            variables[args[0]] += variables[args[1]]
        elif command=="mod":
            variables[args[0]] = variables[args[0]] % variables[args[1]]
        elif command=="eql":
            variables[args[0]] = int(variables[args[0]] == variables[args[1]])


if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    instructions=[parseLine(l) for l in data]
    subset=[[] for i in range(0,18)]
    c=0
    for command, args in instructions:
        if command!="inp":
            subset[c].append((command, args))
            c=(c+1)%17

    As=[int(x[1][1]) for x in subset[3]]
    Bs=[int(x[1][1]) for x in subset[4]]
    Cs=[int(x[1][1]) for x in subset[14]]

    stack=[]
    matches=[]
    for i,abc in enumerate(zip(As,Bs,Cs)):
        if abc[0]==1:
            stack.append((i,abc))
        else:
            matches.append((i,stack.pop(),abc))

    model="array[1..14] of var 1..9: W;\n"+ \
    "var int: Wvalue=W[1]+10*(W[2]+10*(W[3]+10*(W[4]+10*(W[5]+10*(W[6]+10*(W[7]+10*(W[8]+10*(W[9]+10*(W[10]+10*(W[11]+10*(W[12]+10*(W[13]+10*W[14]))))))))))));\n\n"

    constraints=[]
    for i, left, right in matches:
        c = "constraint W["+str(i+1)+"]-("+str(right[1])+")==W["+str(left[0]+1)+"]+"+str(left[1][2])+";"
        model+=c+"\n"
        constraints.append(c)

    model+="\nsolve maximize Wvalue;\n"

    print("------------------")
    print("| MINIZINC MODEL |")
    print("------------------")
    print(model)
    print("the model won't run because of the ints being to large for minizinc to handle :(")

    print("------------------")

    values=[list(range(1,10)) for i in range(0,14)]
    for i1, (i2, (a2,b2,c2)), (a1,b1,c1) in matches:
        newValues=[]
        for x in values[i1]:
            if x+c2+b1 in values[i2]:
                newValues.append(x)
        values[i1]=newValues
        newValues=[]
        for x in values[i2]:
            if x-b1-c2 in values[i1]:
                newValues.append(x)
        values[i2] = newValues

    values=[[10-i for i in v] for v in values]

    print("p1:", "".join([str(max(v)) for v in values]))
    print("p2:", "".join([str(min(v)) for v in values]))

    # inputs=[list(range(1,10)) for i in range(0,14)]
    # inputSelector=[9 for i in range(0,14)]
    # variables={"x":0, "y":0, "z":0, "w":0}
    # for i,selector in inputSelector:
    #     for command, args in instructions:
