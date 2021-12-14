package main

import (
	"fmt"
	"os"
	"regexp"
	"strconv"
	"strings"
)

type matrix [5][5]int

func (m matrix) find(number int) matrix {
	for x, e := range m {
		for y, i := range e {
			if i == number {
				m[x][y] = -1
				return m
			}
		}
	}
	return m
}

func (m matrix) isMatrixSolved() bool {
	for i := 0; i < 5; i++ {
		if m[i][0] == -1 && m[i][1] == -1 && m[i][2] == -1 && m[i][3] == -1 && m[i][4] == -1 {
			return true
		}
		if m[0][i] == -1 && m[1][i] == -1 && m[2][i] == -1 && m[3][i] == -1 && m[4][i] == -1 {
			return true
		}
	}
	return false
}

func (m matrix) calculateMatrixScore() int {
	var score = 0
	for _, e := range m {
		for _, i := range e {
			if i != -1 {
				score += i
			}
		}
	}
	return score
}

func decodeMatrix(file string) ([]int, []matrix) {
	inputString, err := os.ReadFile(file)
	check(err)
	inputArray := strings.Split(string(inputString), "\n\n")
	var numbersInt []int
	var inputMatrix []matrix
	for i, e := range inputArray {
		if i == 0 {
			numbersString := strings.Split(e, ",")
			for _, s := range numbersString {
				n, err := strconv.Atoi(s)
				check(err)
				numbersInt = append(numbersInt, n)
			}
		} else {
			regex := regexp.MustCompile(`\s\s|\s`)
			matrixNumbers := regex.Split(e, -1)
			var newMatrix matrix
			var nextNumber = 0
			for i := 0; i < 25; i++ {
				if matrixNumbers[nextNumber] == "" {
					nextNumber++
				}
				n, err := strconv.Atoi(matrixNumbers[nextNumber])
				check(err)
				newMatrix[i/5][i%5] = n
				nextNumber++
			}
			inputMatrix = append(inputMatrix, newMatrix)
		}
	}
	return numbersInt, inputMatrix
}

func solveMatrix(numbers []int, element matrix) (int, int, int) {
	for round, number := range numbers {
		element = element.find(number)
		if element.isMatrixSolved() {
			return round, element.calculateMatrixScore(), number
		}
	}
	return 0, 0, 0
}

func solvepartone() {
	numbers, matrices := decodeMatrix("./data/test.txt")
	var winRound = 100
	var winScore = 0
	var winLastNum = 0
	for _, e := range matrices {
		round, score, lastNumber := solveMatrix(numbers, e)
		if round < winRound && score > winScore {

			winRound = round
			winScore = score
			winLastNum = lastNumber
		}
	}
	fmt.Println("winning round: ", winRound)
	fmt.Println("winning score: ", winScore)
	fmt.Println("winning last num: ", winLastNum)
	fmt.Println("final score: ", winScore*winLastNum)
}
