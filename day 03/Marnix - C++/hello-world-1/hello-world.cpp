#include <iostream>
#include <fstream>
#include <vector>
#include <bitset>
#include <boost/dynamic_bitset.hpp>

std::vector<boost::dynamic_bitset<>> getBytesFromFile();

int main()
{
  //start somewhere, one must!
  std::cout << "Hello world" << std::endl;

  std::vector<boost::dynamic_bitset<>> codes = getBytesFromFile();

  std::size_t columns = codes[0].size();
  std::size_t rows = codes.size();
  for (size_t i = 0; i < rows; i++)
  {
    std::cout << codes[i] << std::endl;
  }
  

  std::vector<boost::dynamic_bitset<>> TCodes;
  for (int i = 0; i < columns; i++)
  {
    boost::dynamic_bitset code(rows);
    for (int j = 0; j < rows; j++)
    {
      code[(rows - 1) - j] = codes[j][(columns-1)-i];
    }
    TCodes.push_back(code);
  }

  boost::dynamic_bitset<> gammaBits(columns);
  for (size_t i = 0; i < columns; i++)
  {
    std::cout << TCodes[i] << std::endl;
    int amountOfBitsSet = TCodes[i].count();
    std::cout << "amountOfBits: " << amountOfBitsSet << " rows: " << rows << std::endl;
    if (amountOfBitsSet > rows / 2)
    {
      gammaBits[(columns-1)-i] = 1;
    }
    else
    {
      gammaBits[(columns-1)-i] = 0;
    }
  }
  int gamma = gammaBits.to_ulong();
  std::cout << gamma << std::endl;

  int epsilon = gammaBits.flip().to_ulong();
  std::cout << epsilon << std::endl;

  std::cout << "product: " << gamma * epsilon << std::endl;
  std::cout << "ow sheesh :s" << std::endl;
  return 0;
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