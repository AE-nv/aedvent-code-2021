import numpy as np

def visualize(paper):
    mx, my = paper.shape
    for x in range(0,mx):
        s=""
        for y in range(0,my):
            if paper[x,y]>0:
                s+="#"
            else:
                s+="."
        print(s)

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    folding=False
    instructions=[]
    paper = np.zeros((1500,1500))
    for l in data:
        if len(l.strip())==0:
            folding=True
            continue
        if folding:
            axis,number=l.split()[2].split('=')

            instructions.append((axis, int(number)))
        else:
            x,y=l.strip().split(',')
            paper[int(y),int(x)]+=1

    for a,n in instructions:
        if a=="x":
            tmp = paper[:,n+1:n*2+1]
            print("temp:",tmp)
            tmp = np.flip(tmp,1)
            paper=paper[:,:n]
            print("paper:",paper)
        else:
            tmp = paper[n+1:n*2+1,:]
            print("temp:", tmp)
            tmp = np.flip(tmp,0)
            paper = paper[:n, :]
            print("paper:",paper)
        paper+=tmp
    np.set_printoptions(linewidth=np.inf)
    print(paper)
    visualize(paper)
    print("number of visible dots",np.count_nonzero(paper))




