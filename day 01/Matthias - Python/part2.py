import sys

def day1(lines):
  sum_per_sliding_window = {}
  
  for (index, line) in enumerate(lines):
    number = int(line)
        
    for i in range(3):
        sliding_window_index = index + i
        
        if sliding_window_index not in sum_per_sliding_window:
            sum_per_sliding_window[sliding_window_index] = []
            
        sum_per_sliding_window[sliding_window_index].append(number)
    
  previous_sum = sys.maxsize
  amount_of_increases = 0
  
  for index in sum_per_sliding_window:
    sum_list = sum_per_sliding_window[index]
    if len(sum_list) < 3:
        continue
    
    current_sum = sum(sum_list)
    if current_sum > previous_sum:
      amount_of_increases += 1
    
    previous_sum = current_sum
  
  return amount_of_increases
    

with open('input.txt') as file:
  result = day1(file.readlines())
  print(result)
