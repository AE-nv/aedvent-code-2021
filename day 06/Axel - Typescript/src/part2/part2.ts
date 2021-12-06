import { readFileSync } from "fs";

const inputText: string = readFileSync("./input.txt", "utf-8");

const initialTimers: number[] = inputText.split(",").map((e) => parseInt(e));
let numberByAgeMap: Map<number, number> = new Map();
initialTimers.forEach((e) =>
  numberByAgeMap.set(e, (numberByAgeMap.get(e) ?? 0) + 1)
);

const maxDays = 256;

for (let i = 0; i < maxDays; i++) {
  const tempMap: Map<number, number> = new Map();
  for (const [age, quantity] of numberByAgeMap) {
    if (age == 0) {
      tempMap.set(8, (tempMap.get(8) ?? 0) + quantity);
      tempMap.set(6, (tempMap.get(6) ?? 0) + quantity);
    } else {
      tempMap.set(age - 1, (tempMap.get(age - 1) ?? 0) + quantity);
    }
  }
  numberByAgeMap = tempMap;
}

let total: number = 0;
for (const [age, quantity] of numberByAgeMap) {
  total += quantity;
}

console.log(total);
