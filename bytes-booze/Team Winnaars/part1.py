input = open("bytes-booze/Team Winnaars/input", "r").readline()
input = input.split(',')

programs = list('abcdefghijklmnop')

def spin(programs, x):
  return programs[len(programs) - x:] + programs[0:len(programs) - x]

def exchange(programs, x, y):
  temp = programs[x]
  programs[x] = programs[y]
  programs[y] = temp
  return programs

def partner(programs: list, x, y):
  return exchange(programs, programs.index(x), programs.index(y))
  
for command in input:
  if command[0] == 's':
    amount = int(command[1:])
    programs = spin(programs, amount)
  elif command[0] == 'x':
    (left, right) = command[1:].split('/')
    programs = exchange(programs, int(left), int(right))
  elif command[0] == 'p':
    (left, right) = command[1:].split('/')
    programs = partner(programs, left, right)

print(''.join(programs))
    
