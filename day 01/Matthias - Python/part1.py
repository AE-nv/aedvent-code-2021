import sys

def day1(lines):
  previous_number = sys.maxsize
  amount_depth_measurement_increases = 0

  for line in lines:
    current_number = int(line)
       
    if current_number > previous_number:
        amount_depth_measurement_increases += 1
    
    previous_number = current_number
    
  return amount_depth_measurement_increases

with open('input.txt') as file:
  result = day1(file.readlines())
  print(result)
