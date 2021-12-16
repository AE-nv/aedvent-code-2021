package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

type plane [1000][1000]int

func check(err error) {
	if err != nil {
		panic(err)
	}
}

func readFile(filelocation string) []string {
	input, err := os.ReadFile(filelocation)
	check(err)
	data := strings.Split(string(input), "\n")
	return data
}

func (p plane) addLinesToPlane(line string) plane {
	var begin [2]int
	var end [2]int
	datapoints := strings.Split(line, " -> ")
	for i, e := range strings.Split(datapoints[0], ",") {
		var err error
		begin[i], err = strconv.Atoi(e)
		check(err)
	}
	for i, e := range strings.Split(datapoints[1], ",") {
		var err error
		end[i], err = strconv.Atoi(e)
		check(err)
	}
	var startLine int
	var endLine int
	if begin[0] == end[0] {
		if begin[1] < end[1] {
			startLine = begin[1]
			endLine = end[1]
		} else {
			startLine = end[1]
			endLine = begin[1]
		}
		for i := startLine; i <= endLine; i++ {
			p[i][begin[0]]++
		}
	} else if begin[1] == end[1] {
		if begin[0] < end[0] {
			startLine = begin[0]
			endLine = end[0]
		} else {
			startLine = end[0]
			endLine = begin[0]
		}
		for i := startLine; i <= endLine; i++ {
			p[begin[1]][i]++
		}
	} else {
		nextPoint := begin
		for nextPoint != end {
			p[nextPoint[1]][nextPoint[0]]++
			if begin[0] > end[0] {
				nextPoint[0]--
			} else {
				nextPoint[0]++
			}
			if begin[1] > end[1] {
				nextPoint[1]--
			} else {
				nextPoint[1]++
			}
		}
		p[nextPoint[1]][nextPoint[0]]++
	}
	return p
}

func main() {
	data := readFile("./data/data.txt")
	var cave plane
	for _, e := range data {
		cave = cave.addLinesToPlane(e)
	}
	var overlapCount int
	for _, e := range cave {
		for _, f := range e {
			if f > 1 {
				overlapCount++
			}
		}
	}
	fmt.Println("solution: ", overlapCount)
}
