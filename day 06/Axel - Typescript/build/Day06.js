"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = require("fs");
const Fish_1 = __importDefault(require("./Fish"));
const inputText = fs_1.readFileSync("./input.txt", "utf-8");
const initialTimers = inputText.split(",").map((e) => parseInt(e));
const fishes = initialTimers.map((num) => new Fish_1.default(num));
const maxDays = 256;
for (let i = 0; i < maxDays; i++) {
    const newFishes = [];
    for (const fish of fishes) {
        const willSpawn = fish.decrementTimer();
        if (willSpawn) {
            newFishes.push(new Fish_1.default());
        }
    }
    fishes.push(...newFishes);
}
console.log(fishes.length);
//# sourceMappingURL=Day06.js.map