export {};

const fs = require("fs");
const text: string = fs.readFileSync("./input.txt", "utf-8");
const textByLine: string[] = text.split("\n");

let counts: number[] = new Array(textByLine[0].length).fill(0);

for (let i = 0; i < textByLine[0].length; i++) {
  for (let reading of textByLine) {
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

// -----------------------------------------------------------------------

let index: number = 0;
let entries = textByLine;
while (entries.length > 1) {
  const mostCommon: string = getMostCommonOfColumn(entries, index);
  console.log(mostCommon);
  entries = entries.filter((line) => line[index] == mostCommon);
  index++;
}
console.log(entries);

let index2 = 0;
let leastCommons = textByLine;
while (leastCommons.length > 1) {
  const most: string = getMostCommonOfColumn(leastCommons, index2);
  console.log(most);
  leastCommons = leastCommons.filter((line) => line[index2] != most);
  index2++;
}
console.log(leastCommons);

function getMostCommonOfColumn(entries: string[], column: number): string {
  console.log(column);
  let count1s: number = 0;
  for (let entry of entries) {
    if (entry[column] == "1") {
      count1s++;
    }
  }
  console.log(count1s, entries.length);
  return count1s >= entries.length / 2 ? "1" : "0";
}

console.log(
  "product = " + parseInt(entries[0], 2) * parseInt(leastCommons[0], 2)
);
