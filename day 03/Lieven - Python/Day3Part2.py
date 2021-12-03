import numpy as np

def getIdxsWithOneAndZero(col, idxsToConsider):
    idxsWithOne = []
    idxsWithZero = []
    for i in idxsToConsider:
        if col[i] == 1:
            idxsWithOne.append(i)
        else:
            idxsWithZero.append(i)
    return (idxsWithOne,idxsWithZero)

def bitArrToDecimal(bitArray):
    decimalNum = 0
    arrayLen = len(bitArray)

    for i in range(0,arrayLen):
        decimalNum += bitArray[i]*(2**(arrayLen-1-i))
    return decimalNum

# ===========================================================================

file = open("Input.txt", "r")
splitRows = map(lambda line: filter(lambda char: char != "\n", list(line)), file.readlines())
matrix = np.array(map(lambda splitRow: map(int,splitRow),splitRows))
width = len(matrix[0])
height = len(matrix)

oxygenGeneratorRating = []
co2ScrubberRating = []
currOxygenCandidateRowIdxs = range(0,height)
currCo2CandidateRowIdxs = range(0,height)

for i in range(0,width):
    currCol = matrix[:,i]
    if(len(currOxygenCandidateRowIdxs) > 1):
        (oxygenRowsWithOneInCurrPos,oxygenRowsWithZeroInCurrPos) = getIdxsWithOneAndZero(currCol,currOxygenCandidateRowIdxs)

        if(len(oxygenRowsWithOneInCurrPos) >= len(oxygenRowsWithZeroInCurrPos)):
            currOxygenCandidateRowIdxs = oxygenRowsWithOneInCurrPos
        else:
            currOxygenCandidateRowIdxs = oxygenRowsWithZeroInCurrPos
        
        if(len(currOxygenCandidateRowIdxs) == 1):
            oxygenGeneratorRating = matrix[currOxygenCandidateRowIdxs[0]]

    if(len(currCo2CandidateRowIdxs) > 1):
        (co2RowsWithOneInCurrPos,co2RowsWithZeroInCurrPos) = getIdxsWithOneAndZero(currCol,currCo2CandidateRowIdxs)
        if(len(co2RowsWithZeroInCurrPos) <= len(co2RowsWithOneInCurrPos)):
            currCo2CandidateRowIdxs = co2RowsWithZeroInCurrPos
        else:
            currCo2CandidateRowIdxs = co2RowsWithOneInCurrPos

        if(len(currCo2CandidateRowIdxs) == 1):
            co2ScrubberRating = matrix[currCo2CandidateRowIdxs[0]]
    
    print("Idx: {}, oxygen candidates: {}, CO2 candidates: {}".format(i,currOxygenCandidateRowIdxs,currCo2CandidateRowIdxs))

    if(len(currOxygenCandidateRowIdxs) == 1 and len(currCo2CandidateRowIdxs) == 1):
        break

oxygenVal = bitArrToDecimal(oxygenGeneratorRating)
co2Val = bitArrToDecimal(co2ScrubberRating)
print("Oxygen generator rating: {}, C02 scrubber rating: {}, Result: {}".format(oxygenVal, co2Val, oxygenVal*co2Val))
