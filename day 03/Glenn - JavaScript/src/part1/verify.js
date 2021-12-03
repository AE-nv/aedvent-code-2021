const PowerConsumptionCalculator = require("./power-consumption-calculator.js");
console.log(new PowerConsumptionCalculator("./data/reference-data.json").calculateGammaRate() === 22);
console.log(new PowerConsumptionCalculator("./data/reference-data.json").calculateEpsilonRate() === 9);
console.log(new PowerConsumptionCalculator("./data/reference-data.json").calculatePowerConsumption() === 198);