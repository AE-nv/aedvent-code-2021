#include <iostream>
#include <fstream>
#include <map>

int main() {
  //start somewhere, one must!
	std::cout << "Hello world" << std::endl;

	std::map<std::string, int> directions;
  std::ifstream directionsFromFile;
  directionsFromFile.open("hello-world.txt");

  if (!directionsFromFile) {
		std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
		exit(1);
	}

  std::map<std::string, int> position {{"horizontal", 0}, {"vertical", 0}};
  int aim = 0;

  std::string key;
  int val;
    while (directionsFromFile >> key >> val) {
    if (key == "forward")
    {
      position["horizontal"] += val;
      position["vertical"] += val * aim;
    }
    if (key == "down") {
      aim += val;
    }
    if (key == "up")
    {
      aim -= val;
    }
  std::cout << "current position is:" << std::endl;
  std::cout << "horizontal: " << position["horizontal"] << " - vertical: " << position["vertical"] << std::endl;
  }

  std::cout << "current position is:" << std::endl;
  std::cout << "horizontal: " << position["horizontal"] << " - vertical: " << position["vertical"] << std::endl;
  std::cout << "total: " << position["horizontal"] * position["vertical"] << std::endl;
  std::cout << "ow sheesh :s" << std::endl;
  return 0;
}