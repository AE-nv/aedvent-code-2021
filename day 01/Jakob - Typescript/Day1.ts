const fs = require("fs");
const text = fs.readFileSync("./input.txt", "utf-8");
const textByLine = text.split("\r\n");
const input = textByLine.map((str) => parseInt(str));

const movingAmount = 3;

let count = 0;
let movingCount = 0;
let i = 1;
while (i < input.length) {
  if (input[i] > input[i - 1]) {
    count++;
  }
  if (i > movingAmount - 1 && input[i] > input[i - movingAmount]) {
    // moving amount increases if value i is greater than the value it replaces, i.e. the one x places ago.
    movingCount++;
  }
  i++;
}

console.log("count: " + count);
console.log("movingCount: " + movingCount);
