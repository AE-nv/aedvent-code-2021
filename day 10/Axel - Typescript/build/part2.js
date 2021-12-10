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
    [")", 1],
    ["]", 2],
    ["}", 3],
    [">", 4],
]);
const lines = readInput("./input.txt");
const incompleteSequences = getIncompleteSymbols(lines);
const completedSequences = completeSyntax(incompleteSequences);
const score = calculateScore(completedSequences);
console.log(score);
function readInput(path) {
    const inputText = fs_1.readFileSync(path, "utf-8");
    const normalizedInputText = inputText.replace(/\r\n/g, "\n");
    const lines = normalizedInputText.split("\n");
    return lines;
}
function getIncompleteSymbols(lines) {
    const incompleteSymbols = [];
    lines.forEach((line) => {
        const incomplete = checkLineSyntax(line);
        if (incomplete.length > 0) {
            incompleteSymbols.push(incomplete);
        }
    });
    return incompleteSymbols;
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
                return [];
            }
        }
    }
    return opened;
}
function isOpening(symbol) {
    const openingSymbols = Array.from(blockStartEndMap.keys());
    return openingSymbols.includes(symbol);
}
function isCorrectClosing(symbol, openedSymbol) {
    return blockStartEndMap.get(openedSymbol) == symbol;
}
function completeSyntax(incompletes) {
    const result = [];
    incompletes.forEach((incomplete) => {
        const completingSequence = [];
        while (incomplete.length > 0) {
            completingSequence.push(blockStartEndMap.get(incomplete.pop()));
        }
        result.push(completingSequence);
    });
    return result;
}
function calculateScore(symbolSequences) {
    const lineScores = [];
    symbolSequences.forEach((sequence) => {
        let score = 0;
        sequence.forEach((char) => (score = 5 * score + scoreMap.get(char)));
        lineScores.push(score);
    });
    lineScores.sort((n1, n2) => n1 - n2);
    return lineScores[Math.floor(lineScores.length / 2)];
}
//# sourceMappingURL=part2.js.map