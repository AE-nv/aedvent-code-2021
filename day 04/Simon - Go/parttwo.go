package main

import "fmt"

//if you want to run add data folder to GOPATH!

func main() {
	numbers, matrices := decodeMatrix("./data/data.txt")
	var loseRound = 0
	var loseScore = 0
	var loseLastNum = 0
	var loseBoard = 0
	for board, e := range matrices {
		round, score, lastNumber := solveMatrix(numbers, e)
		if round > loseRound {
			loseBoard = board
			loseRound = round
			loseScore = score
			loseLastNum = lastNumber
		}
	}
	fmt.Println("losing board: ", loseBoard)
	fmt.Println("losing round: ", loseRound)
	fmt.Println("losing score: ", loseScore)
	fmt.Println("losing last num: ", loseLastNum)
	fmt.Println("final score: ", loseScore*loseLastNum)
}
