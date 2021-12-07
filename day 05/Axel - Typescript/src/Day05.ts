import { readFileSync } from "fs";
import Coordinate from "./Coordinate";
import Line from "./Line";

const inputText: string = readFileSync("./input_test.txt", "utf-8");
const normalizedInputText: string = inputText.replace(/\r\n/g, "\n");

const lineDescriptions: string[] = normalizedInputText.split("\n");

const inputLines: Line[] = [];
lineDescriptions.forEach((description) => inputLines.push(new Line(description)));

const coordinateMap = getCoordinateHeathmap(inputLines);

const overlappingPoints: Coordinate[] = Array.from(coordinateMap)
  .filter(([key, value]) => value >= 2)
  .map(([key, value]) => key);

  Array.from(coordinateMap)
  .filter(([key, value]) => value >= 2)
  .forEach(e => console.log(e));

console.log(overlappingPoints.length);


export default function getCoordinateHeathmap(lines: Line[]) {
  const coordinateMap = new Map();
  for (const line of lines) {
    const coordinates: Coordinate[] = line.getAllCoordinates();
    for (const coordinate of coordinates) {
      const coordinateKey = coordinate.toString();
      let mapElement = coordinateMap.get(coordinateKey);
      if (mapElement) {
        coordinateMap.set(coordinateKey, mapElement + 1);
      } else {
        coordinateMap.set(coordinateKey, 1);
      }
    }
  }
  return coordinateMap;
}

