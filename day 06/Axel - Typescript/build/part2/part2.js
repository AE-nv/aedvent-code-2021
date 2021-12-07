"use strict";
var _a, _b, _c;
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = require("fs");
const inputText = fs_1.readFileSync("./input.txt", "utf-8");
const initialTimers = inputText.split(",").map((e) => parseInt(e));
let numberByAgeMap = new Map();
initialTimers.forEach((e) => { var _a; return numberByAgeMap.set(e, ((_a = numberByAgeMap.get(e)) !== null && _a !== void 0 ? _a : 0) + 1); });
const maxDays = 256;
for (let i = 0; i < maxDays; i++) {
    const tempMap = new Map();
    for (const [age, quantity] of numberByAgeMap) {
        if (age == 0) {
            tempMap.set(8, ((_a = tempMap.get(8)) !== null && _a !== void 0 ? _a : 0) + quantity);
            tempMap.set(6, ((_b = tempMap.get(6)) !== null && _b !== void 0 ? _b : 0) + quantity);
        }
        else {
            tempMap.set(age - 1, ((_c = tempMap.get(age - 1)) !== null && _c !== void 0 ? _c : 0) + quantity);
        }
    }
    numberByAgeMap = tempMap;
}
let total = 0;
for (const [age, quantity] of numberByAgeMap) {
    total += quantity;
}
console.log(total);
//# sourceMappingURL=part2.js.map