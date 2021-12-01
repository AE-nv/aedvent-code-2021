readings = [int(x) for x in open('./day 01/Xavier - python/input.txt').read().split('\n')]

increases = 0
for i in range(len(readings)-1):
    if readings[i] < readings[i+1]:
        increases += 1

print(increases)

increases = 0
for i in range(len(readings)-3):
    if sum(readings[i:i+3]) < sum(readings[i+1:i+4]):
        increases += 1

print(increases)
