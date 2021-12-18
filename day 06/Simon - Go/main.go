package main

import (
	"fmt"
)

func makeLanterFish(isTest bool) map[int]int {
	var input []int
	if isTest {
		input = []int{3, 4, 3, 1, 2}
	} else {
		input = []int{4, 1, 1, 1, 5, 1, 3, 1, 5, 3, 4, 3, 3, 1, 3, 3, 1, 5, 3, 2, 4, 4, 3, 4, 1, 4, 2, 2, 1, 3, 5, 1, 1, 3, 2, 5, 1, 1, 4, 2, 5, 4, 3, 2, 5, 3, 3, 4, 5, 4, 3, 5, 4, 2, 5, 5, 2, 2, 2, 3, 5, 5, 4, 2, 1, 1, 5, 1, 4, 3, 2, 2, 1, 2, 1, 5, 3, 3, 3, 5, 1, 5, 4, 2, 2, 2, 1, 4, 2, 5, 2, 3, 3, 2, 3, 4, 4, 1, 4, 4, 3, 1, 1, 1, 1, 1, 4, 4, 5, 4, 2, 5, 1, 5, 4, 4, 5, 2, 3, 5, 4, 1, 4, 5, 2, 1, 1, 2, 5, 4, 5, 5, 1, 1, 1, 1, 1, 4, 5, 3, 1, 3, 4, 3, 3, 1, 5, 4, 2, 1, 4, 4, 4, 1, 1, 3, 1, 3, 5, 3, 1, 4, 5, 3, 5, 1, 1, 2, 2, 4, 4, 1, 4, 1, 3, 1, 1, 3, 1, 3, 3, 5, 4, 2, 1, 1, 2, 1, 2, 3, 3, 5, 4, 1, 1, 2, 1, 2, 5, 3, 1, 5, 4, 3, 1, 5, 2, 3, 4, 4, 3, 1, 1, 1, 2, 1, 1, 2, 1, 5, 4, 2, 2, 1, 4, 3, 1, 1, 1, 1, 3, 1, 5, 2, 4, 1, 3, 2, 3, 4, 3, 4, 2, 1, 2, 1, 2, 4, 2, 1, 5, 2, 2, 5, 5, 1, 1, 2, 3, 1, 1, 1, 3, 5, 1, 3, 5, 1, 3, 3, 2, 4, 5, 5, 3, 1, 4, 1, 5, 2, 4, 5, 5, 5, 2, 4, 2, 2, 5, 2, 4, 1, 3, 2, 1, 1, 4, 4, 1, 5}
	}
	sumZero := 0
	sumOne := 0
	sumtwo := 0
	sumthree := 0
	sumfour := 0
	sumfive := 0
	sumsix := 0

	for _, e := range input {
		switch e {
		case 0:
			sumZero++
		case 1:
			sumOne++
		case 2:
			sumtwo++
		case 3:
			sumthree++
		case 4:
			sumfour++
		case 5:
			sumfive++
		case 6:
			sumsix++
		}
	}
	return map[int]int{
		-1: 0,
		0:  sumZero,
		1:  sumOne,
		2:  sumtwo,
		3:  sumthree,
		4:  sumfour,
		5:  sumfive,
		6:  sumsix,
		7:  0,
		8:  0,
	}

}

func main() {
	lanternfish := makeLanterFish(false)
	for i := 0; i < 256; i++ {
		for j := -1; j <= 8; j++ {
			lanternfish[j] = lanternfish[j+1]
		}
		lanternfish[6] += lanternfish[-1]
		lanternfish[8] += lanternfish[-1]
		lanternfish[-1] = 0
	}
	sumLanternfish := 0
	for j := 0; j <= 8; j++ {
		sumLanternfish += lanternfish[j]
	}

	fmt.Println("ammount of fish: ", sumLanternfish)
}
