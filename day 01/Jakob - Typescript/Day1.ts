const fs = require("fs");
const text = fs.readFileSync("./input.txt", "utf-8");
const textByLine = text.split("\r\n");
const input = textByLine.map((str) => parseInt(str));

let count = 0;
let i = 1;
while (i < input.length) {
  if (input[i] > input[i - 1]) {
    count++;
  }
  i++;
}

console.log(count);
