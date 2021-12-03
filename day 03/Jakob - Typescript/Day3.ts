const fs = require("fs");
const text: string = fs.readFileSync("./input.txt", "utf-8");
const textByLine: string[] = text.split("\n");

let counts: number[] = new Array(textByLine[0].length).fill(0);

for (let reading of textByLine) {
  for (let i = 0; i < reading.length; i++) {
    if (reading[i] == "1") {
      counts[i]++;
    }
  }
}

let gamma = parseInt(
  counts
    .map((c) => String(isMostCommon(c, textByLine.length) ? 1 : 0))
    .join(""),
  2
);

let epsilon = parseInt(
  counts
    .map((c) => String(!isMostCommon(c, textByLine.length) ? 1 : 0))
    .join(""),
  2
);

console.log("gamma: " + gamma);
console.log("epsilon: " + epsilon);

console.log("product: " + gamma * epsilon);

function isMostCommon(count: number, length: number): boolean {
  if (count > length / 2) {
    return true;
  } else return false;
}
