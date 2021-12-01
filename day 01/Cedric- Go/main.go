package main

import (
	"encoding/csv"
	"fmt"
	"io"
	"os"
	"strconv"
)

var windowSize = 3

func main() {
	file, _ := os.Open("input.csv")
	csvReader := csv.NewReader(file)

	var err error
	var rec []string

	var prevValue = 0
	var prevMeasurement = NewMeasurement(windowSize)
	var currMeasurement = NewMeasurement(windowSize)

	var i = 0
	var numMeasurementIncreases = 0
	for err == nil {
		i++

		rec, err = csvReader.Read()
		if err != nil {
			break
		}

		var value = parseOrExplode(rec[0])

		if i == 1 {
			prevValue = value
			continue
		}

		prevMeasurement.addValue(prevValue)
		currMeasurement.addValue(value)
		prevValue = value

		if i <= windowSize {
			continue
		}

		if currMeasurement.getMeasurementValue() > prevMeasurement.getMeasurementValue() {
			numMeasurementIncreases++
		}
	}

	if err != io.EOF {
		panic(err)
	}

	fmt.Println(numMeasurementIncreases)
}

func parseOrExplode(a string) int {
	i, err := strconv.Atoi(a)
	if err != nil {
		panic(err)
	}

	return i
}

type Measurement struct {
	values []int
	size   int
	index  int
}

func NewMeasurement(size int) *Measurement {
	var m = &Measurement{}
	m.values = make([]int, size)
	m.size = size
	m.index = 0

	return m
}

func (m *Measurement) addValue(v int) {
	m.values[m.index] = v
	m.index = (m.index + 1) % m.size
}

func (m *Measurement) getMeasurementValue() int {
	sum := 0
	for _, v := range m.values {
		sum += v
	}

	return sum
}
