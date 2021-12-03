const PowerConsumptionCalculator = require("./power-consumption-calculator.js");
console.log(new PowerConsumptionCalculator("./data/data.json").calculatePowerConsumption());