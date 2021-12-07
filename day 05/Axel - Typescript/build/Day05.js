"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = require("fs");
const Line_1 = __importDefault(require("./Line"));
const inputText = (0, fs_1.readFileSync)("./input_test.txt", "utf-8");
const normalizedInputText = inputText.replace(/\r\n/g, "\n");
const lineDescriptions = normalizedInputText.split("\n");
const inputLines = [];
lineDescriptions.forEach((description) => inputLines.push(new Line_1.default(description)));
const coordinateMap = getCoordinateHeathmap(inputLines);
const overlappingPoints = Array.from(coordinateMap)
    .filter(([key, value]) => value >= 2)
    .map(([key, value]) => key);
Array.from(coordinateMap)
    .filter(([key, value]) => value >= 2)
    .forEach(e => console.log(e));
console.log(overlappingPoints.length);
function getCoordinateHeathmap(lines) {
    const coordinateMap = new Map();
    for (const line of lines) {
        const coordinates = line.getAllCoordinates();
        for (const coordinate of coordinates) {
            const coordinateKey = coordinate.toString();
            let mapElement = coordinateMap.get(coordinateKey);
            if (mapElement) {
                coordinateMap.set(coordinateKey, mapElement + 1);
            }
            else {
                coordinateMap.set(coordinateKey, 1);
            }
        }
    }
    return coordinateMap;
}
exports.default = getCoordinateHeathmap;
//# sourceMappingURL=Day05.js.map