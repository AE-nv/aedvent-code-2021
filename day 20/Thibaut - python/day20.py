import numpy as np

def expandImage(input, restoration, paddingValue):
    enlargedInput = np.pad(input, (2,2), constant_values=paddingValue)
    if paddingValue==0:
        resultPadding=restoration[0]
    else:
        resultPadding=restoration[-1]
    output = np.pad(np.zeros_like(input), (2, 2), constant_values=resultPadding)
    for x in range(0,input.shape[0]+2):
        for y in range(0,input.shape[1]+2):
            localArea = enlargedInput[x:x+3,y:y+3]
            index = int("".join([str(int(x)) for x in localArea.flatten().tolist()]),2)
            newPixel=restoration[index]
            output[x+1,y+1]=newPixel
    return output


if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    restoration = [{"#":1,".":0}[c] for c in data[0].strip()]

    input=[]
    for l in data[2:]:
        input.append([{"#":1,".":0}[c] for c in l.strip()])
    input=np.array(input)
    paddingValue=0
    for i in range(0,50):
        input = expandImage(input, restoration, paddingValue)
        if paddingValue==0:
            paddingValue=restoration[0]
        else:
            paddingValue=restoration[-1]
        if i==1:
            print("p1:",sum(sum(input)))
    print("p2:",sum(sum(input)))

