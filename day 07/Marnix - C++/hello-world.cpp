#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <math.h>
#include <algorithm>

std::vector<int> getCrabSubmarines();


int main() {
  //start somewhere, one must!
  std::cout << "Hello world" << std::endl;

  std::vector<int> crabSubmarines = getCrabSubmarines();
  std::sort(crabSubmarines.begin(), crabSubmarines.end());
  
  size_t crabSubmarinesSize = crabSubmarines.size();
  int median;
  if (crabSubmarinesSize % 2 == 0)
  {
    int num1 = crabSubmarines[std::floor((crabSubmarinesSize) / 2)];
    int num2 = crabSubmarines[std::ceil((crabSubmarinesSize) / 2)];
    median = std::round((num1+num2)/2);
  } else {
    median = crabSubmarines[std::round(crabSubmarinesSize / 2)];
  }

  std::cout << "closest x point: " << median << std::endl;

  int fuelcost = 0;
  for (int crabSubmarine : crabSubmarines)
  {
    fuelcost += std::abs(crabSubmarine - median);
  }

  std::cout << "fuel cost: " << fuelcost << std::endl;
  std::cout << "ow sheesh :s" << std::endl;
}


std::vector<int> getCrabSubmarines()
{
  std::ifstream crabFile("hello-world.txt");
  if (!crabFile.is_open())
  {
    std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
    exit(1);
  }

  std::string crabString;
  std::getline(crabFile, crabString);

  std::vector<int> crabSubmarines;

  std::stringstream ss(crabString);
  for (int crab; ss >> crab;) {
      crabSubmarines.push_back(crab);   
      if (ss.peek() == ',')
          ss.ignore();
  }
  return crabSubmarines;
}