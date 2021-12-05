# Python 3.8
# O(n1*n2) where n1 = number of vents, n2 = board width = board height = max number of points in vent 

# Class representing a vent
class Vent:
    def __init__(self, point1, point2):
        self.point1 = point1
        self.point2 = point2

    def __str__(self) -> str:
        return f"[{self.point1},{self.point2}]"
        
    def getCoordinatesOfLine(self):
        coordinatesOfLine = []
        if (self.point1.x == self.point2.x):
            listOfYValues = []
            if (self.point1.y < self.point2.y):
                listOfYValues = range(self.point1.y,self.point2.y+1)
            else:
                listOfYValues = range(self.point2.y,self.point1.y+1)
            coordinatesOfLine = list(zip([self.point1.x for _ in listOfYValues],listOfYValues))
        elif (self.point1.y == self.point2.y):
            listOfXValues = []
            if (self.point1.x < self.point2.x):
                listOfXValues = range(self.point1.x,self.point2.x+1)
            else:
                listOfXValues = range(self.point2.x,self.point1.x+1)
            coordinatesOfLine = list(zip(listOfXValues,[self.point1.y for _ in listOfXValues]))
        else:
            orderX = -1 if self.point1.x > self.point2.x else 1
            orderY = -1 if self.point1.y > self.point2.y else 1
            xValues = range(self.point1.x,self.point2.x + orderX, orderX)
            yValues = range(self.point1.y,self.point2.y + orderY, orderY)
            coordinatesOfLine = list(zip(xValues,yValues))
        
        return [Point(coordinate[0],coordinate[1]) for coordinate in coordinatesOfLine]

class Point:
    def __init__(self, x, y):
        self.x = int(x)
        self.y = int(y)

    def __eq__(self, other: object):
        if not isinstance(other, Point):
            return False
        
        return self.x == other.x and self.y == other.y

    def __hash__(self) -> int:
        return self.x*self.y+self.y

    def __str__(self) -> str:
        return f"({self.x},{self.y})"

def filterNewlines(str):
    return str.replace("\n","")

def createPointFromStr(coordStr):
    xAndY = coordStr.split(",")
    return Point(int(xAndY[0]), int(xAndY[1]))

def createVents(lines):
    vents = []
    for line in lines:
        ventPoints = filterNewlines(line).split(" -> ")
        vents.append(Vent(createPointFromStr(ventPoints[0]),createPointFromStr(ventPoints[1])))
    return vents

# ==========================================

file = open("Input.txt", "r")
lines = file.readlines()
vents = createVents(lines)
# HashMap<Point,int>
board = dict()
numberOfDangerPoints = 0

for vent in vents:
    for point in vent.getCoordinatesOfLine():
        if(point in board):
            board[point] += 1
            if (board[point] == 2):
                numberOfDangerPoints += 1
        else:
            board[point] = 1

print("Number of points where at least two lines overlap: {}".format(numberOfDangerPoints))






