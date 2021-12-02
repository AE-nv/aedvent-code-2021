import { Command, Movement } from "./Command";

export {};

const fs = require("fs");
const text = fs.readFileSync("./input.txt", "utf-8");
const textByLine = text.split("\n");
const input: Command[] = textByLine.map((str) => new Command(str));

let horpos: number = 0;
let depth: number = 0;
let aim: number = 0;

for (let command of input) {
  switch (command.movement) {
    case Movement.DOWN: {
      aim += command.amount;
      break;
    }
    case Movement.UP: {
      aim -= command.amount;
      break;
    }
    case Movement.FORWARD: {
      horpos += command.amount;
      depth += command.amount * aim;
      if (depth < 0) {
        depth = 0;
      }
      break;
    }
  }
}

console.log("horizontal position: " + horpos);
console.log("depth: " + depth);
console.log("product: " + depth * horpos);
console.log("aim: " + aim);
