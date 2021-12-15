if __name__ == '__main__':
    with open("example.txt", 'r') as f:
        data = f.readlines()

    matrix = "[|"+"|".join([",".join([c for c in l.strip()]) for l in data])+"|]"
    print(matrix)

