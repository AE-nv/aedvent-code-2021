#include <iostream>
#include <fstream>
#include <vector>

std::vector<int> getMeasurementsFromFile();

int main() {
  //start somewhere, one must!
	std::cout << "Hello world" << std::endl;

	int higherAmount = 0;
	std::vector<int> measurements = getMeasurementsFromFile();
	int n = measurements.size();
	for (int i = 0; i < n - 3; i++) {
		int movAvgA = measurements.at(i) + measurements.at(i + 1) + measurements.at(i + 2);
		int movAvgB = + measurements.at(i + 1) + measurements.at(i + 2) + measurements.at(i + 3);
		if (movAvgB > movAvgA) {
			higherAmount += 1;
		}
	}
	std::cout << "amount of higher values: " << higherAmount << std::endl;
	std::cout << "ow sheesh :s" << std::endl;
  return 0;
}

std::vector<int> getMeasurementsFromFile() {
	std::vector<int> temp;
	std::ifstream measurementsFile;
	measurementsFile.open("hello-world.txt");

	if (!measurementsFile) {
		std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
		exit(1);
	}

	int x;
	while (measurementsFile >> x)
	{
		temp.push_back(x);
	}
	measurementsFile.close();
	return temp;
}