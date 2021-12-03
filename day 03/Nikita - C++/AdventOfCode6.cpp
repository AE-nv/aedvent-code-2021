#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>


class Submarine 
{
    public:
        Submarine(): count{0}, epsilon{0}, gamma{0}, bits(12,0), bitcount{0}{};

        // functionality

        void readBitsset(std::string input){
            bitsets.push_back(input);
            count++;
        };

        char calculateMostCommonbitfromVector(int pos, std::vector<std::string> bitsets_given){
            float isAOneCount = 0;
            float half_amount = ((float)bitsets_given.size())/2;
            for(std::string bitset: bitsets_given){
               if(bitset[pos] == '1'){
                   isAOneCount++;
               } 
            }
            std::cout << "For position " << pos << " i counted " << isAOneCount << " one's and there are " << bitsets_given.size() << std::endl;
            if (isAOneCount >= half_amount) return '1';
            return '0';
        };

        void filterBitSets(){
            int pos = 0;
            std::vector<std::string> oxygenbits;
            std::vector<std::string> co2bits;
            char mostcommon = calculateMostCommonbitfromVector(pos, bitsets);
            std::cout << mostcommon << " : is the most common bit " << std::endl;
            for (std::string bitsset: bitsets){
                if (bitsset[pos] == mostcommon){
                    oxygenbits.push_back(bitsset);
                    
                }
                else{
                    co2bits.push_back(bitsset);
                }
            }
            int itemsdeleted = 0;
            pos++;

            std::cout << oxygenbits.size() << " : is the size of oxygen " << std::endl;
            std::cout << co2bits.size() << " : is the size of co2 " << std::endl;
            std::vector<std::string> newOxygen;
            std::vector<std::string> newco2;
            while (oxygenbits.size() != 1 && pos < 12)
            {
                newOxygen.clear();
                mostcommon = calculateMostCommonbitfromVector(pos, oxygenbits);
                std::cout << mostcommon << " : is the most common bit " << std::endl;
                int index = 0;
                for (std::string bitsset: oxygenbits){
                    if (bitsset[pos] == mostcommon){
                        newOxygen.push_back(bitsset);
                    }
                    index++;
                }
                std::cout << oxygenbits.size() << std::endl;
                pos++;
                oxygenbits.clear();
                for (int i=0; i<newOxygen.size(); i++)
                    oxygenbits.push_back(newOxygen[i]);

            }
            pos = 1;
            while (co2bits.size() != 1 && pos < 12)
            {
                newco2.clear();
                mostcommon = calculateMostCommonbitfromVector(pos, co2bits);
                int index = 0;
                for (std::string bitsset: co2bits){
                    if (bitsset[pos] != mostcommon){
                        newco2.push_back(bitsset);
                    }
                    index++;
                }
                pos++;
                co2bits.clear();
                for (int i=0; i<newco2.size(); i++)
                    co2bits.push_back(newco2[i]);
            }
            std::string oxygennumber = oxygenbits[0];
            std::string co2number = co2bits[0];
            int oxygeninteger = 0;
            int co2integer = 0;
            int times = 1;
            for (int i=11; i>=0; i--){
                if(oxygennumber[i] == '1'){
                    oxygeninteger += times;
                }
                if(co2number[i] == '1'){
                    co2integer += times;
                }
                times *= 2;
            }
            std::cout << "Number for co2 and oxygen " << co2number << " and " << oxygennumber << std::endl; 
            std::cout << "Number for life support " << oxygeninteger * co2integer << std::endl; 
        };

        void showVector(){
            for(int i = 0; i<12; i++){
                std::cout << bits[i] << "  ";
            }
            std::cout << std::endl;
            };

        void showGE(){std::cout << gamma << " and " << epsilon << std::endl;};
        int calculateGE(){return gamma*epsilon;};
        void showdummy(){std::cout << bitcount << std::endl;};

    // attributes
    private:
        std::vector<std::string> bitsets;
        std::vector<int> bits;
        int count;
        int epsilon;
        int gamma;
        int bitcount;

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
    sub.filterBitSets();
    sub.showGE();
    sub.showdummy();
    std::cout << "The multiple of the coordinates is: " << sub.calculateGE() << std::endl;
    return 0;
}
