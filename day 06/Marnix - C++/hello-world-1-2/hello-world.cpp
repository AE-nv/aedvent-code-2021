#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <queue>

std::vector<unsigned long long int> getSchool();

int main()
{
  //start somewhere, one must!
  std::cout << "Hello world" << std::endl;

  std::vector<unsigned long long int> school = getSchool();
  std::queue<unsigned long long int*> pregersQueue;
  for (size_t i = 0; i < school.size(); i++)
  {
    pregersQueue.push(&school[i]);
  }
  
  int days;
  std::cout << "How many days would you like to simulate: ";
  std::cin >> days;

  std::queue<unsigned long long int> children;
  children.push(0);
  children.push(0);

  //work with pointers instead of having to transfer data the entire time
  for (size_t i = 0; i < days; i++)
  {
    unsigned long long int* birth = pregersQueue.front();
    pregersQueue.pop();
    unsigned long long int newGrownUps = children.front();
    children.pop();
    children.push(*birth);
    *birth = *birth + newGrownUps;
    pregersQueue.push(birth);
  }
  
  unsigned long long int schoolSize = children.front() + children.back();
  for (size_t i = 0; i < school.size(); i++)
  {
    schoolSize += school[i];
  }
  std::cout << "Amount of fish after " << days << ": " << schoolSize << std::endl;
  std::cout << "ow sheesh :s" << std::endl;
}

std::vector<unsigned long long int> getSchool()
{
  std::ifstream fishFile("hello-world.txt");
  if (!fishFile.is_open())
  {
    std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
    exit(1);
  }

  std::string fishString;
  std::getline(fishFile, fishString);

  std::vector<unsigned long long int> school = {0,0,0,0,0,0,0};

  std::stringstream ss(fishString);
  for (int fish; ss >> fish;) {
      school[fish] ++;   
      if (ss.peek() == ',')
          ss.ignore();
  }
  return school;
}