import { Command, Movement } from "./Command";

export {};

const fs = require("fs");
const text = fs.readFileSync("./input.txt", "utf-8");
const textByLine = text.split("\n");
const input: Command[] = textByLine.map((str) => new Command(str));

let horpos: number = 0;
let depth: number = 0;

for (let command of input) {
  switch (command.movement) {
    case Movement.DOWN: {
      depth += command.amount;
      break;
    }
    case Movement.UP: {
      depth -= command.amount;
      if (depth < 0) {
        depth = 0;
      }
      break;
    }
    case Movement.FORWARD: {
      horpos += command.amount;
      break;
    }
  }
}

console.log("horizontal position: " + horpos);
console.log("depth: " + depth);
console.log("product: " + depth * horpos);
