#include <iostream>
#include <fstream>
#include <sstream>
#include <queue>
using namespace std;

#define ILovePasta int
#define MainRecipe main
#define WithaPinchOf <<
#define Spaghetti string
#define Pastas argc
#define Chonchiglie ifstream
#define GetNextPasta getline(infile ,line)
#define SpoiledPasta -99999
#define GrabNextPasta istringstream iss(line)
#define Bolognese ;
#define Macaroni infile("sonarreading")
#define Cheese line
#define NonExistant 0
#define WhileThereAreStillPastasOnTheMenu while
#define IsMaybe >>
#define Pasta iss
#define with {
#define notWith }
#define Penne char
#define LotsOfPenne *argv[]
#define ComplementsToTheCheff return
#define IOrder cout
#define Salt endl
#define AndHopefully &&
#define MORESAUSCE ++
#define is ==
#define equals =
#define drie 3
#define twee 2
#define telErBijOp +=
#define trektErVanaf -=
#define greaterThan >

ILovePasta MainRecipe(ILovePasta Pastas, Penne LotsOfPenne)
with
    Chonchiglie Macaroni Bolognese
    Spaghetti Cheese Bolognese
    queue<ILovePasta> lopendeBand Bolognese
    queue<ILovePasta> spoiledPastas Bolognese
    queue<ILovePasta> goodPastas Bolognese
    ILovePasta pastasOpMijnBord equals NonExistant Bolognese
    ILovePasta pastasInDeVuilbak equals NonExistant Bolognese
    ILovePasta amountOfIncrements equals NonExistant Bolognese
    ILovePasta bord equals drie Bolognese
    ILovePasta grootteVanDeBand equals twee Bolognese
    WhileThereAreStillPastasOnTheMenu (GetNextPasta)
    with
        GrabNextPasta Bolognese
        ILovePasta currentPasta Bolognese
        Pasta IsMaybe currentPasta Bolognese
        
        lopendeBand.push(currentPasta) Bolognese
        if(lopendeBand.size() is grootteVanDeBand)
            with spoiledPastas.push(lopendeBand.front()) Bolognese pastasInDeVuilbak telErBijOp lopendeBand.front() Bolognese lopendeBand.pop() Bolognese notWith

        if (spoiledPastas.size() greaterThan bord)
            with pastasInDeVuilbak trektErVanaf spoiledPastas.front() Bolognese spoiledPastas.pop() Bolognese notWith

        goodPastas.push(currentPasta) Bolognese
        pastasOpMijnBord telErBijOp currentPasta Bolognese
        if (goodPastas.size() greaterThan bord)
            with pastasOpMijnBord trektErVanaf goodPastas.front() Bolognese goodPastas.pop() Bolognese notWith
        
        if (pastasOpMijnBord greaterThan pastasInDeVuilbak AndHopefully spoiledPastas.size() is drie AndHopefully goodPastas.size() is drie)
            with amountOfIncrements MORESAUSCE Bolognese notWith  
    notWith
    IOrder WithaPinchOf amountOfIncrements WithaPinchOf Salt Bolognese
    ComplementsToTheCheff NonExistant Bolognese
notWith


// leesbare code voor niet ultra Pasta chefs 

/***
ILovePasta MainRecipe(ILovePasta Pastas, Penne LotsOfPenne)
{

    ifstream infile("sonarreading");
    string line;
    int spaghetti = -99999;
    queue<int> buffer;
    queue<int> currentsumnumbers;
    queue<int> laggingsumnumbers;
    int sumcurrent = 0;
    int sumlagging = 0;
    int amountOfIncrements = 0;
    while (getline(infile ,line))
    {
        istringstream iss(line);
        int a;
        if (!(iss >> a)) { break; } // error
        
        buffer.push(a);
        if(buffer.size() == 2)
        {
            laggingsumnumbers.push(buffer.front());
            sumlagging += buffer.front();
            buffer.pop();
        }

        if (laggingsumnumbers.size() > 3)
        {
            sumlagging -= laggingsumnumbers.front();
            laggingsumnumbers.pop();
        }

        currentsumnumbers.push(a);
        sumcurrent += a;
        if (currentsumnumbers.size() > 3)
        {
            sumcurrent -= currentsumnumbers.front();
            currentsumnumbers.pop();
        }
        
        if (sumcurrent > sumlagging && laggingsumnumbers.size() == 3 && currentsumnumbers.size() == 3)
        {
            amountOfIncrements++;
        }  
    }
    cout WithaPinchOf amountOfIncrements WithaPinchOf endl;
    return 0;
}
***/
