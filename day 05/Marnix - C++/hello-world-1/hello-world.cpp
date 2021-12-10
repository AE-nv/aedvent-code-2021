#include <iostream>
#include <fstream>
#include <boost/numeric/ublas/matrix.hpp>
#include <vector>

struct coord
{
  int x;
  int y;
};

struct line
{
  coord coord1;
  coord coord2;
};

struct thermalLines
{
  int maxX;
  int maxY;
  std::vector<line> lines;
};

boost::numeric::ublas::matrix<int> getThermalMap();
thermalLines getCoords();
coord getCoord(std::string *coordString);
void updateThermalMap(boost::numeric::ublas::matrix<int> *thermalMap, coord *coord1, coord *coord2);
int calcDangerousZones(boost::numeric::ublas::matrix<int> *thermalMap);

int main()
{
  //start somewhere, one must!
  std::cout << "Hello world" << std::endl;

  thermalLines thermalLines = getCoords();

  boost::numeric::ublas::matrix<int> thermalMap = boost::numeric::ublas::zero_matrix(thermalLines.maxX + 1, thermalLines.maxY + 1);

  for (size_t i = 0; i < thermalLines.lines.size(); i++)
  {
    updateThermalMap(&thermalMap, &thermalLines.lines[i].coord1, &thermalLines.lines[i].coord2);
  }

  int dangerousZones = calcDangerousZones(&thermalMap);
  std::cout << "amount of dangerous zones: " << dangerousZones << std::endl;
  std::cout << "ow sheesh :s" << std::endl;
  return 0;
}

int calcDangerousZones(boost::numeric::ublas::matrix<int> *thermalMap)
{
  int dangerousZones = 0;
  for (size_t r = 0; r < thermalMap->size1(); r++)
  {
    for (size_t c = 0; c < thermalMap->size2(); c++)
    {
      if ((*thermalMap)(r, c) && (*thermalMap)(r, c) > 1)
      {
        int test = (*thermalMap)(r, c);
        dangerousZones++;
      }
    }
  }
  return dangerousZones;
}

thermalLines getCoords()
{
  std::ifstream thermalLinesFile("hello-world.txt");
  if (!thermalLinesFile.is_open())
  {
    std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
    exit(1);
  }

  int maxX = 0;
  int maxY = 0;
  std::vector<line> lines;

  std::string coordString;
  while (std::getline(thermalLinesFile, coordString))
  {
    std::string delimiter = "->";
    int delimiterPos = coordString.find(delimiter);
    std::string coordString1 = coordString.substr(0, delimiterPos);
    std::string coordString2 = coordString.substr(delimiterPos + delimiter.size());

    line line = {
      coord1 : getCoord(&coordString1),
      coord2 : getCoord(&coordString2)
    };

    //only take lines that are hor or vert
    if (line.coord1.x == line.coord2.x || line.coord1.y == line.coord2.y)
    {
      if (line.coord1.x > maxX)
      {
        maxX = line.coord1.x;
      }
      if (line.coord2.x > maxX)
      {
        maxX = line.coord2.x;
      }
      if (line.coord1.y > maxY)
      {
        maxY = line.coord1.y;
      }
      if (line.coord2.y > maxY)
      {
        maxY = line.coord2.y;
      }

      lines.push_back(line);
    }
  }
  return {
    maxX : maxX,
    maxY : maxY,
    lines : lines
  };
}

void updateThermalMap(boost::numeric::ublas::matrix<int> *thermalMap, coord *coord1, coord *coord2)
{
  int rowStart;
  int rowStop;
  if ((*coord1).x < (*coord2).x)
  {
    rowStart = (*coord1).x;
    rowStop = (*coord2).x;
  }
  else
  {
    rowStart = (*coord2).x;
    rowStop = (*coord1).x;
  }

  int columnStart;
  int columnStop;
  if ((*coord1).y < (*coord2).y)
  {
    columnStart = (*coord1).y;
    columnStop = (*coord2).y;
  }
  else
  {
    columnStart = (*coord2).y;
    columnStop = (*coord1).y;
  }

  for (size_t r = rowStart; r <= rowStop; r++)
  {
    for (size_t c = columnStart; c <= columnStop; c++)
    {
      (*thermalMap)(r, c) += 1;
    }
  }
}

coord getCoord(std::string *coordString)
{
  coord coordinate;
  int commaPos = coordString->find(',');
  coordinate.x = std::stoi(coordString->substr(0, commaPos));
  coordinate.y = std::stoi(coordString->substr(commaPos + 1));
  return coordinate;
}