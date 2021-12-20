
input = open("bytes-booze/Team Winnaars/input", "r").readline()
input = input.split(',')

def spin(programs, x)-> list:
  return programs[len(programs) - x:] + programs[0:len(programs) - x]

def exchange(programs:list, x, y)-> list:
  temp = programs[x]
  programs[x] = programs[y]
  programs[y] = temp
  return programs

def partner(programs: list, x, y)-> list:
  return exchange(programs, programs.index(x), programs.index(y))

def dance(programs)-> list:
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
  return programs

program = list('abcdefghijklmnop')
loops = 1000000000
for i in range(loops):
  program = dance(program)
  print(program)
  if program == list('abcdefghijklmnop'):
    rest = loops % (i+1)
    break

print('cycle found: %i' % (i+1))
program = list('abcdefghijklmnop')
for i in range(rest):
  program = dance(program)

print(''.join(program))