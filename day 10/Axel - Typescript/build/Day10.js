"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = require("fs");
const blockStartEndMap = new Map([
    ["(", ")"],
    ["[", "]"],
    ["{", "}"],
    ["<", ">"],
]);
const scoreMap = new Map([
    [")", 3],
    ["]", 57],
    ["}", 1197],
    [">", 25137],
]);
const lines = readInput("./input.txt");
const invalidSymbols = getInvalidSymbols(lines);
const score = calculateScore(invalidSymbols);
console.log(score);
function readInput(path) {
    const inputText = fs_1.readFileSync(path, "utf-8");
    const normalizedInputText = inputText.replace(/\r\n/g, "\n");
    const lines = normalizedInputText.split("\n");
    return lines;
}
function getInvalidSymbols(lines) {
    const invalidSymbols = [];
    lines.forEach((line) => {
        const invalidSymbol = checkLineSyntax(line);
        if (invalidSymbol) {
            invalidSymbols.push(invalidSymbol);
        }
    });
    return invalidSymbols;
}
function checkLineSyntax(line) {
    const symbols = [...line];
    const opened = [];
    for (const symbol of symbols) {
        if (isOpening(symbol)) {
            opened.push(symbol);
        }
        else {
            const lastOpenedSymbol = opened.pop();
            const correct = isCorrectClosing(symbol, lastOpenedSymbol);
            if (!correct) {
                console.log(`Syntax error: expected ${blockStartEndMap.get(lastOpenedSymbol)}, but found ${symbol} instead.`);
                return symbol;
            }
        }
    }
    return "";
}
function isOpening(symbol) {
    const openingSymbols = Array.from(blockStartEndMap.keys());
    return openingSymbols.includes(symbol);
}
function isCorrectClosing(symbol, openedSymbol) {
    return blockStartEndMap.get(openedSymbol) == symbol;
}
function calculateScore(invalidSymbols) {
    return invalidSymbols
        .map((symbol) => scoreMap.get(symbol))
        .reduce((a, b) => a + b);
}
//# sourceMappingURL=Day10.js.map