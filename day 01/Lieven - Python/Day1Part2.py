file = open("InputDay1.txt", "r")
contents = map(int, file.readlines())
length = len(contents)

prevHeight = sum(contents[0:3])
nbIncreases = 0

for i in range(1,length-2):
    windowSum = sum(contents[i:i+3])
    if (windowSum > prevHeight):
        nbIncreases+=1
    prevHeight = windowSum

print("Number of increases: {}".format(nbIncreases))
