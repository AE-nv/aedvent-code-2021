depths = [int(depth) for depth in open(r'./input.txt').read().split('\n') if depth]

nb_increases = 0
for i in range(1, len(depths)):
    if depths[i] > depths[i-1]:
        nb_increases += 1
print(nb_increases)

window_size = 3
nb_increases = 0
for i in range(0, len(depths)-window_size):
    if sum(depths[i:i+window_size]) < sum(depths[i+1:i+1+window_size]):
        nb_increases += 1
print(nb_increases)

