file = open("InputDay1.txt", "r")
contents = map(int, file.readlines())

prevHeight = contents[0]
nbIncreases = 0

for height in contents[1:]:
    if (height > prevHeight):
        nbIncreases+=1
    prevHeight = height

print("Number of increases: {}".format(nbIncreases))
