#include <iostream>
#include <fstream>
#include <vector>
#include <boost/numeric/ublas/matrix.hpp>

const int columns = 5;
const int rows = 5;

std::vector<int> getBingoNumbers(std::ifstream *file);
std::vector<boost::numeric::ublas::bounded_matrix<int, rows, columns>> getBingoCards(std::ifstream *file);
bool checkRow(int row, boost::numeric::ublas::bounded_matrix<bool, rows, columns> *bingoCardHistory);
bool checkColumn(int column, boost::numeric::ublas::bounded_matrix<bool, rows, columns> *bingoCardHistory);
int calcWinningScore(int winningNumber, boost::numeric::ublas::bounded_matrix<int, rows, columns> *bingoCard, boost::numeric::ublas::bounded_matrix<bool, rows, columns> *bingoCardHistory);


int main() {
	std::cout << "Hello world" << std::endl;
  std::ifstream bingoNumbersFile("hello-world.txt");
  if (!bingoNumbersFile.is_open())
  {
    std::cout << "No file 'hello-world.txt' found! Did you name it correctly?" << std::endl;
    exit(1);
  }

  std::vector<int> bingoNumbers = getBingoNumbers(&bingoNumbersFile);
  std::vector<boost::numeric::ublas::bounded_matrix<int, rows, columns>> bingoCards = getBingoCards(&bingoNumbersFile);

  std::vector<boost::numeric::ublas::bounded_matrix<bool, rows, columns>> bingoCardsHistory(bingoCards.size());

  int winner;
  //loop over all numbers called
  for (size_t i = 0; i < bingoNumbers.size(); i++)
  {
    int bingoNumber = bingoNumbers[i];
    //loop over all bingocards
    for (size_t j = 0; j < bingoCards.size(); j++)
    {
      boost::numeric::ublas::bounded_matrix<int, rows, columns> bingoCard = bingoCards[j];
      //loop over rows of bingocard
      for (size_t r = 0; r < rows; r++)
      {
        //loop over columns of the row
        for (size_t c = 0; c < columns; c++)
        {
          if (bingoCard(r, c) == bingoNumber)
          {
            bingoCardsHistory[j](r,c) = 1;
            if (checkRow(r, &bingoCardsHistory[j]) || checkColumn(c, &bingoCardsHistory[j]))
            {
              winner = calcWinningScore(bingoNumber, &bingoCard, &bingoCardsHistory[j]);
              std::cout << "ow sheesh :s" << std::endl;
              return 0;
            }
          }
        }
      }
    }
  } 
}

int calcWinningScore(int winningNumber, boost::numeric::ublas::bounded_matrix<int, rows, columns> *bingoCard, boost::numeric::ublas::bounded_matrix<bool, rows, columns> *bingoCardHistory) {
  int sum = 0;
  for (size_t i = 0; i < rows; i++)
  {
    for (size_t j = 0; j < columns; j++)
    {
      if ((*bingoCardHistory)(i,j) != 1)
      {
        sum += (*bingoCard)(i,j);
      }
    }
  }
  std::cout << "winning number: " << sum * winningNumber << std::endl;
  return sum * winningNumber;
}

bool checkRow(int row, boost::numeric::ublas::bounded_matrix<bool, rows, columns> *bingoCardHistory) {
  for (size_t i = 0; i < columns; i++)
  {
    if ((*bingoCardHistory)(row, i) != 1)
    {
      return 0;
    }
  }
  return 1;
}

bool checkColumn(int column, boost::numeric::ublas::bounded_matrix<bool, rows, columns> *bingoCardHistory) {
  for (size_t i = 0; i < rows; i++)
  {
    if ((*bingoCardHistory)(i, column) != 1)
    {
      return 0;
    }
  }
  return 1;
}

std::vector<boost::numeric::ublas::bounded_matrix<int, rows, columns>> getBingoCards(std::ifstream *file) {
  std::vector<boost::numeric::ublas::bounded_matrix<int, rows, columns>> bingoCards;

  int currentColumn = 0;
  int currentRow = 0;
  boost::numeric::ublas::bounded_matrix<int, rows, columns> bingoCard;
  int x;
  while (*file >> x)
  {
    bingoCard(currentRow, currentColumn) = x;

    currentColumn ++;
    if (currentColumn == columns)
    {
      currentColumn = 0;
      currentRow ++;
    }
    if (currentRow == rows)
    {
      currentRow = 0;
      bingoCards.push_back(bingoCard);
    }
  }
  return bingoCards;
}

std::vector<int> getBingoNumbers(std::ifstream *file) {
  std::string bingoNumbersString;
  std::getline(*file, bingoNumbersString);

  std::string delimiter = ",";
  size_t pos = 0;

  std::vector<int> bingoNumbers;
  std::string token;
  while ((pos = bingoNumbersString.find(delimiter)) != std::string::npos) {
      bingoNumbers.push_back(std::stoi(bingoNumbersString.substr(0, pos)));
      bingoNumbersString.erase(0, pos + delimiter.length());
  }

  return bingoNumbers;
}