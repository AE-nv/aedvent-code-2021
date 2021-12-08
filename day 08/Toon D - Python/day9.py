from collections import Counter
file = open("day 08/Toon D - Python/input", "r")
lines = file.readlines()

lines = [line.replace('\n','').split('|') for line in lines]
lines = [[element.split(' ') for element in line] for line in lines]
lines = [[["".join(sorted(x)) for x in element if x] for element in line] for line in lines] # filter empty strings

outputs = [element for line in lines for element in line[1]]
onefourseveneight = [element for element in outputs if len(element) == 2 or len(element) == 4 or len(element) == 3 or len(element) == 7]

print("part 1: %i" % len(onefourseveneight))

def contains_all(string, set):
  return False not in [char in string for char in set]

def find_output(uniques, output):
  results = {x:None for x in range(0,10)}
  uniques = sorted(uniques, key=len)
  results[1] = uniques.pop(0)
  results[7] = uniques.pop(0)
  results[4] = uniques.pop(0)
  results[8] = uniques.pop(-1)
  
  results[9] = [x for x in uniques if contains_all(x, results[4])][0] # if all chars in 4 are in the string, that one is 9
  uniques.remove(results[9])
  results[0] = [x for x in uniques if contains_all(x, results[7])][-1] # 0 and 3 contain all chars of 7, and 0 has 6 chars, 3 has only 5
  uniques.remove(results[0])
  results[3] = [x for x in uniques if contains_all(x, results[1])][0]
  uniques.remove(results[3])
  results[6] = uniques.pop(-1) # only 6 element character remaining
  results[5] = [x for x in uniques if contains_all(results[9], x)][0]
  uniques.remove(results[5])
  results[2] = uniques[0]

  rev_results = {v:k for k,v in results.items()}
  return int(''.join([str(rev_results[x]) for x in output]))

result = [find_output(line[0], line[1]) for line in lines]
print("part 2: %i" % sum(result))

