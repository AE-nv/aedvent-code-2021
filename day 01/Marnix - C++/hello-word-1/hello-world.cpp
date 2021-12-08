#include <iostream>
#include <fstream>

int main() {

	//start somewhere, one must!
	std::cout << "Hello world" << std::endl;

	int higherAmount = 0;
	int a;
	int b;

	std::ifstream measurements;

	measurements.open("hello-world.txt");

	if (!measurements) {
		std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
		exit(1);
	}

	while (measurements >> a) {
		if (b < a)
		{
			higherAmount += 1;
		}
		b = a;
	}
	measurements.close();
	std::cout << "Amount of measurements highter than previous one: " << higherAmount << std::endl;
	std::cout << "ow sheesh :s" << std::endl;

	return 0;
}