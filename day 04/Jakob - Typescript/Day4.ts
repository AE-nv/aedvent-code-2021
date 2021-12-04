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
  let bingo: boolean = false;
  boards.forEach((board) => {
    board.markValue(values[i]);
    if (board.isBingo()) {
      const sumRemaining = board.sumRemaining();
      console.log("Bingo! Used " + i + " turns");
      console.log("Sum remaining: " + sumRemaining);
      console.log("Value: " + values[i]);
      console.log("Score: " + values[i] * sumRemaining);
      bingo = true;
    }
  });
  if (bingo) {
    break;
  }
}
