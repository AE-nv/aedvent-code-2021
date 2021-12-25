# done manually after printing the instructions (and getting a big hint)

ops = [op.split() for op in open('input.txt').read().splitlines()]

def perform_operation(vars, op, input):
    if op[0] == 'inp':
        vars[op[1]] = int(input.pop(0))
    else:
        a = op[1]
        b = op[2]
        if not b in vars.keys():
            b = int(b)
        else:
            b = vars[b]
        if op[0] == 'add':
            vars[a] += b
        elif op[0] == 'mul':
            vars[a] *= b
        elif op[0] == 'div':
            vars[a] = vars[a]//b
        elif op[0] == 'mod':
            vars[a] %= b
        else:
            vars[a] = 1 if vars[a] == b else 0
    return vars, input

def monad(input):
    vars = {'w': 0, 'z': 0, 'x': 0, 'y': 0}
    for op in ops:
        vars, input = perform_operation(vars, op, input)
    return vars

def print_instructions():
    '''Print instructions per input next to one another to check similarity'''
    lines = open('input.txt').read().splitlines()
    lines = ['']*18
    for i, op in enumerate(ops):
        lines[i%18] += ' \t' + op
    
    for line in lines:
        print(line)

# Verification
print(monad(list('51939397989999')))
print(monad(list('11717131211195')))