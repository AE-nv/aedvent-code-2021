import { readFileSync } from "fs";

const blockStartEndMap: Map<string, string> = new Map([
  ["(", ")"],
  ["[", "]"],
  ["{", "}"],
  ["<", ">"],
]);

const scoreMap: Map<string, number> = new Map([
  [")", 3],
  ["]", 57],
  ["}", 1197],
  [">", 25137],
]);

const lines = readInput("./input.txt");
const invalidSymbols: string[] = getInvalidSymbols(lines);
const score: number = calculateScore(invalidSymbols);
console.log(score);

function readInput(path: string) {
  const inputText: string = readFileSync(path, "utf-8");
  const normalizedInputText: string = inputText.replace(/\r\n/g, "\n");

  const lines: string[] = normalizedInputText.split("\n");

  return lines;
}

function getInvalidSymbols(lines: string[]): string[] {
  const invalidSymbols: string[] = [];
  lines.forEach((line) => {
    const invalidSymbol = checkLineSyntax(line);
    if (invalidSymbol) {
      invalidSymbols.push(invalidSymbol);
    }
  });
  return invalidSymbols;
}

function checkLineSyntax(line: string): string {
  const symbols: string[] = [...line];
  const opened: string[] = [];
  for (const symbol of symbols) {
    if (isOpening(symbol)) {
      opened.push(symbol);
    } else {
      const lastOpenedSymbol = opened.pop();
      const correct: boolean = isCorrectClosing(symbol, lastOpenedSymbol);
      if (!correct) {
        console.log(
          `Syntax error: expected ${blockStartEndMap.get(
            lastOpenedSymbol
          )}, but found ${symbol} instead.`
        );
        return symbol;
      }
    }
  }

  return "";
}

function isOpening(symbol: string): boolean {
  const openingSymbols: string[] = Array.from(blockStartEndMap.keys());
  return openingSymbols.includes(symbol);
}

function isCorrectClosing(symbol: string, openedSymbol: string): boolean {
  return blockStartEndMap.get(openedSymbol) == symbol;
}

function calculateScore(invalidSymbols: string[]): number {
  return invalidSymbols
    .map((symbol) => scoreMap.get(symbol))
    .reduce((a, b) => a + b);
}
