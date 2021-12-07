positions = [int(pos) for pos in open('input.txt', 'r').read().split(',')]
print("Part 1:", min([sum([abs(fp - p) for fp in positions]) for p in range(max(positions) + 1)]))
print("Part 2:", min([sum([(abs(fp - p)/2*(1+abs(fp - p))) for fp in positions]) for p in range(max(positions) + 1)]))
