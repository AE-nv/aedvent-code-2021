package main

import (
	"fmt"
	"strings"
)

func makeDictPartTwo(data string) (string, map[string]string, map[string]int) {
	m := make(map[string]string)
	n := make(map[string]int)
	dataArray := strings.Split(data, "\n")
	var input string
	for i, e := range dataArray {
		if i == 0 {
			input = e
		} else if i > 1 {
			elementArray := strings.Split(e, " -> ")
			m[elementArray[0]] = elementArray[1]
			n[elementArray[0]] = 0
		}
	}
	return input, m, n
}

func countPairs(pairsCount map[string]int, input string) map[string]int {
	letters := strings.Split(input, "")
	for i := 0; i < (len(input) - 1); i++ {
		pair := letters[i] + letters[i+1]
		pairsCount[pair] = pairsCount[pair] + 1
	}
	return pairsCount
}

func main() {
	data := readData("./data/data.txt")
	input, pairs, pairsCount := makeDictPartTwo(data)
	letterCount := countLetters(input)
	pairsCount = countPairs(pairsCount, input)

	for i := 0; i < 40; i++ {
		pairsPresent := make(map[string]int)
		for k, e := range pairsCount {
			if e > 0 {
				pairsPresent[k] = e
			}
		}
		for k, e := range pairsPresent {
			newLetter := pairs[k]
			letters := strings.Split(k, "")
			letterCount[newLetter] += e
			pairsCount[letters[0]+newLetter] += e
			pairsCount[newLetter+letters[1]] += e
			pairsCount[k] -= e
		}
	}

	max := 0
	min := letterCount["B"]
	var maxString string
	var minString string
	for _, e := range letterCount {
		if max < e {
			max = e
		}
		if min > e {
			min = e
		}
	}

	for k, e := range letterCount {
		if max == e {
			maxString = k
			fmt.Println("max: ", k)
		}
		if min == e {
			minString = k
			fmt.Println("min: ", k)
		}
	}
	fmt.Println("solution: ", letterCount[maxString]-letterCount[minString])
	fmt.Println(letterCount)
}
