file = open("day 06/Toon - Python/input", "r")

fishes = [int(x) for x in file.readline().split(',')]

def transform(fish_list):
  fish_days = [0 for i in range(0,9)]
  for fish in fish_list:
    fish_days[fish] += 1
  return fish_days

def better_day(fishes):
  pregnant = fishes[0]
  new_fishes = fishes[1:] + [pregnant]
  new_fishes[6] += pregnant
  return new_fishes

def day(fishes):
    return [
        f(fish) for fish in fishes
        for f in (
            (lambda x: 6, lambda x: 8) if fish == 0 else
            (lambda x: x-1,)
        )
    ]
day_fishes = transform(fishes)
print(day_fishes)
for i in range(0,80):
  print(day_fishes)
  day_fishes = better_day(day_fishes)
#  fishes = day(fishes)

print("part 1: %s" % sum(day_fishes))

for i in range(80,256):
  day_fishes = better_day(day_fishes)

print("part 2: %s" % sum(day_fishes))
