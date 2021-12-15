package main

import (
	"fmt"
	"os"
	"strings"
)

func check(err error) {
	if err != nil {
		panic(err)
	}
}

func readData(filePath string) string {
	input, err := os.ReadFile(filePath)
	check(err)
	return string(input)
}

func makeDict(data string) (string, map[string]string) {
	m := make(map[string]string)
	dataArray := strings.Split(data, "\n")
	var input string
	for i, e := range dataArray {
		if i == 0 {
			input = e
		} else if i > 1 {
			elementArray := strings.Split(e, " -> ")
			m[elementArray[0]] = elementArray[1]
		}
	}
	return input, m
}

func countLetters(input string) map[string]int {
	m := make(map[string]int)
	inputLetters := strings.Split(input, "")
	for _, e := range inputLetters {
		if _, found := m[e]; found {
			m[e] = m[e] + 1
		} else {
			m[e] = 1
		}
	}
	return m
}

func partone() {
	data := readData("./data/test.txt")
	input, pairs := makeDict(data)

	for i := 0; i < 40; i++ {
		letters := strings.Split(input, "")
		lenInput := len(input) - 1
		for i := 0; i < lenInput; i++ {
			pair := letters[i] + letters[i+1]
			letterToInsert := pairs[pair]
			input = input[:(2*i)+1] + letterToInsert + input[(2*i)+1:]
		}
	}

	fmt.Println(input)
	fmt.Println(countLetters(input))
}
