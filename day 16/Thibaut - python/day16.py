def getVersionAndType(binary):
    result = (int(binary[0:3],2), int(binary[3:6],2))
    return result

def popAndParseLiteralPacket(binary):
    #print("--LITERAL--")
    binary = binary[6:]
    done=False
    number=""
    length=6
    while not done:
        if binary[0]=="0":
            done=True
        number+=binary[1:5]
        binary=binary[5:]
        length+=5
    #print("value:",int(number, 2))
    return (length, int(number, 2), binary)


def popAndParseOperatorPacket(binary):
    #print("--OPERATOR--")
    binary=binary[6:]
    locationId=binary[0]
    #print("lengthId:",locationId)
    binary=binary[1:]
    length=7
    values=[]
    if locationId=="0":
        #next 15 bits are a number that represents the total length in bits of the contained sub-packets
        totalSubPacketLength = int(binary[0:15],2)
        binary = binary[15:]
        length+=15
        while totalSubPacketLength>0:
            #print("remainingBits",totalSubPacketLength)
            version, type, subPacketLength, value, binary = parsePacket(binary)
            values.append(value)
            totalSubPacketLength-=subPacketLength
            length+=subPacketLength
    else:
        #next 11 bits are a number that represents the number sub-packet that this operator contains
        packetCount = int(binary[0:11], 2)
        binary=binary[11:]
        length+=11
        while packetCount>0:
            #print("remainingPackets:",packetCount)
            packetCount-=1
            version, type, subPacketLength, value, binary = parsePacket(binary)
            values.append(value)
            length+=subPacketLength
    return length, values, binary

def evaluateOperator(values, type):
    if type==0:
        return sum(values)
    elif type==1:
        result=1
        for v in values:
            result=result*v
        return result
    elif type==2:
        return min(values)
    elif type==3:
        return max(values)
    elif type==5:
        return int(values[0] > values[1])
    elif type==6:
        return int(values[0] < values[1])
    elif type==7:
        return int(values[0] == values[1])
    else:
        print("shit")

versions=[]
def parsePacket(binary):
    version, type = getVersionAndType(binary)
    #print("parse:",version, type)
    global versions
    versions.append(version)
    if type != 4:
        length, values, binary = popAndParseOperatorPacket(binary)
        value=evaluateOperator(values,type)
        return (version, type, length, value, binary)
    else:
        length, value, binary = popAndParseLiteralPacket(binary)
        return (version, type, length, value, binary)

if __name__ == '__main__':
    with open("input.txt", 'r') as f:
        data = f.readlines()

    hex = {"0" : "0000", "1" : "0001", "2" : "0010", "3" : "0011", "4" : "0100", "5" : "0101", "6" : "0110", "7" : "0111", "8" : "1000", "9" : "1001", "A" : "1010", "B" : "1011", "C" : "1100", "D" : "1101", "E" : "1110", "F" : "1111"}
    binary = "".join([hex[c] for c in data[0].strip()])

    print("p1:",sum(versions))
    print("p2:",parsePacket(binary)[3])

