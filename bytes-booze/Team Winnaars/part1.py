i = open("input", "r").readline().split(',')
p = list('abcdefghijklmnop')
def e(x, y):
  p[x], p[y] = p[y], p[x]
for c in i:
  if c[0] == 's':
    x = int(c[1:])
    p = p[len(p) - x:] + p[0:len(p) - x]
  elif c[0] == 'x':
    (l, r) = c[1:].split('/')
    e(int(l), int(r))
  else:
    (l, r) = c[1:].split('/')
    e(p.index(l), p.index(r))
print(''.join(p))