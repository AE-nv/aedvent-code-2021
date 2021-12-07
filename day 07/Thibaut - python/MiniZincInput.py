if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()
    positions = list(map(lambda x: int(x), data[0].rstrip().split(",")))
    nPositions = len(positions)
    ran = max(positions)
    print("positions=",positions)
    print("nPositions=",nPositions)
    print("range=",ran)

    #Python code to help debug P2
    consumption = [0]+list(map(lambda x: sum(range(1,x+1)),range(1,ran+2)))
    print(consumption)
    bestFuel = 1000000000000000
    for target in range(0,ran+1):
        fuelUsed = sum(map(lambda x: consumption[abs(x-target)],positions))
        if fuelUsed<bestFuel:
            bestFuel=fuelUsed
        print(target)
    print(bestFuel)


