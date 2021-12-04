import { parse } from "path/posix";
import { Board } from "./Board";

export {};

const fs = require("fs");
const text: string = fs.readFileSync("./input.txt", "utf-8");
const textByLine: string[] = text.split("\n");

const size = 5;

const values = textByLine[0].split(",").map((s) => parseInt(s));

// parse the boards

let boards: Board[] = [];
let i = 2;
while (i < textByLine.length) {
  boards.push(
    new Board(
      textByLine.slice(i, i + 5).map((line) => parseLine(line)),
      size
    )
  );
  i += 6;
}

function parseLine(line: string): number[] {
  if (line[0] == " ") {
    line = line.slice(1);
  }
  return line.split(/\s+/).map((s) => parseInt(s));
}

// play the game

for (let i in values) {
  boards.forEach((board) => {
    board.markValue(values[i]);
    if (board.isBingo() && board.bingoAfterTurns == null) {
      board.bingoAfterTurns = parseInt(i);
      board.score = board.sumRemaining() * values[i];
    }
  });
}

boards.sort((a, b) => b.bingoAfterTurns - a.bingoAfterTurns); // inverse sort
console.log(boards[0]);
