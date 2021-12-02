#include <iostream>
#include <fstream>
#include <sstream>



class Submarine 
{
    public:
        Submarine(): horizontal_pos{0}, depth{0}{};

        // getters
        int get_depth(){ return depth;};
        int get_horizontal_pos(){ return horizontal_pos;};
        int calculte_multiple(){return horizontal_pos*depth;};

        // functionality
        void move(std::string richting, int units){
            if (richting == "forward") forward(units);
            if (richting == "up") up(units);
            if (richting == "down") down(units);
        };
        void forward(int units){
            horizontal_pos += units;
        };
        void up(int units){
            depth -= units;
        };
        void down(int units) {
            depth += units;
        };
        void show_position(){
            std::cout << "Current postition: " <<"(" << horizontal_pos << "," << depth << ")" << std::endl;
            };

    // attributes
    private:
        int horizontal_pos;
        int depth;
    
};


int main(int argc, char *argv[])
{
    Submarine sub = Submarine();
    std::string line;
    std::ifstream infile("coordinates");
    while (std::getline(infile ,line))
    {
        std::istringstream iss(line);
        int units;
        std::string richting;
        if (!(iss >> richting >> units)) { break; } // error
        sub.move(richting, units);
        sub.show_position();
    }
    std::cout << "The multiple of the coordinates is: " << sub.calculte_multiple() << std::endl;
    return 0;
}