#include <iostream>
#include <fstream>
#include <vector>
#include <bitset>
#include <boost/dynamic_bitset.hpp>

std::vector<boost::dynamic_bitset<>> getBytesFromFile();
int calcDataRate(std::vector<boost::dynamic_bitset<>> codes, std::string dataType);
void removeElementWithBitAtPos(std::vector<boost::dynamic_bitset<>> *codes, std::size_t col, bool bit);

int main()
{
  //start somewhere, one must!
  std::cout << "Hello world" << std::endl;

  std::vector<boost::dynamic_bitset<>> codes = getBytesFromFile();

  int oxigenGenRate = calcDataRate(codes, "oxigen");
  std::cout << "oxigenGenRate: " << oxigenGenRate << std::endl;

  int co2scrubRate = calcDataRate(codes, "co2");
  std::cout << "co2scrubRate: " << co2scrubRate << std::endl;

  std::cout << "product: " << oxigenGenRate * co2scrubRate << std::endl;
  std::cout << "ow sheesh :s" << std::endl;
  return 0;
}

std::vector<boost::dynamic_bitset<>> transposeMatrix(std::vector<boost::dynamic_bitset<>> *codes) {
  std::size_t columns = codes->at(0).size();
  std::size_t rows = codes->size();

  std::vector<boost::dynamic_bitset<>> TCodes;
  for (int i = 0; i < columns; i++)
  {
    boost::dynamic_bitset code(rows);
    for (int j = 0; j < rows; j++)
    {
      code[(rows - 1) - j] = codes->at(j)[(columns-1)-i];
    }
    TCodes.push_back(code);
  }
  return TCodes;
}

int calcDataRate(std::vector<boost::dynamic_bitset<>> codes, std::string dataType)
{
  std::vector<boost::dynamic_bitset<>> TCodes = transposeMatrix(&codes);
  std::size_t rows = TCodes[0].size();
  std::size_t columns = TCodes.size();

  for (int i = 0; i < columns && codes.size() > 1; i++)
  {
    float amountOfBitsSet = TCodes[i].count();
    if (amountOfBitsSet >= (float)rows/(float)2)
    {
      if (dataType == "oxigen")
      {
        removeElementWithBitAtPos(&codes, i, 0);
      }
      else
      {
        removeElementWithBitAtPos(&codes, i, 1);
      }
    }
    else
    {
      if (dataType == "oxigen")
      {
        removeElementWithBitAtPos(&codes, i, 1);
      }
      else
      {
        removeElementWithBitAtPos(&codes, i, 0);
      }
    }
    TCodes = transposeMatrix(&codes);
    columns = TCodes.size();
    rows = TCodes[0].size();
  }
  return codes.at(0).to_ulong();
}

void removeElementWithBitAtPos(std::vector<boost::dynamic_bitset<>> *codes, std::size_t col, bool bit)
{
  std::size_t rows = codes->size();
  std::size_t columns = codes->at(0).size();
  for (size_t i = 0; i < rows; i++)
  {
    if (codes->at(i)[(columns -1)-col] == bit)
    {
      codes->erase(codes->begin() + i);
      i--;
      rows --;
    }
  }
}

std::vector<boost::dynamic_bitset<>> getBytesFromFile()
{
  std::vector<boost::dynamic_bitset<>> temp;
  std::ifstream bytesFromFile;
  bytesFromFile.open("hello-world.txt");

  if (!bytesFromFile)
  {
    std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
    exit(1);
  }

  boost::dynamic_bitset<> x;
  while (bytesFromFile >> x)
  {
    temp.push_back(x);
  }
  bytesFromFile.close();
  return temp;
}