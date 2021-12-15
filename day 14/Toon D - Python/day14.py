lines = open("day 14/Toon D - Python/input", "r").readlines()
template = lines[0].replace('\n','')

pairs = {elements[0]: elements[1] for elements in (line.replace('\n','').split(' -> ') for line in lines[2:])}

def step(pairs, template):
  return ''.join([template[i] + pairs[template[i:i+2]] for i in range(len(template)-1)] + [template[-1]])

def get_sorted_elements(template):
  return sorted([[element, template.count(element)] for element in set(template)],key=lambda x:x[1],reverse=True)

next_step = template
for i in range(10):
  next_step = step(pairs, next_step)

elements = get_sorted_elements(next_step)

print('part 1: %i' % (elements[0][1]-elements[-1][1]))

def merge_element_count(count_one, count_two):
  return {key: count_one[key] + count_two[key] for key in count_one.keys()}

cache = dict()

def depth_first_step(pairs, pair, depth):
  global cache
  key = str([pair, depth])
  if key in cache:
    return cache[key]
  if depth == 0:
    return {x:0 for x in set(pairs.values())}
  next_element = pairs[pair]
  left_element_count = depth_first_step(pairs, pair[0]+next_element, depth - 1)
  right_element_count = depth_first_step(pairs, next_element+pair[1], depth - 1)
  result = merge_element_count(left_element_count, right_element_count)
  result[next_element] += 1

  cache[key] = result
  return result

total =  {x:template.count(x) for x in set(pairs.values())}
for i in range(len(template)-1):
  element_count = depth_first_step(pairs, template[i:i+2], 40)
  total = merge_element_count(element_count, total)

elements = sorted(total.items(),key=lambda x:x[1],reverse=True)

print('part 2: %i' % (elements[0][1]-elements[-1][1]))
