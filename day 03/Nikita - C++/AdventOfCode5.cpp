#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>


class Submarine 
{
    public:
        Submarine(): count{0}, epsilon{0}, gamma{0}, bits(12,0), dummycount{0}{};

        // getters

        // functionality

        void readBitsset(std::string input){
            if (input[1] == '1') dummycount++;
            for (int i=0; i<12; i++)
            {
                if ('1' == input[i]){
                    bits[i]++;};
            }
            count++;
            showVector();
        };

        void calculateGammaEpsilon(){
            int half_amount = count/2;
            int times = 1;
            for (int i=11; i>=0; i--){
                if(bits[i] > half_amount){
                    gamma += times;
                }
                else{
                    epsilon += times;
                }
                times*=2;
            }
        };

        void showVector(){
            for(int i = 0; i<12; i++){
                std::cout << bits[i] << "  ";
            }
            std::cout << std::endl;
            };

        void showGE(){std::cout << gamma << " and " << epsilon << std::endl;};
        int calculateGE(){return gamma*epsilon;};
        void showdummy(){std::cout << dummycount << std::endl;};

    // attributes
    private:
        std::vector<int> bits;
        int count;
        int epsilon;
        int gamma;
        int dummycount;
    
};


int main(int argc, char *argv[])
{
    Submarine sub = Submarine();
    std::string line;
    std::ifstream infile("bitsets");
    while (std::getline(infile ,line))
    {
        std::istringstream iss(line);
        std::string richting;
        if (!(iss >> richting)) { break; } // error
        sub.readBitsset(richting);
    }
    sub.calculateGammaEpsilon();
    sub.showGE();
    sub.showdummy();
    std::cout << "The multiple of the coordinates is: " << sub.calculateGE() << std::endl;
    return 0;
}