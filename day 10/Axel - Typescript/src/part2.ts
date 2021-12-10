import { readFileSync } from "fs";

const blockStartEndMap: Map<string, string> = new Map([
  ["(", ")"],
  ["[", "]"],
  ["{", "}"],
  ["<", ">"],
]);

const scoreMap: Map<string, number> = new Map([
  [")", 1],
  ["]", 2],
  ["}", 3],
  [">", 4],
]);

const lines = readInput("./input.txt");
const incompleteSequences: string[][] = getIncompleteSymbols(lines);
const completedSequences: string[][] = completeSyntax(incompleteSequences);
const score: number = calculateScore(completedSequences);
console.log(score);

function readInput(path: string) {
  const inputText: string = readFileSync(path, "utf-8");
  const normalizedInputText: string = inputText.replace(/\r\n/g, "\n");

  const lines: string[] = normalizedInputText.split("\n");

  return lines;
}

function getIncompleteSymbols(lines: string[]): string[][] {
  const incompleteSymbols: string[][] = [];
  lines.forEach((line) => {
    const incomplete = checkLineSyntax(line);
    if (incomplete.length > 0) {
      incompleteSymbols.push(incomplete);
    }
  });
  return incompleteSymbols;
}

function checkLineSyntax(line: string): string[] {
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
        return [];
      }
    }
  }

  return opened;
}

function isOpening(symbol: string): boolean {
  const openingSymbols: string[] = Array.from(blockStartEndMap.keys());
  return openingSymbols.includes(symbol);
}

function isCorrectClosing(symbol: string, openedSymbol: string): boolean {
  return blockStartEndMap.get(openedSymbol) == symbol;
}

function completeSyntax(incompletes: string[][]): string[][] {
  const result: string[][] = [];
  incompletes.forEach((incomplete) => {
    const completingSequence: string[] = [];
    while (incomplete.length > 0) {
      completingSequence.push(blockStartEndMap.get(incomplete.pop()));
    }
    result.push(completingSequence);
  });
  return result;
}

function calculateScore(symbolSequences: string[][]): number {
  const lineScores: number[] = [];
  symbolSequences.forEach((sequence) => {
    let score = 0;
    sequence.forEach((char) => (score = 5 * score + scoreMap.get(char)));
    lineScores.push(score);
  });

  lineScores.sort((n1, n2) => n1 - n2);
  return lineScores[Math.floor(lineScores.length / 2)];
}
