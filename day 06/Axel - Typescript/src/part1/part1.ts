import { readFileSync } from "fs";
import Fish from "./Fish";

const inputText: string = readFileSync("./input.txt", "utf-8");

const initialTimers: number[] = inputText.split(",").map((e) => parseInt(e));
const initialFishes: Fish[] = initialTimers.map((num) => new Fish(num));

const result = getNumberOfFishes(initialFishes, 80);

console.log(result);

function getNumberOfFishes(fishes: Fish[], maxDays: number) {
  for (let i = 0; i < maxDays; i++) {
    const newFishes: Fish[] = [];
    for (const fish of fishes) {
      const willSpawn: boolean = fish.decrementTimer();
      if (willSpawn) {
        newFishes.push(new Fish());
      }
    }
    fishes.push(...newFishes);
  }
  return fishes.length;
}
